package wrapper

import (
	//"errors"
	"account"
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
func CreditPoints(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {
	var transObject CreditPointsTransData
	//base64解码
	arg, err := base64.StdEncoding.DecodeString(args[0])
	if err != nil {
		return nil, errors.New("CreditPoints base64 decode error.")
	}
	//解析数据
	err = json.Unmarshal(arg, &transObject)
	if err != nil {
		return nil, errors.New("CreditPoints json Parse error.")
	}
	//账户信息表更新
	if transObject.Account.OperFlag == "1" {

	}
	return nil, nil
}

//消费积分
func ConsumePoints(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {

	return nil, nil
}

//承兑积分
func AccpetPoints(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {

	return nil, nil
}
