package config

import (
	"errors"
	"log"
	"util"

	"github.com/hyperledger/fabric/core/chaincode/shim"
)

type ConfigType struct { //ConfigDetail
	CategoryId   string //类别ID
	CategoryName string //类别名称
	CreateTime   string //创建时间
	CreateUser   string //创建人
	UpdateTime   string //修改时间
	UpdateUser   string //修改人
}
type ConfigDetail struct { //配置信息明细表信息
	DetailId   string //类别明细ID
	DetailName string //类别明细名称
	CategoryId string //类别ID
	Describe   string //描述
	CreateTime string //创建时间
	CreateUser string //创建人
	UpdateTime string //修改时间
	UpdateUser string //修改人
}

//配置信息类别表信息录入
func InsertConfigureCategory(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {
	data := new(ConfigType)
	err := util.ParseJsonAndDecode(data, args)
	if err != nil {
		log.Println("Error occurred when parsing json")
		return nil, errors.New("Error occurred when parsing json.")
	}
	//插入配置信息类别表
	ok, err := stub.InsertRow(util.Configure_Category, shim.Row{
		Columns: []*shim.Column{
			&shim.Column{Value: &shim.Column_String_{String_: data.CategoryId}},   //类别ID
			&shim.Column{Value: &shim.Column_String_{String_: data.CategoryName}}, //类别名称
			&shim.Column{Value: &shim.Column_String_{String_: data.CreateTime}},   //创建时间
			&shim.Column{Value: &shim.Column_String_{String_: data.CreateUser}},   //创建人
			&shim.Column{Value: &shim.Column_String_{String_: data.UpdateTime}},   //修改时间
			&shim.Column{Value: &shim.Column_String_{String_: data.UpdateUser}}},  //修改人
	})
	if !ok && err == nil {
		return nil, errors.New("insert Configure_Category failure")
	}
	//更新或者插入table_count表
	totalNumber, err := util.UpdateTableCount(stub, util.Configure_Category)
	if totalNumber == 0 || err != nil {
		stub.DeleteRow(util.Configure_Category, []shim.Column{shim.Column{Value: &shim.Column_String_{String_: data.CategoryId}}})
		return nil, errors.New("Table_Count insert failed")
	}
	//把获取的总数插入行号表中当主键
	err = util.UpdateRowNoTable(stub, util.Configure_Category_Rownum, data.CategoryId, totalNumber)
	if err != nil {
		stub.DeleteRow(util.Configure_Category, []shim.Column{shim.Column{Value: &shim.Column_String_{String_: data.CategoryId}}})
		var columns []shim.Column
		col := shim.Column{Value: &shim.Column_String_{String_: util.Configure_Category}}
		columns = append(columns, col)
		row, _ := stub.GetRow(util.Table_Count, columns) //row是否为空
		if len(row.Columns) == 1 {
			stub.DeleteRow(util.Table_Count, []shim.Column{shim.Column{Value: &shim.Column_String_{String_: util.Configure_Category}}})
		} else {
			stub.ReplaceRow(util.Table_Count, shim.Row{
				Columns: []*shim.Column{
					&shim.Column{Value: &shim.Column_String_{String_: util.Configure_Category}}, //表名
					&shim.Column{Value: &shim.Column_Int64{Int64: totalNumber - 1}}},            //总数
			})
		}
		return nil, errors.New("insert Configure_Category_Rownum failed")
	}
	log.Println("InsertConfigureCategory success.")
	return nil, nil
}

//配置信息明细表信息录入
func InsertConfigureDetail(stub shim.ChaincodeStubInterface, args []string) ([]byte, error) {
	data := new(ConfigDetail)
	err := util.ParseJsonAndDecode(data, args)
	if err != nil {
		log.Println("Error occurred when parsing json")
		return nil, errors.New("Error occurred when parsing json.")
	}
	//插入配置信息明细表
	ok, err := stub.InsertRow(util.Configure_Detail, shim.Row{
		Columns: []*shim.Column{
			&shim.Column{Value: &shim.Column_String_{String_: data.DetailId}},    //类别明细ID
			&shim.Column{Value: &shim.Column_String_{String_: data.DetailName}},  //类别明细名称
			&shim.Column{Value: &shim.Column_String_{String_: data.CategoryId}},  //类别ID
			&shim.Column{Value: &shim.Column_String_{String_: data.Describe}},    //描述
			&shim.Column{Value: &shim.Column_String_{String_: data.CreateTime}},  //创建时间
			&shim.Column{Value: &shim.Column_String_{String_: data.CreateUser}},  //创建人
			&shim.Column{Value: &shim.Column_String_{String_: data.UpdateTime}},  //修改时间
			&shim.Column{Value: &shim.Column_String_{String_: data.UpdateUser}}}, //修改人
	})
	if !ok && err == nil {
		return nil, errors.New("insert Configure_Detail failure")
	}
	//更新或者插入table_count表
	totalNumber, err := util.UpdateTableCount(stub, util.Configure_Detail)
	if totalNumber == 0 || err != nil {
		stub.DeleteRow(util.Configure_Detail, []shim.Column{shim.Column{Value: &shim.Column_String_{String_: data.DetailId}}})
		return nil, errors.New("Table_Count insert failed")
	}
	//把获取的总数插入行号表中当主键
	err = util.UpdateRowNoTable(stub, util.Configure_Detail_Rownum, data.DetailId, totalNumber)
	if err != nil {
		stub.DeleteRow(util.Configure_Detail, []shim.Column{shim.Column{Value: &shim.Column_String_{String_: data.DetailId}}})
		stub.DeleteRow(util.Configure_Category, []shim.Column{shim.Column{Value: &shim.Column_String_{String_: data.CategoryId}}})
		var columns []shim.Column
		col := shim.Column{Value: &shim.Column_String_{String_: util.Configure_Detail}}
		columns = append(columns, col)
		row, _ := stub.GetRow(util.Table_Count, columns) //row是否为空
		if len(row.Columns) == 1 {
			stub.DeleteRow(util.Table_Count, []shim.Column{shim.Column{Value: &shim.Column_String_{String_: util.Configure_Detail}}})
		} else {
			stub.ReplaceRow(util.Table_Count, shim.Row{
				Columns: []*shim.Column{
					&shim.Column{Value: &shim.Column_String_{String_: util.Configure_Detail}}, //表名
					&shim.Column{Value: &shim.Column_Int64{Int64: totalNumber - 1}}},          //总数
			})
		}
		return nil, errors.New("insert Configure_Detail_Rownum failed")
	}
	log.Println("InsertConfigureDetail success.")
	return nil, nil
}
