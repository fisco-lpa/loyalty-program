package account

import (
	"encoding/base64"
	"encoding/json"
	"errors"

	"github.com/hyperledger/fabric/core/chaincode/shim"
)

type Account struct {
	AccountId      string //账户ID
	UserId         string // 账户所属用户ID
	AccountBalance string // 账户积分剩余数量
	AccountTypeId  string // 账户类型ID
	Status         string // 状态
	CreateTime     string // 创建时间
	CreateUser     string // 创建人
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

	return nil, nil
}
