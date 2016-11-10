package account

import (
	"encoding/base64"
	"encoding/json"
	"errors"
	//"log"
	"util"

	"github.com/hyperledger/fabric/core/chaincode/shim"
)

//账户信息记录对象
type Account struct {
	AccountId      string           //账户ID
	UserId         string           // 账户所属用户ID
	AccountBalance string           // 账户积分剩余数量
	AccountTypeId  string           // 账户类型ID
	Status         string           // 状态
	OperFlag       string           // 操作标积 0-新增，1-修改，2-删除
	AuditObj       util.AuditObject //audit object
}

type AccountType struct {
	AccountTypeId string           // 账户类型ID
	Describe      string           // 描述
	AuditObj      util.AuditObject //audit object
}

//账户信息录入
func InsertAccount(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {

	var data Account
	//base64解码
	arg, err := base64.StdEncoding.DecodeString(args[0])
	if err != nil {
		return nil, errors.New("InsertAccount base64 decode error.")
	}

	//解析数据
	err = json.Unmarshal(arg, &data)
	if err != nil {
		return nil, errors.New("InsertAccount json Parse error.")
	}

	//往账户表插入数据
	ok, err := stub.InsertRow(util.Account, shim.Row{
		Columns: []*shim.Column{
			&shim.Column{Value: &shim.Column_String_{String_: data.AccountId}},            // 账户ID
			&shim.Column{Value: &shim.Column_String_{String_: data.UserId}},               // 账户所属用户ID
			&shim.Column{Value: &shim.Column_String_{String_: data.AccountBalance}},       // 账户积分剩余数量
			&shim.Column{Value: &shim.Column_String_{String_: data.AccountTypeId}},        // 账户类型ID
			&shim.Column{Value: &shim.Column_String_{String_: data.Status}},               // 状态
			&shim.Column{Value: &shim.Column_String_{String_: data.AuditObj.CreateTime}},  // 创建时间
			&shim.Column{Value: &shim.Column_String_{String_: data.AuditObj.CreateUser}}}, // 创建人
	})
	if !ok && err == nil {
		return nil, errors.New("Table Account insert failed.")
	}

	//判断总数表是否有数据，若有数据则查出来+1
	var columns []shim.Column
	col := shim.Column{Value: &shim.Column_String_{String_: util.Account}}
	columns = append(columns, col)
	row, _ := stub.GetRow(util.Table_Count, columns) //row是否为空
	totalNumber := row.Columns[1].GetInt64()
	if len(row.Columns) == 0 {
		//若没有数据，则插入总数表一条记录
		totalNumber = 1
		ok, err := stub.InsertRow(util.Table_Count, shim.Row{
			Columns: []*shim.Column{
				&shim.Column{Value: &shim.Column_String_{String_: util.Account}}, //表名
				&shim.Column{Value: &shim.Column_Int64{Int64: totalNumber}}},     //总数
		})
		if !ok && err == nil {
			stub.DeleteRow(util.Account, []shim.Column{shim.Column{Value: &shim.Column_String_{String_: data.AccountId}}})
			return nil, errors.New("method InsertAccount Total_Count insert failed.")
		}
	} else {
		//若有数据，则更新总数
		totalNumber = totalNumber + 1
		ok, err := stub.ReplaceRow(util.Table_Count, shim.Row{
			Columns: []*shim.Column{
				&shim.Column{Value: &shim.Column_String_{String_: util.Account}}, //表名
				&shim.Column{Value: &shim.Column_Int64{Int64: totalNumber}}},     //总数
		})
		if !ok && err == nil {
			stub.DeleteRow(util.Account, []shim.Column{shim.Column{Value: &shim.Column_String_{String_: data.AccountId}}})
			return nil, errors.New("method InsertAccount Table_Count update failed.")
		}
	}
	//把获取的总数插入行号表中当主键
	ok, err = stub.InsertRow(util.Account_Rownum, shim.Row{
		Columns: []*shim.Column{
			&shim.Column{Value: &shim.Column_Int64{Int64: totalNumber}},         //行号
			&shim.Column{Value: &shim.Column_String_{String_: data.AccountId}}}, //数据表主键
	})
	if !ok && err == nil {
		stub.DeleteRow(util.Account, []shim.Column{shim.Column{Value: &shim.Column_String_{String_: data.AccountId}}})
		if len(row.Columns) == 0 {
			stub.DeleteRow(util.Table_Count, []shim.Column{shim.Column{Value: &shim.Column_String_{String_: util.Account}}})
		} else {
			totalNumber = totalNumber - 1
			ok, err := stub.ReplaceRow(util.Table_Count, shim.Row{
				Columns: []*shim.Column{
					&shim.Column{Value: &shim.Column_String_{String_: util.Account}}, //表名
					&shim.Column{Value: &shim.Column_Int64{Int64: totalNumber}}},     //总数
			})
			if !ok && err == nil {
				return nil, errors.New("method InsertAccount Table_Count update failed.")
			}
		}
		return nil, errors.New("method InsertAccount Account_Rownum insert failed.")
	}
	return nil, nil
}

//账户信息录入
func InsertAccountType(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {

	var data AccountType
	//base64解码
	arg, err := base64.StdEncoding.DecodeString(args[0])
	if err != nil {
		return nil, errors.New("InsertAccountType base64 decode error.")
	}

	//解析数据
	err = json.Unmarshal(arg, &data)
	if err != nil {
		return nil, errors.New("InsertAccountType json Parse error.")
	}

	//往账户表插入数据
	ok, err := stub.InsertRow(util.Account_Type, shim.Row{
		Columns: []*shim.Column{
			&shim.Column{Value: &shim.Column_String_{String_: data.AccountTypeId}},        // 账户类型ID
			&shim.Column{Value: &shim.Column_String_{String_: data.Describe}},             // 描述
			&shim.Column{Value: &shim.Column_String_{String_: data.AuditObj.CreateTime}},  // 创建时间
			&shim.Column{Value: &shim.Column_String_{String_: data.AuditObj.CreateUser}}}, // 创建人
	})
	if !ok && err == nil {
		return nil, errors.New("Table Account_Type insert failed.")
	}

	//判断总数表是否有数据，若有数据则查出来+1
	var columns []shim.Column
	col := shim.Column{Value: &shim.Column_String_{String_: util.Account_Type}}
	columns = append(columns, col)
	row, _ := stub.GetRow(util.Table_Count, columns) //row是否为空
	totalNumber := row.Columns[1].GetInt64()
	if len(row.Columns) == 0 {
		//若没有数据，则插入总数表一条记录
		totalNumber = 1
		ok, err := stub.InsertRow(util.Table_Count, shim.Row{
			Columns: []*shim.Column{
				&shim.Column{Value: &shim.Column_String_{String_: util.Account_Type}}, //表名
				&shim.Column{Value: &shim.Column_Int64{Int64: totalNumber}}},          //总数
		})
		if !ok && err == nil {
			stub.DeleteRow(util.Account_Type, []shim.Column{shim.Column{Value: &shim.Column_String_{String_: data.AccountTypeId}}})
			return nil, errors.New("method InsertAccountType Total_Count insert failed.")
		}
	} else {
		//若有数据，则更新总数
		totalNumber = totalNumber + 1
		ok, err := stub.ReplaceRow(util.Table_Count, shim.Row{
			Columns: []*shim.Column{
				&shim.Column{Value: &shim.Column_String_{String_: util.Account_Type}}, //表名
				&shim.Column{Value: &shim.Column_Int64{Int64: totalNumber}}},          //总数
		})
		if !ok && err == nil {
			stub.DeleteRow(util.Account_Type, []shim.Column{shim.Column{Value: &shim.Column_String_{String_: data.AccountTypeId}}})
			return nil, errors.New("method InsertAccountType Table_Count update failed.")
		}
	}
	//把获取的总数插入行号表中当主键
	ok, err = stub.InsertRow(util.Account_Type_Rownum, shim.Row{
		Columns: []*shim.Column{
			&shim.Column{Value: &shim.Column_Int64{Int64: totalNumber}},             //行号
			&shim.Column{Value: &shim.Column_String_{String_: data.AccountTypeId}}}, //数据表主键
	})
	if !ok && err == nil {
		stub.DeleteRow(util.Account_Type, []shim.Column{shim.Column{Value: &shim.Column_String_{String_: data.AccountTypeId}}})
		if len(row.Columns) == 0 {
			stub.DeleteRow(util.Table_Count, []shim.Column{shim.Column{Value: &shim.Column_String_{String_: util.Account_Type}}})
		} else {
			totalNumber = totalNumber - 1
			ok, err := stub.ReplaceRow(util.Table_Count, shim.Row{
				Columns: []*shim.Column{
					&shim.Column{Value: &shim.Column_String_{String_: util.Account_Type}}, //表名
					&shim.Column{Value: &shim.Column_Int64{Int64: totalNumber}}},          //总数
			})
			if !ok && err == nil {
				return nil, errors.New("method InsertAccountType Table_Count update failed.")
			}
		}
		return nil, errors.New("method InsertAccountType Account_Type_Rownum insert failed.")
	}
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
				&shim.Column{Value: &shim.Column_String_{String_: data.AuditObj.UpdateTime}},    //修改时间
				&shim.Column{Value: &shim.Column_String_{String_: data.AuditObj.UpdateUser}}},   //修改人
		})
		if !ok && err == nil {
			return errors.New("method updateAccount Account ReplaceRow failed.")
		}
	}
	return nil
}
