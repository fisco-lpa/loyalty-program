package com.fiscolpa.demo.mapper;

import com.fiscolpa.demo.model.Account;

public interface UserAccountMapper {
    //增加接口
    int sumByPrimaryKey(String accountId);
    
    String getAccountByUserName(String userName);
    
    void updateAccountByBalance(Account account);
    
    String getUserByAccountId(String phoneNumber);
    
}