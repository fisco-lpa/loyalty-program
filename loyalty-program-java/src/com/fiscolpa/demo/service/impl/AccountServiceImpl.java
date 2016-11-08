package com.fiscolpa.demo.service.impl;

import org.springframework.stereotype.Service;

import com.fiscolpa.demo.model.Account;
import com.fiscolpa.demo.service.AccountService;

@Service("accountService")
public class AccountServiceImpl extends BaseService<Account> implements AccountService {

}
