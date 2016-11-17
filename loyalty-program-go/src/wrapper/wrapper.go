package wrapper

import (
	"account"
	"errors"
	"fmt"
	"log"
	"points"
	"strconv"
	"user"
	"util"

	"github.com/hyperledger/fabric/core/chaincode/shim"
)

type TotalNum struct {
	TableName string
}

type CreditPointsTransData struct {
	Account                 *account.Account
	PointsTransaction       *points.PointsTransaction
	PointsTransactionDetail *points.PointsTransactionDetail
}

type ConsumePointsTransData struct {
	AccountList                 []*account.Account
	PointsTransaction           *points.PointsTransaction
	PointsTransactionDetailList []*points.PointsTransactionDetail
}
type AccpetPointsTransData struct {
	PointsTransaction           []*points.PointsTransaction
	PointsTransactionDetailList []*points.PointsTransactionDetail
}

type InitTableData struct {
	PointsUser []*user.PointsUser
	Account    []*account.Account
}

type PointsInfo struct {
	TransId string
}

//注册
func SignUp(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {

	return nil, nil
}

//登录
func SignIn(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {

	return nil, nil
}

//授信积分
func CreditPoints(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {
	defer util.End(util.Begin("CreditPoints"))
	defer func() {
		e := recover()
		if e != nil {
			fmt.Println("授信积分抛出异常:", e)
			log.Println("授信积分抛出异常:", e)
		}
	}()
	// 解析传入数据
	creditObject := new(CreditPointsTransData)
	err := util.ParseJsonAndDecode(creditObject, args)
	if err != nil {
		log.Println("Error occurred when parsing json")
		return nil, errors.New("Error occurred when parsing json.")
	}
	//账户信息表更新
	if creditObject.Account.OperFlag == "1" {
		err := account.UpdateAccount(stub, creditObject.Account)
		if err != nil {
			log.Println("Error occurred when performing UpdateAccount")
			return nil, errors.New("Error occurred when performing UpdateAccount.")
		}

	} else {
		log.Println("Flag is not 1,that's why we do nothing for table account")
	}
	// 积分交易表增加记录
	if creditObject.PointsTransaction.OperFlag == "0" {
		err := points.InsertPointsTransation(stub, creditObject.PointsTransaction)
		if err != nil {
			log.Println("Error occurred when performing InsertPointsTransation")
			return nil, errors.New("Error occurred when performing InsertPointsTransation.")
		}

	} else {
		log.Println("Flag is not 0,that's why we do nothing for table points_transation")
	}
	// 积分交易明细表增加记录
	if creditObject.PointsTransactionDetail.OperFlag == "0" {
		err := points.InsertPointsTransationDetail(stub, creditObject.PointsTransactionDetail)
		if err != nil {
			log.Println("Error occurred when performing InsertPointsTransationDetail")
			return nil, errors.New("Error occurred when performing InsertPointsTransationDetail.")
		}

	} else {
		log.Println("Flag is not 0,that's why we do nothing for table points_transation_detail")
	}
	log.Println("CreditPoints success.")

	return nil, nil
}

//消费积分:用户购买了商品，商户赠送积分给消费用户，所以这里的关系必须是转出账号是商户，转入账号是用户
func ConsumePoints(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {
	defer util.End(util.Begin("ConsumePoints"))
	defer func() {
		e := recover()
		if e != nil {
			fmt.Println("消费积分抛出异常:", e)
			log.Println("消费积分抛出异常:", e)
		}
	}()

	// parse data
	data := new(ConsumePointsTransData)
	err := util.ParseJsonAndDecode(data, args)
	if err != nil {
		log.Println("Error occurred when parsing json")
		return nil, errors.New("Error occurred when parsing json.")
	}

	transAmount := data.PointsTransaction.TransAmount // transfer amount
	amount, err := strconv.ParseInt(transAmount, 10, 64)
	if err != nil {
		log.Println("Error occurred when converting amount string to int")
		panic(err)
	}
	if amount == 0 {
		errorMsg := "transfer amount is zero,so no need to do any transaction."
		return nil, errors.New(errorMsg)
	}

	// check the number of accounts
	if len(data.AccountList) != 2 {
		errorMsg := "Transfer points must and only need two accounts"
		log.Println(errorMsg)
		panic(errorMsg)
	}

	transOut := data.PointsTransaction.RolloutAccount // transfer out account
	transIn := data.PointsTransaction.RollinAccount   // transfeer in account

	// update account
	for i := 0; i < len(data.AccountList); i++ {
		acc := data.AccountList[i]

		// check if specified account exists or not.
		data, err := account.QueryAccountRecordByKey(stub, acc.AccountId)
		if data == nil {
			panic(err) // throw exception when account doesn't exist.
		}

		if transOut == acc.AccountId { // If it is transfer out account

			// check if balance of specified account is enough to pay.
			balance := account.QueryAccountBalanceByKey(stub, acc.AccountId) // get balance.
			balanceInt, err := strconv.ParseInt(balance, 10, 64)
			if err != nil {
				log.Println("Error occurred when converting balance string to int")
				panic(err)
			}

			// check if the type of transfer out account is correct,it must be a merchant account.
			if acc.AccountTypeId != account.AccountType_Merchant {
				errMsg := "Incorrect account type, it must be a merchant account,account id =" + acc.AccountId
				panic(errMsg)
			}

			if balanceInt < amount {
				errMsg := "Balance is not enough,account id =" + acc.AccountId
				panic(errMsg)
			}
		} else if transIn == acc.AccountId {

			// check if the type of transfer in account is correct,it must be a c-end user account.
			if acc.AccountTypeId != account.AccountType_Merchant {
				errMsg := "Incorrect account type, it must be a c-end user account,account id =" + acc.AccountId
				panic(errMsg)
			}
		}
	}

	// update points transaction table.
	pointsObj := data.PointsTransaction
	trans, _ := strconv.ParseInt(pointsObj.TransAmount, 10, 64) // transaction points amount

	// define a variable to store exchange amounts from all update amount.
	var totalUpdate int64

	// upate points transaction detail table
	for i := 0; i < len(data.PointsTransactionDetailList); i++ {
		detail := data.PointsTransactionDetailList[i]

		// check if last transaction detail exists
		result := points.CheckPointsDetailExist(stub, detail.SourceDetailId)
		if !result {
			var errorMsg = "Table Points_Transation_Detail: specified record doesn't exist,detailId = " + detail.SourceDetailId
			panic(errorMsg)
		}

		// current balance of last transaction detail
		curBalance, _ := strconv.ParseInt(points.QueryPointsDetailCurBalanceByKey(stub, detail.SourceDetailId), 10, 64)
		if detail.OperFlag == "0" {
			// check if remaining points of last transaction detail is enough.
			transPoints, _ := strconv.ParseInt(detail.TransAmount, 10, 64)
			if transPoints > curBalance {
				log.Println("transPoints ->" + strconv.FormatInt(transPoints, 10))
				log.Println("curBalance ->" + strconv.FormatInt(curBalance, 10))
				var errorMsg = "Current balance of last transaction detail is not enough to pay,detailId = " + detail.SourceDetailId
				panic(errorMsg)
			}
		} else {
			// compute exchange amount
			temp, _ := strconv.ParseInt(detail.CurBalance, 10, 64)
			changeAmount := curBalance - temp
			totalUpdate += changeAmount
		}
	}

	if trans != totalUpdate {
		errMsg := "Invalid data, pls. check if this request has been tampered"
		panic(errMsg)
	}

	// performing insert or update
	log.Println("Performing insert/update start............")
	for i := 0; i < len(data.AccountList); i++ {
		account.UpdateAccount(stub, data.AccountList[i])
	}
	points.InsertPointsTransation(stub, pointsObj)
	for i := 0; i < len(data.PointsTransactionDetailList); i++ {
		detailObject := data.PointsTransactionDetailList[i]
		if detailObject.OperFlag == "0" {
			points.InsertPointsTransationDetail(stub, detailObject)
		} else {
			points.UpdatePointsTransationDetail(stub, detailObject)
		}
	}
	log.Println("Performing insert/update end............")

	log.Println("ConsumePoints success.")
	return nil, nil
}

//承兑积分
func AccpetPoints(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {
	defer util.End(util.Begin("AccpetPoints"))
	defer func() {
		e := recover()
		if e != nil {
			fmt.Println("承兑积分抛出异常:", e)
			log.Println("承兑积分抛出异常:", e)
		}
	}()
	// 解析传入数据
	data := new(AccpetPointsTransData)
	err := util.ParseJsonAndDecode(data, args)
	if err != nil {
		log.Println("Error occurred when parsing json")
		return nil, errors.New("Error occurred when parsing json.")
	}
	for i := 0; i < len(data.PointsTransaction); i++ {
		//如果标识符为0就对账户表新增否则修改
		if data.PointsTransaction[i].OperFlag == "0" {
			points.InsertPointsTransation(stub, data.PointsTransaction[i])
		} else {
			return nil, errors.New("输入标识符有误")
		}
	}
	for i := 0; i < len(data.PointsTransactionDetailList); i++ {
		if data.PointsTransactionDetailList[i].OperFlag == "0" {
			points.InsertPointsTransationDetail(stub, data.PointsTransactionDetailList[i])
		} else {
			points.UpdatePointsTransationDetail(stub, data.PointsTransactionDetailList[i])
		}
	}
	log.Println("AccpetPoints success.")
	return nil, nil
}

//初始化数据
func InitData(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {
	defer util.End(util.Begin("InitData"))
	defer func() {
		e := recover()
		if e != nil {
			fmt.Println("初始化数据抛出异常:", e)
			log.Println("初始化数据抛出异常:", e)
		}
	}()

	data := new(InitTableData)
	err := util.ParseJsonAndDecode(data, args)
	if err != nil {
		log.Println("Error occurred when parsing json")
		return nil, errors.New("Error occurred when parsing json.")
	}
	for i := 0; i < len(data.PointsUser); i++ {
		user.InsertPointsUser(stub, *data.PointsUser[i])
	}
	for j := 0; j < len(data.Account); j++ {
		account.InsertAccount(stub, *data.Account[j])
	}
	log.Println("InitData success.")
	return nil, nil
}

//通过表名查条数
func QueryTableLines(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {
	defer util.End(util.Begin("QueryTableLines"))
	data := new(TotalNum)
	util.ParseJsonAndDecode(data, args)
	return util.QueryTableLines(stub, data.TableName)
}

//通过主键查询次条记录
func QueryPointsByKey(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {
	defer util.End(util.Begin("QueryPointsByKey"))
	data := new(PointsInfo)
	err := util.ParseJsonAndDecode(data, args)
	if err != nil {
		log.Println("Error occurred when parsing json")
		return nil, errors.New("Error occurred when parsing json.")
	}
	return points.QueryPointsByKey(stub, data.TransId)
}
