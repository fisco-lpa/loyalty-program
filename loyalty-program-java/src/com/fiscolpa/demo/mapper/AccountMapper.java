package com.fiscolpa.demo.mapper;

import java.util.List;

import com.fiscolpa.demo.model.Account;
import com.fiscolpa.demo.vo.AccountVo;

import tk.mybatis.mapper.common.Mapper;

public interface AccountMapper extends Mapper<Account> {
    
    List<AccountVo> getAllAccountByType(String accountType);
    
    List<Account> getAllAccount();
}