package main

import (
	"encoding/base64"

	"encoding/json"
	"errors"
	"fmt"

	"github.com/hyperledger/fabric/core/chaincode/shim"
)

var THandler = NewDepositoryHandler()

type RegisterChaincode struct {
}

//注册
type pointsUser struct {
	UserId       string //用户ID
	UserName     string //用户名称
	UserPassword string //用户密码
	PhoneNumber  string //手机号
	UserType     string //用户类型 0.授信，1.商户,2.会员
	CreateTime   string //创建时间
	CreateUser   string //创建人
	UpdateTime   string //修改时间
	UpdateUser   string //修改人
}

//修改密码
type modifyPassword struct {
	UserId       string //用户ID
	UserPassword string //用户密码
	UpdateTime   string //修改时间
	UpdateUser   string //修改人
}

//授信
type credit struct {
	AccountId        string //账户ID
	Accountbalance   string //账户积分剩余数量
	TransId          string //积分交易ID
	RollOutAccount   string //转出账户
	RollInAccount    string //转入账户
	TransAmount      string //交易积分数量
	Describe         string //描述
	TransferTime     string //交易时间
	TransferType     string //交易类型
	CreateTime       string //创建时间
	CreateUser       string //创建人
	UpdateTime       string //修改时间
	UpdateUser       string //修改人
	DetailId         string //逐笔明细流水号
	SourceDetailId   string //来源流水号
	ExpireTime       string //过期时间
	ExtRef           string //外部引用
	Status           string //状态  0.冻结,1.正常
	CurBalance       string //当笔积分剩余数量
	CreditParty      string //授信方账户
	Merchant         string //商户账户
	CreditCreateTime string //授信创建时间

}

//注册
func (t *RegisterChaincode) register(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {
	var data pointsUser
	arg, err := base64.StdEncoding.DecodeString(args[0])
	if err != nil {
		return nil, errors.New("The basr64 decoding error")
	}
	err = json.Unmarshal(arg, &data)
	if err != nil {
		return nil, errors.New("argument json Parse error")
	}
	return THandler.register(stub, data)
}

//修改密码
func (t *RegisterChaincode) modifyPassword(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {
	var data modifyPassword
	arg, err := base64.StdEncoding.DecodeString(args[0])
	if err != nil {
		return nil, errors.New("The basr64 decoding error")
	}
	err = json.Unmarshal(arg, &data)
	if err != nil {
		return nil, errors.New("argument json Parse error")
	}
	return THandler.modifyPassword(stub, data)
}

//授信
func (t *RegisterChaincode) Credit(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {
	var data credit
	arg, err := base64.StdEncoding.DecodeString(args[0])
	if err != nil {
		return nil, errors.New("The basr64 decoding error")
	}
	err = json.Unmarshal(arg, &data)
	if err != nil {
		return nil, errors.New("argument json Parse error")
	}
	return THandler.credit(stub, data)
}

//初始化创建表
func (t *RegisterChaincode) Init(stub shim.ChaincodeStubInterface, function string, args []string) ([]byte, error) {
	return nil, THandler.creatTable(stub)
}

//调用方法
func (t *RegisterChaincode) Invoke(stub shim.ChaincodeStubInterface, function string, args []string) ([]byte, error) {
	if function == "register" { //注册
		return t.register(stub, args)
	}
	if function == "modifyPassword" { //注册
		return t.modifyPassword(stub, args)
	}
	if function == "Credit" { //授信
		return t.Credit(stub, args)
	}
	return nil, errors.New("调用invoke失败")
}

//查询
func (t *RegisterChaincode) Query(stub shim.ChaincodeStubInterface, function string, args []string) ([]byte, error) {
	return nil, errors.New("调用query失败")
}

func main() {

	err := shim.Start(new(RegisterChaincode))
	if err != nil {
		fmt.Printf("Error starting chaincode: %s", err)
	}

}
