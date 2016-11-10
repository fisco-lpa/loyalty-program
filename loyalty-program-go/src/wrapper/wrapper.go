package wrapper

import (
	"account"
	"errors"
	"log"
	"points"
	"util"

	"github.com/hyperledger/fabric/core/chaincode/shim"
)

type CreditPointsTransData struct {
	Account                 *account.Account
	PointsTransaction       *points.PointsTransaction
	PointsTransactionDetail *points.PointsTransactionDetail
	AuditObj                *util.AuditObject
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
func CreditPoints(stub shim.ChaincodeStubInterface, args []string) error {
	// 解析传入数据
	creditObject := new(CreditPointsTransData)
	err := util.ParseJsonAndDecode(creditObject, args)
	if err != nil {
		log.Println("Error occurred when parsing json")
		return errors.New("Error occurred when parsing json.")
	}

	//账户信息表更新
	if creditObject.PointsTransaction.OperFlag == "1" {
		err := account.UpdateAccount(stub, creditObject.Account)
		if err != nil {
			log.Println("Error occurred when performing UpdateAccount")
			return errors.New("Error occurred when performing UpdateAccount.")
		}

	} else {
		log.Println("Flag is not 1,that's why we do nothing for table account")
	}

	// 积分交易表增加记录
	if creditObject.Account.OperFlag == "0" {
		err := points.InsertPointsTransation(stub, creditObject.PointsTransaction)
		if err != nil {
			log.Println("Error occurred when performing InsertPointsTransation")
			return errors.New("Error occurred when performing InsertPointsTransation.")
		}

	} else {
		log.Println("Flag is not 0,that's why we do nothing for table points_transation")
	}

	// 积分交易明细表增加记录
	if creditObject.Account.OperFlag == "0" {
		err := points.InsertPointsTransationDetail(stub, creditObject.PointsTransactionDetail)
		if err != nil {
			log.Println("Error occurred when performing InsertPointsTransationDetail")
			return errors.New("Error occurred when performing InsertPointsTransationDetail.")
		}

	} else {
		log.Println("Flag is not 0,that's why we do nothing for table points_transation_detail")
	}

	log.Println("credit points is ok!!")

	return nil
}

//消费积分
func ConsumePoints(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {

	return nil, nil
}

//承兑积分
func AccpetPoints(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {

	return nil, nil
}
