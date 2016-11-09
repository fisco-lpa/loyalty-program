package user

import (
	"encoding/base64"
	"encoding/json"
	"errors"
	"util"

	"github.com/hyperledger/fabric/core/chaincode/shim"
)

//注册
type PointsUser struct {
	UserId      string //用户ID
	UserName    string //用户名称
	PhoneNumber string //手机号
	UserType    string //用户类型 0.授信，1.商户,2.会员
	CreateTime  string //创建时间
	CreateUser  string //创建人
	UpdateTime  string //修改时间
	UpdateUser  string //修改人
}

//用户信息录入（ 0.授信，1.商户,2.会员）
func InsertPointsUser(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {
	var data PointsUser
	//base解码
	arg, err := base64.StdEncoding.DecodeString(args[0])
	if err != nil {
		return nil, errors.New("InsertPointsUser method base64 decoding error.")
	}
	//解析
	err = json.Unmarshal(arg, &data)
	if err != nil {
		return nil, errors.New("InsertPointsUser method json Parse error.")
	}
	//插入用户表
	ok, err := stub.InsertRow(util.Points_User, shim.Row{
		Columns: []*shim.Column{
			&shim.Column{Value: &shim.Column_String_{String_: data.UserId}},      //用户ID
			&shim.Column{Value: &shim.Column_String_{String_: data.UserName}},    //用户名称
			&shim.Column{Value: &shim.Column_String_{String_: data.PhoneNumber}}, //手机号
			&shim.Column{Value: &shim.Column_String_{String_: data.UserType}},    //用户类型 0.授信，1.商户,2.会员
			&shim.Column{Value: &shim.Column_String_{String_: data.CreateTime}},  //创建时间
			&shim.Column{Value: &shim.Column_String_{String_: data.CreateUser}},  //创建人
			&shim.Column{Value: &shim.Column_String_{String_: data.UpdateTime}},  //修改时间
			&shim.Column{Value: &shim.Column_String_{String_: data.UpdateUser}}}, //修改人
	})
	if !ok && err == nil {
		return nil, errors.New("Points_User insert failed.")
	}
	//判断总数表表是否有数据，若有数据则查出来+1
	var columns []shim.Column
	col := shim.Column{Value: &shim.Column_String_{String_: "points_user"}}
	columns = append(columns, col)
	row, _ := stub.GetRow(util.Table_Count, columns) //row是否为空
	totalNumber := row.Columns[1].GetInt64()
	if len(row.Columns) == 0 {
		//若没有数据，则插入总数表一条记录
		totalNumber = 1
		ok, err := stub.InsertRow(util.Table_Count, shim.Row{
			Columns: []*shim.Column{
				&shim.Column{Value: &shim.Column_String_{String_: "points_user"}}, //表名
				&shim.Column{Value: &shim.Column_Int64{Int64: totalNumber}}},      //总数
		})
		if !ok && err == nil {
			return nil, errors.New("Total_Count insert failed.")
		}
	} else {
		//若有数据，则更新总数
		totalNumber = totalNumber + 1
		ok, err := stub.ReplaceRow(util.Table_Count, shim.Row{
			Columns: []*shim.Column{
				&shim.Column{Value: &shim.Column_String_{String_: "points_user"}}, //表名
				&shim.Column{Value: &shim.Column_Int64{Int64: totalNumber}}},      //总数
		})
		if !ok && err == nil {
			return nil, errors.New("update Table_Count failed.")
		}
	}
	//把获取的总数插入行号表中当主键
	ok, err = stub.InsertRow(util.Points_User_Rownum, shim.Row{
		Columns: []*shim.Column{
			&shim.Column{Value: &shim.Column_Int64{Int64: totalNumber}},      //行号
			&shim.Column{Value: &shim.Column_String_{String_: data.UserId}}}, //数据表主键
	})
	if !ok && err == nil {
		return nil, errors.New("Points_User_Rownum insert failed")
	}
	return nil, nil
}
