package main

import (
	"errors"
	"fmt"
	"util"
	"wrapper"

	"github.com/hyperledger/fabric/core/chaincode/shim"
)

type RegisterChaincode struct {
}

//初始化创建表
func (t *RegisterChaincode) Init(stub shim.ChaincodeStubInterface, function string, args []string) ([]byte, error) {
	return nil, util.CreateTable(stub)
}

//调用方法
func (t *RegisterChaincode) Invoke(stub shim.ChaincodeStubInterface, function string, args []string) ([]byte, error) {
	if function == "SignUp" { //用户注册
		return wrapper.SignUp(stub, args)
	} else if function == "SignIn" { //用户登录
		return wrapper.SignIn(stub, args)
	} else if function == "CreditPoints" { // 授信积分
		return wrapper.CreditPoints(stub, args)
	} else if function == "ConsumePoints" { // 消费积分
		return wrapper.ConsumePoints(stub, args)
	} else if function == "AccpetPoints" { // 承兑积分
		return wrapper.AccpetPoints(stub, args)
	}

	return nil, errors.New("调用invoke失败")
}

//查询
func (t *RegisterChaincode) Query(stub shim.ChaincodeStubInterface, function string, args []string) ([]byte, error) {
	//function, args := stub.GetFunctionAndParameters()
	return nil, errors.New("调用query失败")
}

func main() {

	err := shim.Start(new(RegisterChaincode))
	if err != nil {
		fmt.Printf("Error starting chaincode: %s", err)
	}

}
