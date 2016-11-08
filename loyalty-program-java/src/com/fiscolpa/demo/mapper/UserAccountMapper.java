package com.fiscolpa.demo.mapper;

public interface UserAccountMapper {
    //增加接口
    int sumByPrimaryKey(String accountId);
    
    String getAccountByUserName(String userName);
}