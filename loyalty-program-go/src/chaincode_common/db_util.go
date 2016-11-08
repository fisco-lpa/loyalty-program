package chaincode_common

import (
	"errors"

	"github.com/hyperledger/fabric/core/chaincode/shim"
)

const (
	points_user                     = "points_user"
	points_user_rownum              = "points_user_rownum"
	account                         = "account"
	account_rownum                  = "account_rownum"
	account_type                    = "account_type"
	account_type_rownum             = "account_type_rownum"
	points_transation               = "points_transation"
	points_transation_rownum        = "points_transation_rownum"
	configure_category              = "configure_category"
	configure_category_rownum       = "configure_category_rownum"
	configure_detail                = "configure_detail"
	configure_detail_rownum         = "configure_detail_rownum"
	points_transation_detail        = "points_transation_detail"
	points_transation_detail_rownum = "points_transation_detail_rownum"
	table_count                     = "table_count"
)

//创建表
func CreateTable(stub shim.ChaincodeStubInterface) error {
	//创建用户表
	err := stub.CreateTable("points_user", []*shim.ColumnDefinition{
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
		return errors.New("creat points_user is fail")
	}
	//创建用户行号表(新增)
	err = stub.CreateTable("points_user_rownum", []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "number", Type: shim.ColumnDefinition_INT64, Key: true},
		&shim.ColumnDefinition{Name: "userId", Type: shim.ColumnDefinition_STRING, Key: false},
	})
	if err != nil {
		stub.DeleteTable("points_user")
		return errors.New("creat points_user_rownum is fail")
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
		stub.DeleteTable("points_user")
		stub.DeleteTable("points_user_rownum")
		return errors.New("creat account is fail")
	}
	//创建账户行号表(新增)
	err = stub.CreateTable("account_rownum", []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "number", Type: shim.ColumnDefinition_INT64, Key: true},
		&shim.ColumnDefinition{Name: "accountId", Type: shim.ColumnDefinition_STRING, Key: false},
	})
	if err != nil {
		stub.DeleteTable("points_user")
		stub.DeleteTable("points_user_rownum")
		stub.DeleteTable("account")
		return errors.New("creat account_rownum is fail")
	}
	//创建账户类型表
	err = stub.CreateTable("account_type", []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "accountTypeId", Type: shim.ColumnDefinition_STRING, Key: true},
		&shim.ColumnDefinition{Name: "describe", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "createTime", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "createUser", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "updateTime", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "updateUser", Type: shim.ColumnDefinition_STRING, Key: false},
	})
	if err != nil {
		stub.DeleteTable("points_user")
		stub.DeleteTable("points_user_rownum")
		stub.DeleteTable("account")
		stub.DeleteTable("account_rownum")
		return errors.New("creat account_type is fail")
	}
	//创建账户类型行号表(新增)
	err = stub.CreateTable("account_type_rownum", []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "number", Type: shim.ColumnDefinition_INT64, Key: true},
		&shim.ColumnDefinition{Name: "accountTypeId", Type: shim.ColumnDefinition_STRING, Key: false},
	})
	if err != nil {
		stub.DeleteTable("points_user")
		stub.DeleteTable("points_user_rownum")
		stub.DeleteTable("account")
		stub.DeleteTable("account_rownum")
		stub.DeleteTable("account_type")
		return errors.New("creat account_type_rownum is fail")
	}
	//创建积分交易表
	err = stub.CreateTable("points_transation", []*shim.ColumnDefinition{
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
		stub.DeleteTable("points_user")
		stub.DeleteTable("points_user_rownum")
		stub.DeleteTable("account")
		stub.DeleteTable("account_rownum")
		stub.DeleteTable("account_type")
		stub.DeleteTable("account_type_rownum")
		return errors.New("creat points_transation is fail")
	}
	//创建积分交易行号表(新增)
	err = stub.CreateTable("points_transation_rownum", []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "number", Type: shim.ColumnDefinition_INT64, Key: true},
		&shim.ColumnDefinition{Name: "transId", Type: shim.ColumnDefinition_STRING, Key: false},
	})
	if err != nil {
		stub.DeleteTable("points_user")
		stub.DeleteTable("points_user_rownum")
		stub.DeleteTable("account")
		stub.DeleteTable("account_rownum")
		stub.DeleteTable("account_type")
		stub.DeleteTable("account_type_rownum")
		stub.DeleteTable("points_transation")
		return errors.New("creat points_transation_rownum is fail")
	}
	//创建配置信息类别表
	err = stub.CreateTable("configure_category", []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "categoryId", Type: shim.ColumnDefinition_STRING, Key: true},
		&shim.ColumnDefinition{Name: "categoryName", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "createTime", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "createUser", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "updateTime", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "updateUser", Type: shim.ColumnDefinition_STRING, Key: false},
	})
	if err != nil {
		stub.DeleteTable("points_user")
		stub.DeleteTable("points_user_rownum")
		stub.DeleteTable("account")
		stub.DeleteTable("account_rownum")
		stub.DeleteTable("account_type")
		stub.DeleteTable("account_type_rownum")
		stub.DeleteTable("points_transation")
		stub.DeleteTable("points_transation_rownum")
		return errors.New("creat configure_category is fail")
	}
	//创建配置信息类别行号表(新增)
	err = stub.CreateTable("configure_category_rownum", []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "number", Type: shim.ColumnDefinition_INT64, Key: true},
		&shim.ColumnDefinition{Name: "categoryId", Type: shim.ColumnDefinition_STRING, Key: false},
	})
	if err != nil {
		stub.DeleteTable("points_user")
		stub.DeleteTable("points_user_rownum")
		stub.DeleteTable("account")
		stub.DeleteTable("account_rownum")
		stub.DeleteTable("account_type")
		stub.DeleteTable("account_type_rownum")
		stub.DeleteTable("points_transation")
		stub.DeleteTable("points_transation_rownum")
		stub.DeleteTable("configure_category")
		return errors.New("creat configure_category_rownum is fail")
	}
	//创建配置信息明细表
	err = stub.CreateTable("configure_detail", []*shim.ColumnDefinition{
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
		stub.DeleteTable("points_user")
		stub.DeleteTable("points_user_rownum")
		stub.DeleteTable("account")
		stub.DeleteTable("account_rownum")
		stub.DeleteTable("account_type")
		stub.DeleteTable("account_type_rownum")
		stub.DeleteTable("points_transation")
		stub.DeleteTable("points_transation_rownum")
		stub.DeleteTable("configure_category")
		stub.DeleteTable("configure_category_rownum")
		return errors.New("creat configure_detail is fail")
	}
	//创建配置信息明细行号表(新增)
	err = stub.CreateTable("configure_detail_rownum", []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "number", Type: shim.ColumnDefinition_INT64, Key: true},
		&shim.ColumnDefinition{Name: "detailId", Type: shim.ColumnDefinition_STRING, Key: false},
	})
	if err != nil {
		stub.DeleteTable("points_user")
		stub.DeleteTable("points_user_rownum")
		stub.DeleteTable("account")
		stub.DeleteTable("account_rownum")
		stub.DeleteTable("account_type")
		stub.DeleteTable("account_type_rownum")
		stub.DeleteTable("points_transation")
		stub.DeleteTable("points_transation_rownum")
		stub.DeleteTable("configure_category")
		stub.DeleteTable("configure_category_rownum")
		stub.DeleteTable("configure_detail")
		return errors.New("creat configure_detail_rownum is fail")
	}
	//创建积分交易逐笔明细表
	err = stub.CreateTable("points_transation_detail", []*shim.ColumnDefinition{
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
		stub.DeleteTable("points_user")
		stub.DeleteTable("points_user_rownum")
		stub.DeleteTable("account")
		stub.DeleteTable("account_rownum")
		stub.DeleteTable("account_type")
		stub.DeleteTable("account_type_rownum")
		stub.DeleteTable("points_transation")
		stub.DeleteTable("points_transation_rownum")
		stub.DeleteTable("configure_category")
		stub.DeleteTable("configure_category_rownum")
		stub.DeleteTable("configure_detail")
		stub.DeleteTable("configure_detail_rownum")
		return errors.New("creat points_transation_detail is fail")
	}
	//创建积分交易逐笔明细行号表(新增)
	err = stub.CreateTable("points_transation_detail_rownum", []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "number", Type: shim.ColumnDefinition_INT64, Key: true},
		&shim.ColumnDefinition{Name: "detailId", Type: shim.ColumnDefinition_STRING, Key: false},
	})
	if err != nil {
		stub.DeleteTable("points_user")
		stub.DeleteTable("points_user_rownum")
		stub.DeleteTable("account")
		stub.DeleteTable("account_rownum")
		stub.DeleteTable("account_type")
		stub.DeleteTable("account_type_rownum")
		stub.DeleteTable("points_transation")
		stub.DeleteTable("points_transation_rownum")
		stub.DeleteTable("configure_category")
		stub.DeleteTable("configure_category_rownum")
		stub.DeleteTable("configure_detail")
		stub.DeleteTable("configure_detail_rownum")
		stub.DeleteTable("points_transation_detail")
		return errors.New("creat points_transation_detail_rownum is fail")
	}
	//创建总数表
	err = stub.CreateTable("table_count", []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "tableName", Type: shim.ColumnDefinition_STRING, Key: true},
		&shim.ColumnDefinition{Name: "totalAccount", Type: shim.ColumnDefinition_INT64, Key: false},
	})
	if err != nil {
		stub.DeleteTable("points_user")
		stub.DeleteTable("points_user_rownum")
		stub.DeleteTable("account")
		stub.DeleteTable("account_rownum")
		stub.DeleteTable("account_type")
		stub.DeleteTable("account_type_rownum")
		stub.DeleteTable("points_transation")
		stub.DeleteTable("points_transation_rownum")
		stub.DeleteTable("configure_category")
		stub.DeleteTable("configure_category_rownum")
		stub.DeleteTable("configure_detail")
		stub.DeleteTable("configure_detail_rownum")
		stub.DeleteTable("points_transation_detail")
		stub.DeleteTable("points_transation_detail_rownum")
		return errors.New("creat table_count is fail")
	}
	return nil
}
