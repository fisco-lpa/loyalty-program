package main

import (
	"account"
	"fmt"
	//"log"
)

type AccountInterface interface {
}

func main2() {
	aa := new(account.Account)
	test(aa)
	fmt.Println("test1..........")
}

func test(acc AccountInterface) {
	fmt.Println("test2..........")
}
