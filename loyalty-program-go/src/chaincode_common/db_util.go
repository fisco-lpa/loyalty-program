package chaincode_common

import (
	"errors"

	"github.com/hyperledger/fabric/core/chaincode/shim"
)

const (
	Points_User                     = "points_user"
	Points_User_Rownum              = "points_user_rownum"
	Account                         = "account"
	Account_Rownum                  = "account_rownum"
	Account_Type                    = "account_type"
	Account_Type_Rownum             = "account_type_rownum"
	Points_Transation               = "points_transation"
	Points_Transation_Rownum        = "points_transation_rownum"
	Configure_Category              = "configure_category"
	Configure_Category_Rownum       = "configure_category_rownum"
	Configure_Detail                = "configure_detail"
	Configure_Detail_Rownum         = "configure_detail_rownum"
	Points_Transation_Detail        = "points_transation_detail"
	Points_Transation_Detail_Rownum = "points_transation_detail_rownum"
	Table_Count                     = "table_count"
)

//创建表
func CreateTable(stub shim.ChaincodeStubInterface) error {
	//创建用户表
	err := stub.CreateTable(Points_User, []*shim.ColumnDefinition{
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
	err = stub.CreateTable(Points_User_Rownum, []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "number", Type: shim.ColumnDefinition_INT64, Key: true},
		&shim.ColumnDefinition{Name: "userId", Type: shim.ColumnDefinition_STRING, Key: false},
	})
	if err != nil {
		stub.DeleteTable(Points_User)
		return errors.New("creat points_user_rownum is fail")
	}
	//创建账户表
	err = stub.CreateTable(Account, []*shim.ColumnDefinition{
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
		stub.DeleteTable(Points_User)
		stub.DeleteTable(Points_User_Rownum)
		return errors.New("creat account is fail")
	}
	//创建账户行号表(新增)
	err = stub.CreateTable(Account_Rownum, []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "number", Type: shim.ColumnDefinition_INT64, Key: true},
		&shim.ColumnDefinition{Name: "accountId", Type: shim.ColumnDefinition_STRING, Key: false},
	})
	if err != nil {
		stub.DeleteTable(Points_User)
		stub.DeleteTable(Points_User_Rownum)
		stub.DeleteTable(Account)
		return errors.New("creat account_rownum is fail")
	}
	//创建账户类型表
	err = stub.CreateTable(Account_Type, []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "accountTypeId", Type: shim.ColumnDefinition_STRING, Key: true},
		&shim.ColumnDefinition{Name: "describe", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "createTime", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "createUser", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "updateTime", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "updateUser", Type: shim.ColumnDefinition_STRING, Key: false},
	})
	if err != nil {
		stub.DeleteTable(Points_User)
		stub.DeleteTable(Points_User_Rownum)
		stub.DeleteTable(Account)
		stub.DeleteTable(Account_Rownum)
		return errors.New("creat account_type is fail")
	}
	//创建账户类型行号表(新增)
	err = stub.CreateTable(Account_Type_Rownum, []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "number", Type: shim.ColumnDefinition_INT64, Key: true},
		&shim.ColumnDefinition{Name: "accountTypeId", Type: shim.ColumnDefinition_STRING, Key: false},
	})
	if err != nil {
		stub.DeleteTable(Points_User)
		stub.DeleteTable(Points_User_Rownum)
		stub.DeleteTable(Account)
		stub.DeleteTable(Account_Rownum)
		stub.DeleteTable(Account_Type)
		return errors.New("creat account_type_rownum is fail")
	}
	//创建积分交易表
	err = stub.CreateTable(Points_Transation, []*shim.ColumnDefinition{
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
		stub.DeleteTable(Points_User)
		stub.DeleteTable(Points_User_Rownum)
		stub.DeleteTable(Account)
		stub.DeleteTable(Account_Rownum)
		stub.DeleteTable(Account_Type)
		stub.DeleteTable(Account_Type_Rownum)
		return errors.New("creat points_transation is fail")
	}
	//创建积分交易行号表(新增)
	err = stub.CreateTable(Points_Transation_Rownum, []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "number", Type: shim.ColumnDefinition_INT64, Key: true},
		&shim.ColumnDefinition{Name: "transId", Type: shim.ColumnDefinition_STRING, Key: false},
	})
	if err != nil {
		stub.DeleteTable(Points_User)
		stub.DeleteTable(Points_User_Rownum)
		stub.DeleteTable(Account)
		stub.DeleteTable(Account_Rownum)
		stub.DeleteTable(Account_Type)
		stub.DeleteTable(Account_Type_Rownum)
		stub.DeleteTable(Points_Transation)
		return errors.New("creat points_transation_rownum is fail")
	}
	//创建配置信息类别表
	err = stub.CreateTable(Configure_Category, []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "categoryId", Type: shim.ColumnDefinition_STRING, Key: true},
		&shim.ColumnDefinition{Name: "categoryName", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "createTime", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "createUser", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "updateTime", Type: shim.ColumnDefinition_STRING, Key: false},
		&shim.ColumnDefinition{Name: "updateUser", Type: shim.ColumnDefinition_STRING, Key: false},
	})
	if err != nil {
		stub.DeleteTable(Points_User)
		stub.DeleteTable(Points_User_Rownum)
		stub.DeleteTable(Account)
		stub.DeleteTable(Account_Rownum)
		stub.DeleteTable(Account_Type)
		stub.DeleteTable(Account_Type_Rownum)
		stub.DeleteTable(Points_Transation)
		stub.DeleteTable(Points_Transation_Rownum)
		return errors.New("creat configure_category is fail")
	}
	//创建配置信息类别行号表(新增)
	err = stub.CreateTable(Configure_Category_Rownum, []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "number", Type: shim.ColumnDefinition_INT64, Key: true},
		&shim.ColumnDefinition{Name: "categoryId", Type: shim.ColumnDefinition_STRING, Key: false},
	})
	if err != nil {
		stub.DeleteTable(Points_User)
		stub.DeleteTable(Points_User_Rownum)
		stub.DeleteTable(Account)
		stub.DeleteTable(Account_Rownum)
		stub.DeleteTable(Account_Type)
		stub.DeleteTable(Account_Type_Rownum)
		stub.DeleteTable(Points_Transation)
		stub.DeleteTable(Points_Transation_Rownum)
		stub.DeleteTable(Configure_Category)
		return errors.New("creat configure_category_rownum is fail")
	}
	//创建配置信息明细表
	err = stub.CreateTable(Configure_Detail, []*shim.ColumnDefinition{
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
		stub.DeleteTable(Points_User)
		stub.DeleteTable(Points_User_Rownum)
		stub.DeleteTable(Account)
		stub.DeleteTable(Account_Rownum)
		stub.DeleteTable(Account_Type)
		stub.DeleteTable(Account_Type_Rownum)
		stub.DeleteTable(Points_Transation)
		stub.DeleteTable(Points_Transation_Rownum)
		stub.DeleteTable(Configure_Category)
		stub.DeleteTable(Configure_Category_Rownum)
		return errors.New("creat configure_detail is fail")
	}
	//创建配置信息明细行号表(新增)
	err = stub.CreateTable(Configure_Detail_Rownum, []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "number", Type: shim.ColumnDefinition_INT64, Key: true},
		&shim.ColumnDefinition{Name: "detailId", Type: shim.ColumnDefinition_STRING, Key: false},
	})
	if err != nil {
		stub.DeleteTable(Points_User)
		stub.DeleteTable(Points_User_Rownum)
		stub.DeleteTable(Account)
		stub.DeleteTable(Account_Rownum)
		stub.DeleteTable(Account_Type)
		stub.DeleteTable(Account_Type_Rownum)
		stub.DeleteTable(Points_Transation)
		stub.DeleteTable(Points_Transation_Rownum)
		stub.DeleteTable(Configure_Category)
		stub.DeleteTable(Configure_Category_Rownum)
		stub.DeleteTable(Configure_Detail)
		return errors.New("creat configure_detail_rownum is fail")
	}
	//创建积分交易逐笔明细表
	err = stub.CreateTable(Points_Transation_Detail, []*shim.ColumnDefinition{
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
		stub.DeleteTable(Points_User)
		stub.DeleteTable(Points_User_Rownum)
		stub.DeleteTable(Account)
		stub.DeleteTable(Account_Rownum)
		stub.DeleteTable(Account_Type)
		stub.DeleteTable(Account_Type_Rownum)
		stub.DeleteTable(Points_Transation)
		stub.DeleteTable(Points_Transation_Rownum)
		stub.DeleteTable(Configure_Category)
		stub.DeleteTable(Configure_Category_Rownum)
		stub.DeleteTable(Configure_Detail)
		stub.DeleteTable(Configure_Detail_Rownum)
		return errors.New("creat points_transation_detail is fail")
	}
	//创建积分交易逐笔明细行号表(新增)
	err = stub.CreateTable(Points_Transation_Detail_Rownum, []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "number", Type: shim.ColumnDefinition_INT64, Key: true},
		&shim.ColumnDefinition{Name: "detailId", Type: shim.ColumnDefinition_STRING, Key: false},
	})
	if err != nil {
		stub.DeleteTable(Points_User)
		stub.DeleteTable(Points_User_Rownum)
		stub.DeleteTable(Account)
		stub.DeleteTable(Account_Rownum)
		stub.DeleteTable(Account_Type)
		stub.DeleteTable(Account_Type_Rownum)
		stub.DeleteTable(Points_Transation)
		stub.DeleteTable(Points_Transation_Rownum)
		stub.DeleteTable(Configure_Category)
		stub.DeleteTable(Configure_Category_Rownum)
		stub.DeleteTable(Configure_Detail)
		stub.DeleteTable(Configure_Detail_Rownum)
		stub.DeleteTable(Points_Transation_Detail)
		return errors.New("creat points_transation_detail_rownum is fail")
	}
	//创建总数表
	err = stub.CreateTable(Table_Count, []*shim.ColumnDefinition{
		&shim.ColumnDefinition{Name: "tableName", Type: shim.ColumnDefinition_STRING, Key: true},
		&shim.ColumnDefinition{Name: "totalAccount", Type: shim.ColumnDefinition_INT64, Key: false},
	})
	if err != nil {
		stub.DeleteTable(Points_User)
		stub.DeleteTable(Points_User_Rownum)
		stub.DeleteTable(Account)
		stub.DeleteTable(Account_Rownum)
		stub.DeleteTable(Account_Type)
		stub.DeleteTable(Account_Type_Rownum)
		stub.DeleteTable(Points_Transation)
		stub.DeleteTable(Points_Transation_Rownum)
		stub.DeleteTable(Configure_Category)
		stub.DeleteTable(Configure_Category_Rownum)
		stub.DeleteTable(Configure_Detail)
		stub.DeleteTable(Configure_Detail_Rownum)
		stub.DeleteTable(Points_Transation_Detail)
		stub.DeleteTable(Points_Transation_Detail_Rownum)
		return errors.New("creat table_count is fail")
	}
	return nil
}
