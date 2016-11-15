package util

import (
	"flag"
	"fmt"
	"log"
	"os"
	"runtime"
)

//var (
//	logFileName = flag.String("log", "blockchain.log", "Log file name")
//)

func init() {
	path, _ := GetHomePathForUnix()
	fileName := path + "/" + "blockchain.log"
	fmt.Println("log file path is :" + fileName)
	logFileName := flag.String("log", fileName, "Log file name")

	runtime.GOMAXPROCS(runtime.NumCPU())
	flag.Parse()

	//创建log文件 log文件的权限位0666（即所有用户可读写）
	//如果log文件不存在，创建一个新的文件os.O_CREATE
	//将log信息写入到log文件，是继承到当前log文件，不是覆盖os.O_APPEND
	logFile, logErr := os.OpenFile(*logFileName, os.O_CREATE|os.O_RDWR|os.O_APPEND, 0666)
	if logErr != nil {
		fmt.Println("Fail to find", *logFile, "cServer start Failed")
		os.Exit(1)
	}

	//设置输出目的地log.SetOutput()
	log.SetOutput(logFile)

	// 可以通过log.SetFlags()自定议你想要表达的格式
	log.SetFlags(log.Ldate | log.Ltime | log.Lshortfile)

	log.Println("Log file has been created.")
}

func Begin(funcName string) string {
	fmt.Println("Function " + funcName + " begin....")
	log.Println("Function " + funcName + " begin....")
	return funcName
}

func End(funcName string) {
	log.Println("Function " + funcName + " end....")
	fmt.Println("Function " + funcName + " end....")
}
