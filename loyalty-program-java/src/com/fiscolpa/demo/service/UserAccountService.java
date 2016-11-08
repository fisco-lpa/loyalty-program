package com.fiscolpa.demo.service;

public interface UserAccountService{

	int sumByPrimaryKey(String accountId);
	
	String getAccountByUserName(String userName);
}
