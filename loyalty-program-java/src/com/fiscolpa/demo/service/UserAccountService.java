package com.fiscolpa.demo.service;

import com.fiscolpa.demo.model.Account;

public interface UserAccountService{

	int sumByPrimaryKey(String accountId);
	
	String getAccountByUserName(String userName);
	
	void updateAccountByBalance(Account account);
}
