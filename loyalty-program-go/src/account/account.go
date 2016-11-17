package account

import (
	"encoding/base64"
	"errors"
	"log"
	"util"

	"github.com/hyperledger/fabric/core/chaincode/shim"
)

//账户信息记录对象
type Account struct {
	AccountId      string //账户ID
	UserId         string // 账户所属用户ID
	AccountBalance string // 账户积分剩余数量
	AccountTypeId  string // 账户类型ID
	OperFlag       string // 操作标积 0-新增，1-修改，2-删除
	CreateTime     string //创建时间
	CreateUser     string //创建人
	UpdateTime     string //修改时间
	UpdateUser     string //修改人
}

type AccountType struct {
	AccountTypeId string // 账户类型ID
	Describe      string // 描述
	CreateTime    string //创建时间
	CreateUser    string //创建人
	UpdateTime    string //修改时间
	UpdateUser    string //修改人
}

const (
	AccountType_CreditParty = "1" //授信方
	AccountType_Merchant    = "2" //商户
	AccountType_Usser       = "3" //用户
)

//账户信息录入
func InsertAccount(stub shim.ChaincodeStubInterface, data Account) ([]byte, error) {
	//往账户表插入数据
	ok, err := stub.InsertRow(util.Account, shim.Row{
		Columns: []*shim.Column{
			&shim.Column{Value: &shim.Column_String_{String_: data.AccountId}},      // 账户ID
			&shim.Column{Value: &shim.Column_String_{String_: data.UserId}},         // 账户所属用户ID
			&shim.Column{Value: &shim.Column_String_{String_: data.AccountBalance}}, // 账户积分剩余数量
			&shim.Column{Value: &shim.Column_String_{String_: data.AccountTypeId}},  // 账户类型ID
			&shim.Column{Value: &shim.Column_String_{String_: data.CreateTime}},     // 创建时间
			&shim.Column{Value: &shim.Column_String_{String_: data.CreateUser}},     // 创建人
			&shim.Column{Value: &shim.Column_String_{String_: data.UpdateTime}},     // 修改时间
			&shim.Column{Value: &shim.Column_String_{String_: data.UpdateUser}}},    // 创建时间
	})
	if !ok && err == nil {
		return nil, errors.New("Table Account insert failed.")
	}
	//更新或者插入table_count表
	totalNumber, err := util.UpdateTableCount(stub, util.Account)
	if totalNumber == 0 || err != nil {
		stub.DeleteRow(util.Account, []shim.Column{shim.Column{Value: &shim.Column_String_{String_: data.AccountId}}})
		return nil, errors.New("InsertAccount Table_Count insert failed")
	}
	//把获取的总数插入行号表中当主键
	err = util.UpdateRowNoTable(stub, util.Account_Rownum, data.AccountId, totalNumber)
	if err != nil {
		stub.DeleteRow(util.Account, []shim.Column{shim.Column{Value: &shim.Column_String_{String_: data.AccountId}}})
		var columns []shim.Column
		col := shim.Column{Value: &shim.Column_String_{String_: util.Account}}
		columns = append(columns, col)
		row, _ := stub.GetRow(util.Table_Count, columns) //row是否为空
		if len(row.Columns) == 1 {
			stub.DeleteRow(util.Table_Count, []shim.Column{shim.Column{Value: &shim.Column_String_{String_: util.Account}}})
		} else {
			stub.ReplaceRow(util.Table_Count, shim.Row{
				Columns: []*shim.Column{
					&shim.Column{Value: &shim.Column_String_{String_: util.Account}}, //表名
					&shim.Column{Value: &shim.Column_Int64{Int64: totalNumber - 1}}}, //总数
			})
		}
		return nil, errors.New("InsertAccount insert Account_Rownum failed")
	}
	log.Println("InsertAccount success.")
	return nil, nil
}

