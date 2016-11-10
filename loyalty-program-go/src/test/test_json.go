package main

import (
	//"encoding/json"
	"fmt"
	"util"
)

type AssetAttrs struct {
	AcceptanceName string
	AssetAmount    string
	Ac             UserAttrs
}

type UserAttrs struct {
	OwnerName    string
	EnterpriseID string
}

func main() {
	data := new(AssetAttrs)
	fmt.Println("print before assigning value:")
	//printValue(data)

	var str string = `{"AcceptanceName":"shakespeare","AssetAmount":"1234.67","Ac":{"OwnerName":"one","EnterpriseID":"12"}}`
	_, err := util.ParseJson(data, str)
	if err != nil {
		fmt.Println("error............")
		return
	}

	fmt.Println("print after assigning value:")
	//printValue(data)
}

func printValue(data *AssetAttrs) {
	fmt.Println("acceptance name:" + data.AcceptanceName)
	fmt.Println("asset amount:" + data.AssetAmount)
	fmt.Println("enterprise id:" + data.Ac.EnterpriseID)
	fmt.Println("owner name:" + data.Ac.OwnerName)
}
