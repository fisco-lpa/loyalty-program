package main

import (
	"errors"
	"fmt"
	"log"
	"util"
	"wrapper"

	"github.com/hyperledger/fabric/core/chaincode/shim"
)

type PointsChaincode struct {
}

//初始化创建表
func (t *PointsChaincode) Init(stub shim.ChaincodeStubInterface, function string, args []string) ([]byte, error) {
	return nil, util.CreateTable(stub)
}

//调用方法
func (t *PointsChaincode) Invoke(stub shim.ChaincodeStubInterface, function string, args []string) ([]byte, error) {
	if function == "SignUp" { //用户注册
		log.Println("SignUp,args = " + args[0])
		fmt.Println("SignUp,args = " + args[0])
		return wrapper.SignUp(stub, args)
	} else if function == "SignIn" { //用户登录
		log.Println("SignIn,args = " + args[0])
		fmt.Println("SignIn,args = " + args[0])
		return wrapper.SignIn(stub, args)
	} else if function == "CreditPoints" { // 授信积分
		log.Println("CreditPoints,args = " + args[0])
		fmt.Println("CreditPoints,args = " + args[0])
		return wrapper.CreditPoints(stub, args)
	} else if function == "ConsumePoints" { // 消费积分
		log.Println("ConsumePoints,args = " + args[0])
		fmt.Println("ConsumePoints,args = " + args[0])
		return wrapper.ConsumePoints(stub, args)
	} else if function == "AccpetPoints" { // 承兑积分
		log.Println("AccpetPoints,args = " + args[0])
		fmt.Println("AccpetPoints,args = " + args[0])
		return wrapper.AccpetPoints(stub, args)
	}

	return nil, errors.New("调用invoke失败")
}

//查询
func (t *PointsChaincode) Query(stub shim.ChaincodeStubInterface, function string, args []string) ([]byte, error) {
	//function, args := stub.GetFunctionAndParameters()
	return nil, errors.New("调用query失败")
}

func main() {

	err := shim.Start(new(PointsChaincode))
	if err != nil {
		fmt.Printf("Error starting chaincode: %s", err)
	}

}