//账户信息类型录入
func InsertAccountType(stub shim.ChaincodeStubInterface, data AccountType) ([]byte, error) {
	//往账户表插入数据
	ok, err := stub.InsertRow(util.Account_Type, shim.Row{
		Columns: []*shim.Column{
			&shim.Column{Value: &shim.Column_String_{String_: data.AccountTypeId}}, // 账户类型ID
			&shim.Column{Value: &shim.Column_String_{String_: data.Describe}},      // 描述
			&shim.Column{Value: &shim.Column_String_{String_: data.CreateTime}},    // 创建时间
			&shim.Column{Value: &shim.Column_String_{String_: data.CreateUser}},    // 创建人
			&shim.Column{Value: &shim.Column_String_{String_: data.UpdateTime}},    //修改时间
			&shim.Column{Value: &shim.Column_String_{String_: data.UpdateUser}}},   // 修改人
	})
	if !ok && err == nil {
		return nil, errors.New("Table Account_Type insert failed.")
	}
	//更新或者插入table_count表
	totalNumber, err := util.UpdateTableCount(stub, util.Account_Type)
	if totalNumber == 0 || err != nil {
		stub.DeleteRow(util.Account_Type, []shim.Column{shim.Column{Value: &shim.Column_String_{String_: data.AccountTypeId}}})
		return nil, errors.New("InsertAccountType Table_Count insert failed")
	}
	//把获取的总数插入行号表中当主键
	err = util.UpdateRowNoTable(stub, util.Account_Type_Rownum, data.AccountTypeId, totalNumber)
	if err != nil {
		stub.DeleteRow(util.Account_Type, []shim.Column{shim.Column{Value: &shim.Column_String_{String_: data.AccountTypeId}}})
		var columns []shim.Column
		col := shim.Column{Value: &shim.Column_String_{String_: util.Account_Type}}
		columns = append(columns, col)
		row, _ := stub.GetRow(util.Table_Count, columns) //row是否为空
		if len(row.Columns) == 1 {
			stub.DeleteRow(util.Table_Count, []shim.Column{shim.Column{Value: &shim.Column_String_{String_: util.Account_Type}}})
		} else {
			stub.ReplaceRow(util.Table_Count, shim.Row{
				Columns: []*shim.Column{
					&shim.Column{Value: &shim.Column_String_{String_: util.Account_Type}}, //表名
					&shim.Column{Value: &shim.Column_Int64{Int64: totalNumber - 1}}},      //总数
			})
		}
		return nil, errors.New("InsertAccountType insert Account_Type_Rownum failed")
	}
	log.Println("InsertAccountType success.")
	return nil, nil
}

//更新账户表信息
func UpdateAccount(stub shim.ChaincodeStubInterface, data *Account) error {
	var columns []shim.Column
	col := shim.Column{Value: &shim.Column_String_{String_: data.AccountId}}
	columns = append(columns, col)
	row, _ := stub.GetRow(util.Account, columns)
	if len(row.Columns) != 0 {
		ok, err := stub.ReplaceRow(util.Account, shim.Row{
			Columns: []*shim.Column{
				&shim.Column{Value: &shim.Column_String_{String_: data.AccountId}},              //账户ID
				&shim.Column{Value: &shim.Column_String_{String_: row.Columns[1].GetString_()}}, //账户所属用户ID
				&shim.Column{Value: &shim.Column_String_{String_: data.AccountBalance}},         //账户积分剩余数量
				&shim.Column{Value: &shim.Column_String_{String_: row.Columns[3].GetString_()}}, //账户类型ID
				&shim.Column{Value: &shim.Column_String_{String_: row.Columns[4].GetString_()}}, //状态
				&shim.Column{Value: &shim.Column_String_{String_: row.Columns[5].GetString_()}}, //创建时间
				&shim.Column{Value: &shim.Column_String_{String_: row.Columns[6].GetString_()}}, //创建人
				&shim.Column{Value: &shim.Column_String_{String_: data.UpdateTime}},             //修改时间
				&shim.Column{Value: &shim.Column_String_{String_: data.UpdateUser}}},            //修改人
		})
		if !ok && err == nil {
			return errors.New("method updateAccount Account ReplaceRow failed.")
		}
	}
	log.Println("UpdateAccount success.")
	return nil
}

//根据主键查询账户表记录
func QueryAccountRecordByKey(stub shim.ChaincodeStubInterface, accountId string) ([]byte, error) {
	defer util.End(util.Begin("QueryAccountRecordByKey"))

	var columns []shim.Column
	col := shim.Column{Value: &shim.Column_String_{String_: accountId}}
	columns = append(columns, col)
	row, _ := stub.GetRow(util.Account, columns)
	if len(row.Columns) == 0 { //row是否为空
		var errorMsg = "Table Account: specified record doesn't exist,account id = " + accountId
		log.Println(errorMsg)
		return nil, errors.New(errorMsg)
	} else {
		jsonResp := `{"accountId":"` + row.Columns[0].GetString_() + `","userId":"` + row.Columns[1].GetString_() +
			`","accountbalance":"` + row.Columns[2].GetString_() + `","accountTypeId":"` + row.Columns[3].GetString_() +
			`","createTime":"` + row.Columns[7].GetString_() + `","createUser":"` + row.Columns[8].GetString_() +
			`","updateTime":"` + row.Columns[9].GetString_() + `","updateUser":"` + row.Columns[10].GetString_() + `"}`

		log.Println("jsonResp:" + jsonResp)
		return []byte(base64.StdEncoding.EncodeToString([]byte(`{"status":"OK","errMsg":"查询成功","data":` + jsonResp + `}`))), nil
	}
}

//根据主键查询账户余额
func QueryAccountBalanceByKey(stub shim.ChaincodeStubInterface, accountId string) string {
	defer util.End(util.Begin("QueryAccountBalanceByKey"))

	var columns []shim.Column
	col := shim.Column{Value: &shim.Column_String_{String_: accountId}}
	columns = append(columns, col)
	row, _ := stub.GetRow(util.Account, columns)
	if len(row.Columns) == 0 { //row是否为空
		var errorMsg = "Table Account: specified record doesn't exist,account id = " + accountId
		log.Println(errorMsg)
		panic(errorMsg)
	} else {
		balance := row.Columns[2].GetString_()
		log.Println("accountId is = " + accountId)
		log.Println("account balance is = " + balance)
		return balance
	}
}
