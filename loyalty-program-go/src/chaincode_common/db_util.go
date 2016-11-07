package chaincode_common

import (
	"errors"

	"github.com/hyperledger/fabric/core/chaincode/shim"
)

//创建表
func CreateTable(stub shim.ChaincodeStubInterface) error {
	//创建用户表
	err := stub.CreateTable("pointsUser", []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "userId", Type: shim.ColumnDefinition_STRING, Key: true},
		&shim.ColumnDefinition{Name: "userName", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "userPassword", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "phoneNumber", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "userType", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "createTime", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "createUser", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "updateTime", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "updateUser", Type: shim.ColumnDefinition_STRING, Key: false},
	})
	if err != nil {
		return errors.New("creat pointsUser is fail")
	}
	//创建用户行号表
	err = stub.CreateTable("pointsUserNumber", []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "number", Type: shim.ColumnDefinition_INT64, Key: true},
		&shim.ColumnDefinition{Name: "userId", Type: shim.ColumnDefinition_STRING, Key: false},
	})
	if err != nil {
		stub.DeleteTable("pointsUser")
		return errors.New("creat pointsUserNumber is fail")
	}
	//创建账户表
	err = stub.CreateTable("account", []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "accountId", Type: shim.ColumnDefinition_STRING, Key: true},
		&shim.ColumnDefinition{Name: "userId", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "accountbalance", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "accountTypeId", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "status", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "createTime", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "createUser", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "updateTime", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "updateUser", Type: shim.ColumnDefinition_STRING, Key: false},
	})
	if err != nil {
		stub.DeleteTable("pointsUser")
		stub.DeleteTable("pointsUserNumber")
		return errors.New("creat account is fail")
	}
	//创建账户行号表
	err = stub.CreateTable("accountNumber", []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "number", Type: shim.ColumnDefinition_INT64, Key: true},
		&shim.ColumnDefinition{Name: "accountId", Type: shim.ColumnDefinition_STRING, Key: false},
	})
	if err != nil {
		stub.DeleteTable("pointsUser")
		stub.DeleteTable("pointsUserNumber")
		stub.DeleteTable("account")
		return errors.New("creat accountNumber is fail")
	}
	//创建账户类型表
	err = stub.CreateTable("accountType", []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "accountTypeId", Type: shim.ColumnDefinition_STRING, Key: true},
		&shim.ColumnDefinition{Name: "describe", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "createTime", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "createUser", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "updateTime", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "updateUser", Type: shim.ColumnDefinition_STRING, Key: false},
	})
	if err != nil {
		stub.DeleteTable("pointsUser")
		stub.DeleteTable("pointsUserNumber")
		stub.DeleteTable("account")
		stub.DeleteTable("accountNumber")
		return errors.New("creat accountType is fail")
	}
	//创建账户类型行号表
	err = stub.CreateTable("accountTypeNumber", []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "number", Type: shim.ColumnDefinition_INT64, Key: true},
		&shim.ColumnDefinition{Name: "accountTypeId", Type: shim.ColumnDefinition_STRING, Key: false},
	})
	if err != nil {
		stub.DeleteTable("pointsUser")
		stub.DeleteTable("pointsUserNumber")
		stub.DeleteTable("account")
		stub.DeleteTable("accountNumber")
		stub.DeleteTable("accountType")
		return errors.New("creat accountTypeNumber is fail")
	}
	//创建积分交易表
	err = stub.CreateTable("pointsTransation", []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "transId", Type: shim.ColumnDefinition_STRING, Key: true},
		&shim.ColumnDefinition{Name: "rollOutAccount", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "rollInAccount", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "transAmount", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "describe", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "transferTime", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "createTime", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "createUser", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "updateTime", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "updateUser", Type: shim.ColumnDefinition_STRING, Key: false},
	})
	if err != nil {
		stub.DeleteTable("pointsUser")
		stub.DeleteTable("pointsUserNumber")
		stub.DeleteTable("account")
		stub.DeleteTable("accountNumber")
		stub.DeleteTable("accountType")
		stub.DeleteTable("accountTypeNumber")
		return errors.New("creat pointsTransation is fail")
	}
	//创建积分交易行号表
	err = stub.CreateTable("pointsTransationNumber", []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "number", Type: shim.ColumnDefinition_INT64, Key: true},
		&shim.ColumnDefinition{Name: "transId", Type: shim.ColumnDefinition_STRING, Key: false},
	})
	if err != nil {
		stub.DeleteTable("pointsUser")
		stub.DeleteTable("pointsUserNumber")
		stub.DeleteTable("account")
		stub.DeleteTable("accountNumber")
		stub.DeleteTable("accountType")
		stub.DeleteTable("accountTypeNumber")
		stub.DeleteTable("pointsTransation")
		return errors.New("creat pointsTransationNumber is fail")
	}
	//创建配置信息类别表
	err = stub.CreateTable("configureCategory", []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "categoryId", Type: shim.ColumnDefinition_STRING, Key: true},
		&shim.ColumnDefinition{Name: "categoryName", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "createTime", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "createUser", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "updateTime", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "updateUser", Type: shim.ColumnDefinition_STRING, Key: false},
	})
	if err != nil {
		stub.DeleteTable("pointsUser")
		stub.DeleteTable("pointsUserNumber")
		stub.DeleteTable("account")
		stub.DeleteTable("accountNumber")
		stub.DeleteTable("accountType")
		stub.DeleteTable("accountTypeNumber")
		stub.DeleteTable("pointsTransation")
		stub.DeleteTable("pointsTransationNumber")
		return errors.New("creat configureCategory is fail")
	}
	//创建配置信息类别行号表
	err = stub.CreateTable("configureCategoryNumber", []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "number", Type: shim.ColumnDefinition_INT64, Key: true},
		&shim.ColumnDefinition{Name: "categoryId", Type: shim.ColumnDefinition_STRING, Key: false},
	})
	if err != nil {
		stub.DeleteTable("pointsUser")
		stub.DeleteTable("pointsUserNumber")
		stub.DeleteTable("account")
		stub.DeleteTable("accountNumber")
		stub.DeleteTable("accountType")
		stub.DeleteTable("accountTypeNumber")
		stub.DeleteTable("pointsTransation")
		stub.DeleteTable("pointsTransationNumber")
		stub.DeleteTable("configureCategory")
		return errors.New("creat configureCategoryNumber is fail")
	}
	//创建配置信息明细表
	err = stub.CreateTable("configureDetail", []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "detailId", Type: shim.ColumnDefinition_STRING, Key: true},
		&shim.ColumnDefinition{Name: "detailName", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "categoryId", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "describe", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "createTime", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "createUser", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "updateTime", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "updateUser", Type: shim.ColumnDefinition_STRING, Key: false},
	})
	if err != nil {
		stub.DeleteTable("pointsUser")
		stub.DeleteTable("pointsUserNumber")
		stub.DeleteTable("account")
		stub.DeleteTable("accountNumber")
		stub.DeleteTable("accountType")
		stub.DeleteTable("accountTypeNumber")
		stub.DeleteTable("pointsTransation")
		stub.DeleteTable("pointsTransationNumber")
		stub.DeleteTable("configureCategory")
		stub.DeleteTable("configureCategoryNumber")
		return errors.New("creat configureDetail is fail")
	}
	//创建配置信息明细行号表
	err = stub.CreateTable("configureDetailNumber", []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "number", Type: shim.ColumnDefinition_INT64, Key: true},
		&shim.ColumnDefinition{Name: "detailId", Type: shim.ColumnDefinition_STRING, Key: false},
	})
	if err != nil {
		stub.DeleteTable("pointsUser")
		stub.DeleteTable("pointsUserNumber")
		stub.DeleteTable("account")
		stub.DeleteTable("accountNumber")
		stub.DeleteTable("accountType")
		stub.DeleteTable("accountTypeNumber")
		stub.DeleteTable("pointsTransation")
		stub.DeleteTable("pointsTransationNumber")
		stub.DeleteTable("configureCategory")
		stub.DeleteTable("configureCategoryNumber")
		stub.DeleteTable("configureDetail")
		return errors.New("creat configureDetailNumber is fail")
	}
	//创建积分交易逐笔明细表
	err = stub.CreateTable("pointsTransationDetail", []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "detailId", Type: shim.ColumnDefinition_STRING, Key: true},
		&shim.ColumnDefinition{Name: "sourceDetailId", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "transId", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "expireTime", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "extRef", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "status", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "createTime", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "createUser", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "updateTime", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "updateUser", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "rollOuAccount", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "rollInAccount", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "transAmount", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "curBalance", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "creditParty", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "merchant", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "transferTime", Type: shim.ColumnDefinition_STRING, Key: false},
	})
	if err != nil {
		stub.DeleteTable("pointsUser")
		stub.DeleteTable("pointsUserNumber")
		stub.DeleteTable("account")
		stub.DeleteTable("accountNumber")
		stub.DeleteTable("accountType")
		stub.DeleteTable("accountTypeNumber")
		stub.DeleteTable("pointsTransation")
		stub.DeleteTable("pointsTransationNumber")
		stub.DeleteTable("configureCategory")
		stub.DeleteTable("configureCategoryNumber")
		stub.DeleteTable("configureDetail")
		stub.DeleteTable("configureDetailNumber")
		return errors.New("creat pointsTransationDetail is fail")
	}
	//创建积分交易逐笔明细行号表
	err = stub.CreateTable("pointsTransationDetailNumber", []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "number", Type: shim.ColumnDefinition_INT64, Key: true},
		&shim.ColumnDefinition{Name: "detailId", Type: shim.ColumnDefinition_STRING, Key: false},
	})
	if err != nil {
		stub.DeleteTable("pointsUser")
		stub.DeleteTable("pointsUserNumber")
		stub.DeleteTable("account")
		stub.DeleteTable("accountNumber")
		stub.DeleteTable("accountType")
		stub.DeleteTable("accountTypeNumber")
		stub.DeleteTable("pointsTransation")
		stub.DeleteTable("pointsTransationNumber")
		stub.DeleteTable("configureCategory")
		stub.DeleteTable("configureCategoryNumber")
		stub.DeleteTable("configureDetail")
		stub.DeleteTable("configureDetailNumber")
		stub.DeleteTable("pointsTransationDetail")
		return errors.New("creat pointsTransationDetailNumber is fail")
	}
	//创建总数表
	err = stub.CreateTable("totalNumber", []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "tableName", Type: shim.ColumnDefinition_STRING, Key: true},
		&shim.ColumnDefinition{Name: "totalAccount", Type: shim.ColumnDefinition_INT64, Key: false},
	})
	if err != nil {
		stub.DeleteTable("pointsUser")
		stub.DeleteTable("pointsUserNumber")
		stub.DeleteTable("account")
		stub.DeleteTable("accountNumber")
		stub.DeleteTable("accountType")
		stub.DeleteTable("accountTypeNumber")
		stub.DeleteTable("pointsTransation")
		stub.DeleteTable("pointsTransationNumber")
		stub.DeleteTable("configureCategory")
		stub.DeleteTable("configureCategoryNumber")
		stub.DeleteTable("configureDetail")
		stub.DeleteTable("configureDetailNumber")
		stub.DeleteTable("pointsTransationDetail")
		stub.DeleteTable("pointsTransationDetailNumber")
		return errors.New("creat totalNumber is fail")
	}
	return nil
}
