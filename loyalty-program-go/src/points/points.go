package points

import (
	"encoding/base64"
	"encoding/json"
	"errors"
	"log"
	"util"

	"github.com/hyperledger/fabric/core/chaincode/shim"
)

//积分交易记录对象
type PointsTransaction struct {
	TransId        string           //积分交易ID
	RolloutAccount string           //转出账户
	RollinAccount  string           //转入账户
	TransAmount    string           //交易积分数量
	Description    string           //描述
	TransferTime   string           //交易时间
	TransferType   string           //交易类型
	AuditObj       util.AuditObject //audit object
	OperFlag       string           // 操作标积 0-新增，1-修改，2-删除
}

//积分交易明细对象
type PointsTransactionDetail struct {
	DetailId         string           //逐笔明细流水号
	SourceDetailId   string           //来源流水号
	TransId          string           //积分交易ID
	RolloutAccount   string           //转出账户
	RollinAccount    string           //转入账户
	TransAmount      string           //交易积分数量
	ExpireTime       string           //过期时间
	ExtRef           string           //外部引用
	Status           string           // 状态  0-冻结，1-正常
	CurBalance       string           //当笔积分剩余数量
	TransferTime     string           //交易时间
	CreditCreateTime string           //授信创建时间
	CreditParty      string           //授信方账户
	Merchant         string           // 商户账户
	AuditObj         util.AuditObject //audit object
	OperFlag         string           // 操作标积 0-新增，1-修改，2-删除
}

func InsertPointsTransation(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {
	// 解析传入数据
	transObject := new(PointsTransaction)
	err := util.ParseJsonAndDecode(transObject, args)
	if err != nil {
		log.Println("Error occurred when parsing json")
		return nil, errors.New("Error occurred when parsing json.")
	}

	//插入记录到积分交易表
	ok, err := stub.InsertRow(util.Points_Transation, shim.Row{
		Columns: []*shim.Column{
			&shim.Column{Value: &shim.Column_String_{String_: transObject.TransId}},
			&shim.Column{Value: &shim.Column_String_{String_: transObject.RolloutAccount}},
			&shim.Column{Value: &shim.Column_String_{String_: transObject.RollinAccount}},
			&shim.Column{Value: &shim.Column_String_{String_: transObject.Description}},
			&shim.Column{Value: &shim.Column_String_{String_: transObject.TransferTime}},
			&shim.Column{Value: &shim.Column_String_{String_: transObject.TransferType}},
			&shim.Column{Value: &shim.Column_String_{String_: transObject.AuditObj.CreateTime}},
			&shim.Column{Value: &shim.Column_String_{String_: transObject.AuditObj.CreateUser}}},
	})
	if !ok {
		log.Println("InsertRow transObject error..")
		return nil, errors.New("Points_Transaction insertion failed.")
	}

	//更新table_count表
	totalNo, err := util.UpdateTableCount(stub, util.Points_Transation)
	if totalNo == 0 || err != nil {
		log.Println("update table_count error..")
		return nil, errors.New("Total_Count update failed")
	}

	//更新行号表
	err = util.UpdateRowNoTable(stub, util.Points_Transation_Rownum, transObject.TransId, totalNo)

	if err != nil {
		log.Println("update Points_Transaction_Rownum error..")
		return nil, errors.New("Points_Transaction_Rownum insert failed")
	}

	log.Println("InsertPointsTransation sucess!")

	return nil, nil
}

func InsertPointsTransationDetail(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {

	// 解析传入数据
	pointsDetail := new(PointsTransactionDetail)
	err := util.ParseJsonAndDecode(pointsDetail, args)
	if err != nil {
		log.Println("Error occurred when parsing json")
		return nil, errors.New("Error occurred when parsing json.")
	}

	//插入记录到积分交易逐笔流水表
	ok, err := stub.InsertRow(util.Points_Transation, shim.Row{
		Columns: []*shim.Column{
			&shim.Column{Value: &shim.Column_String_{String_: pointsDetail.DetailId}},
			&shim.Column{Value: &shim.Column_String_{String_: pointsDetail.SourceDetailId}},
			&shim.Column{Value: &shim.Column_String_{String_: pointsDetail.TransId}},
			&shim.Column{Value: &shim.Column_String_{String_: pointsDetail.RolloutAccount}},
			&shim.Column{Value: &shim.Column_String_{String_: pointsDetail.RollinAccount}},
			&shim.Column{Value: &shim.Column_String_{String_: pointsDetail.TransAmount}},
			&shim.Column{Value: &shim.Column_String_{String_: pointsDetail.ExpireTime}},
			&shim.Column{Value: &shim.Column_String_{String_: pointsDetail.ExtRef}},
			&shim.Column{Value: &shim.Column_String_{String_: pointsDetail.TransferTime}},
			&shim.Column{Value: &shim.Column_String_{String_: pointsDetail.CurBalance}},
			&shim.Column{Value: &shim.Column_String_{String_: pointsDetail.Merchant}},
			&shim.Column{Value: &shim.Column_String_{String_: pointsDetail.CreditCreateTime}},
			&shim.Column{Value: &shim.Column_String_{String_: pointsDetail.CreditParty}},
			&shim.Column{Value: &shim.Column_String_{String_: pointsDetail.AuditObj.CreateTime}},
			&shim.Column{Value: &shim.Column_String_{String_: pointsDetail.AuditObj.CreateUser}}},
	})
	if !ok {
		log.Println("InsertRow pointsDetail error..")
		return nil, errors.New("Points_Transaction_Detail insertion failed.")
	}

	//更新table_count表
	totalNo, err := util.UpdateTableCount(stub, util.Points_Transation)
	if totalNo == 0 || err != nil {
		log.Println("update table_count error..")
		return nil, errors.New("Total_Count update failed")
	}

	//更新行号表
	err = util.UpdateRowNoTable(stub, util.Points_Transation_Rownum, pointsDetail.DetailId, totalNo)

	if err != nil {
		log.Println("update Points_Transaction_Rownum error..")
		return nil, errors.New("Points_Transaction_Rownum insert failed")
	}

	log.Println("InsertPointsTransationDetail sucess!")

	return nil, nil
}

func UpdatePointsTransationDetail(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {
	// 解析传入数据
	pointsDetail := new(PointsTransactionDetail)
	err := util.ParseJsonAndDecode(pointsDetail, args)
	if err != nil {
		log.Println("Error occurred when parsing json")
		return nil, errors.New("Error occurred when parsing json.")
	}

	// to do:

	return nil, nil
}
