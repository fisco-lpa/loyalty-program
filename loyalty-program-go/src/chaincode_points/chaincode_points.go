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
	TransId        string                       //积分交易ID
	RolloutAccount string                       //转出账户
	RollinAccount  string                       //转入账户
	TransAmount    string                       //交易积分数量
	Description    string                       //描述
	TransferTime   string                       //交易时间
	TransferType   string                       //交易类型
	AuditObj       chaincode_common.AuditObject //audit object
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
	//插入记录到积分交易表
	ok, err := stub.InsertRow(chaincode_common.Points_Transation, shim.Row{
		Columns: []*shim.Column{
			&shim.Column{Value: &shim.Column_String_{String_: transObject.TransId}},
			&shim.Column{Value: &shim.Column_String_{String_: transObject.RolloutAccount}},
			&shim.Column{Value: &shim.Column_String_{String_: transObject.RollinAccount}},
			&shim.Column{Value: &shim.Column_String_{String_: transObject.Description}},
			&shim.Column{Value: &shim.Column_String_{String_: transObject.TransferTime}},
			&shim.Column{Value: &shim.Column_String_{String_: transObject.TransferType}},
			&shim.Column{Value: &shim.Column_String_{String_: transObject.AuditObj.CreateTime}},
			&shim.Column{Value: &shim.Column_String_{String_: transObject.AuditObj.CreateUser}},
			&shim.Column{Value: &shim.Column_String_{String_: transObject.AuditObj.UpdateTime}},
			&shim.Column{Value: &shim.Column_String_{String_: transObject.AuditObj.UpdateUser}}},
	})
	if !ok && err == nil {
		return nil, errors.New("Points_Transaction insertion failed.")
	}

	//更新table_count表
	totalNo, err := chaincode_common.UpdateTableCount(stub, chaincode_common.Points_Transation)
	if totalNo == 0 || err != nil {
		return nil, errors.New("Total_Count insert failed")
	}

	//更新行号表
	err = chaincode_common.UpdateRowNoTable(stub, chaincode_common.Points_Transation_Rownum, totalNo, transObject.TransId)

	if err != nil {
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
