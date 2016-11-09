package main

import (
	//"encoding/base64"

	"account"
	_ "config"
	"points"
	_ "query"
	"user"
	"util"
	//"encoding/json"
	"errors"
	"fmt"

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
	//function, args := stub.GetFunctionAndParameters()

	if function == "InsertPointsUser" { //用户信息录入
		return user.InsertPointsUser(stub, args)
	} else if function == "InsertAccount" { //账户表信息录入
		return account.InsertAccount(stub, args)
	} else if function == "InsertPointsTransation" { // 积分交易信息录入
		return points.InsertPointsTransation(stub, args)
	} else if function == "UpdatePointsTransationDetail" { // 积分交易逐笔明细表信息更新
		return points.UpdatePointsTransationDetail(stub, args)
	} else if function == "InsertPointsTransationDetail" { // 积分交易逐笔明细表信息录入
		return points.InsertPointsTransationDetail(stub, args)
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
