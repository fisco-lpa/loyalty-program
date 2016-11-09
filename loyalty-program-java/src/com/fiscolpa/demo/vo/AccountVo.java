package com.fiscolpa.demo.vo;

import com.fiscolpa.demo.model.Account;

/**
 * 
 * @author zhuyongjun
 *
 */
public class AccountVo extends Account{
    
    /**
     * 账户类型名称
     */
    private String accountTypeName;
    
    private String userName;

	public String getAccountTypeName() {
		return accountTypeName;
	}

	public void setAccountTypeName(String accountTypeName) {
		this.accountTypeName = accountTypeName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}