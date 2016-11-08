package chaincode_points

import (
	"chaincode_common"
	"encoding/base64"
	"encoding/json"
	"errors"

	"github.com/hyperledger/fabric/core/chaincode/shim"
)

//积分交易记录对象
type PointsTransaction struct {
	TransId        string //积分交易ID
	RolloutAccount string //转出账户
	RollinAccount  string //转入账户
	TransAmount    string //交易积分数量
	Description    string //描述
	TransferTime   string //交易时间
	TransferType   string //交易类型
	CreateTime     string //创建时间
	CreateUser     string //创建人
	UpdateTime     string //修改时间
	UpdateUser     string //修改人
}

func InsertPointsTransation(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {
	var transObject PointsTransaction
	//base解码
	arg, err := base64.StdEncoding.DecodeString(args[0])
	if err != nil {
		return nil, errors.New("InsertPointsTransation method base64 decoding error.")
	}
	//解析
	err = json.Unmarshal(arg, &transObject)
	if err != nil {
		return nil, errors.New("InsertPointsTransation method json Parse error.")
	}
	//插入积分交易记录
	ok, err := stub.InsertRow(chaincode_common.Points_User, shim.Row{
		Columns: []*shim.Column{
			&shim.Column{Value: &shim.Column_String_{String_: transObject.TransId}},
			&shim.Column{Value: &shim.Column_String_{String_: transObject.RolloutAccount}},
			&shim.Column{Value: &shim.Column_String_{String_: transObject.RollinAccount}},
			&shim.Column{Value: &shim.Column_String_{String_: transObject.Description}},
			&shim.Column{Value: &shim.Column_String_{String_: transObject.TransferTime}},
			&shim.Column{Value: &shim.Column_String_{String_: transObject.TransferType}},
			&shim.Column{Value: &shim.Column_String_{String_: transObject.CreateTime}},
			&shim.Column{Value: &shim.Column_String_{String_: transObject.CreateUser}},
			&shim.Column{Value: &shim.Column_String_{String_: transObject.UpdateTime}},
			&shim.Column{Value: &shim.Column_String_{String_: transObject.UpdateUser}}},
	})
	if !ok && err == nil {
		return nil, errors.New("Points_Transaction insert failed.")
	}
	//判断总数表表是否有数据，若有数据则查出来+1
	var columns []shim.Column
	col := shim.Column{Value: &shim.Column_String_{String_: chaincode_common.Points_Transation}}
	columns = append(columns, col)
	row, _ := stub.GetRow(chaincode_common.Table_Count, columns) //row是否为空
	totalNumber := row.Columns[1].GetInt64()
	if len(row.Columns) == 0 {
		//若没有数据，则插入总数表一条记录
		totalNumber = 1
		ok, err := stub.InsertRow(chaincode_common.Table_Count, shim.Row{
			Columns: []*shim.Column{
				&shim.Column{Value: &shim.Column_String_{String_: chaincode_common.Points_Transation}}, //表名
				&shim.Column{Value: &shim.Column_Int64{Int64: totalNumber}}},                           //总数
		})
		if !ok && err == nil {
			return nil, errors.New("Total_Count insert failed.")
		}
	} else {
		//若有数据，则更新总数
		totalNumber = totalNumber + 1
		ok, err := stub.ReplaceRow(chaincode_common.Table_Count, shim.Row{
			Columns: []*shim.Column{
				&shim.Column{Value: &shim.Column_String_{String_: chaincode_common.Points_Transation}}, //表名
				&shim.Column{Value: &shim.Column_Int64{Int64: totalNumber}}},                           //总数
		})
		if !ok && err == nil {
			return nil, errors.New("update Table_Count failed.")
		}
	}
	//把获取的总数插入行号表中当主键
	ok, err = stub.InsertRow(chaincode_common.Points_User_Rownum, shim.Row{
		Columns: []*shim.Column{
			&shim.Column{Value: &shim.Column_Int64{Int64: totalNumber}},              //行号
			&shim.Column{Value: &shim.Column_String_{String_: transObject.TransId}}}, //数据表主键
	})
	if !ok && err == nil {
		return nil, errors.New("Points_Transaction_Rownum insert failed")
	}
	return nil, nil
}

func InsertPointsTransationDetail(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {

	return nil, nil
}

func UpdatePointsTransationDetail(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {

	return nil, nil
}
