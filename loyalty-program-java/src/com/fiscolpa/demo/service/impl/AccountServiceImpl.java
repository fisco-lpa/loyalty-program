package com.fiscolpa.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiscolpa.demo.mapper.AccountMapper;
import com.fiscolpa.demo.model.Account;
import com.fiscolpa.demo.service.AccountService;
import com.fiscolpa.demo.vo.AccountVo;

@Service("accountService")
public class AccountServiceImpl extends BaseService<Account> implements AccountService {
	@Autowired
	private AccountMapper accountMapper;

	@Override
	public List<AccountVo> getAllMerchant(String accountType) {
		return accountMapper.getAllAccountByType(accountType);
	}

	@Override
	public Account getAccount(String accountId) {
		return accountMapper.selectByPrimaryKey(accountId);
	}
	@Override
	public List<Account> getAllAccount() {
		return accountMapper.getAllAccount();
	}
}
