package com.fiscolpa.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiscolpa.demo.mapper.UserAccountMapper;
import com.fiscolpa.demo.model.Account;
import com.fiscolpa.demo.service.UserAccountService;

@Service("userAccountService")
public class UserAccountServiceImpl implements UserAccountService {

	@Autowired  
    private UserAccountMapper userAccountMapper;  
	
	@Override
	public int sumByPrimaryKey(String accountId) {
		return userAccountMapper.sumByPrimaryKey(accountId);
	}

	@Override
	public String getAccountByUserName(String userName) {
		return userAccountMapper.getAccountByUserName(userName);
	}

	@Override
	public void updateAccountByBalance(Account account) {
		userAccountMapper.updateAccountByBalance(account);
	}

	@Override
	public String getUserByAccountId(String phoneNumber) {
		return userAccountMapper.getUserByAccountId(phoneNumber);
	}


	
}
