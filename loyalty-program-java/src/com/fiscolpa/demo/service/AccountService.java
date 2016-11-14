package com.fiscolpa.demo.service;

import java.util.List;

import com.fiscolpa.demo.model.Account;
import com.fiscolpa.demo.vo.AccountVo;

public interface AccountService extends IService<Account> {
	/**
	 * 通过账户类型获取账户列表
	 * @param accountType 账户类型
	 * @return  账户对象
	 */
	List<AccountVo> getAllMerchant (String accountType);
	
	Account getAccount(String accountId);
	
	List<Account> getAllAccount();
}
