package user

import (
	"errors"

	"util"

	"github.com/hyperledger/fabric/core/chaincode/shim"
)

//注册
type PointsUser struct {
	UserId       string //用户ID
	UserName     string //用户名称
	UserPassword string //用户密码
	PhoneNumber  string //手机号
	UserType     string //用户类型 0.授信，1.商户,2.会员
	CreateTime   string //创建时间
	CreateUser   string //创建人
	UpdateTime   string //修改时间
	UpdateUser   string //修改人
	//AuditObj    util.AuditObject
}

//用户信息录入（ 0.授信，1.商户,2.会员）
func InsertPointsUser(stub shim.ChaincodeStubInterface, data PointsUser) ([]byte, error) {

	//插入用户表
	ok, err := stub.InsertRow(util.Points_User, shim.Row{
		Columns: []*shim.Column{
			&shim.Column{Value: &shim.Column_String_{String_: data.UserId}},       //用户ID
			&shim.Column{Value: &shim.Column_String_{String_: data.UserName}},     //用户名称
			&shim.Column{Value: &shim.Column_String_{String_: data.UserPassword}}, //用户密码
			&shim.Column{Value: &shim.Column_String_{String_: data.PhoneNumber}},  //手机号
			&shim.Column{Value: &shim.Column_String_{String_: data.UserType}},     //用户类型 0.授信，1.商户,2.会员
			&shim.Column{Value: &shim.Column_String_{String_: data.CreateTime}},   //创建时间
			&shim.Column{Value: &shim.Column_String_{String_: data.CreateUser}},   //创建人
			&shim.Column{Value: &shim.Column_String_{String_: data.UpdateTime}},   //修改时间
			&shim.Column{Value: &shim.Column_String_{String_: data.UpdateUser}}},  //修改人
	})
	if !ok && err == nil {
		return nil, errors.New("Points_User insert failed.")
	}

	//更新或者插入table_count表
	totalNumber, err := util.UpdateTableCount(stub, util.Points_User)
	if totalNumber == 0 || err != nil {
		stub.DeleteRow(util.Points_User, []shim.Column{shim.Column{Value: &shim.Column_String_{String_: data.UserId}}})
		return nil, errors.New("InsertPointsUser Table_Count insert failed")
	}
	//把获取的总数插入行号表中当主键
	err = util.UpdateRowNoTable(stub, util.Points_User_Rownum, data.UserId, totalNumber)
	if err != nil {
		stub.DeleteRow(util.Points_User, []shim.Column{shim.Column{Value: &shim.Column_String_{String_: data.UserId}}})
		var columns []shim.Column
		col := shim.Column{Value: &shim.Column_String_{String_: util.Points_User}}
		columns = append(columns, col)
		row, _ := stub.GetRow(util.Table_Count, columns) //row是否为空
		if len(row.Columns) == 1 {
			stub.DeleteRow(util.Table_Count, []shim.Column{shim.Column{Value: &shim.Column_String_{String_: util.Points_User}}})
		} else {
			stub.ReplaceRow(util.Table_Count, shim.Row{
				Columns: []*shim.Column{
					&shim.Column{Value: &shim.Column_String_{String_: util.Points_User}}, //表名
					&shim.Column{Value: &shim.Column_Int64{Int64: totalNumber - 1}}},     //总数
			})
		}
		return nil, errors.New("InsertPointsUser insert Points_User_Rownum failed")
	}
	return nil, nil
}
