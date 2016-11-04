package com.fiscolpa.demo.mapper;

import com.fiscolpa.demo.model.AccountType;

public interface AccountTypeMapper {
    int deleteByPrimaryKey(String accountTypeId);

    int insert(AccountType record);

    int insertSelective(AccountType record);

    AccountType selectByPrimaryKey(String accountTypeId);

    int updateByPrimaryKeySelective(AccountType record);

    int updateByPrimaryKey(AccountType record);
}