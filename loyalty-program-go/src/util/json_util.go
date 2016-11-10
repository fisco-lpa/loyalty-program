package util

import (
	"account"
	"error"
)

func ParseJson(args []string) (Account, error) {
	var data Account
	//base64解码
	arg, err := base64.StdEncoding.DecodeString(args[0])
	if err != nil {
		return nil, errors.New("updateAccount base64 decode error.")
	}

	//解析数据
	err = json.Unmarshal(arg, &data)
	if err != nil {
		return nil, errors.New("updateAccount json Parse error.")
	}
	return data, nil
}
