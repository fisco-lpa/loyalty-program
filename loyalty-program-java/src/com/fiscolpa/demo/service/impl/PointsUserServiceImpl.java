package com.fiscolpa.demo.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fiscolpa.demo.mapper.AccountMapper;
import com.fiscolpa.demo.mapper.PointsUserMapper;
import com.fiscolpa.demo.model.Account;
import com.fiscolpa.demo.model.PointsUser;
import com.fiscolpa.demo.service.PointsUserService;
import com.fiscolpa.demo.util.UUIDGenerator;

@Service("pointsUserService")
public class PointsUserServiceImpl extends BaseService<PointsUser> implements PointsUserService {

	@Autowired
	private PointsUserMapper pointsUserMapper;

	@Autowired
	private AccountMapper accountMapper;
	
	public List<PointsUser> select(PointsUser pointsUser) {
		return pointsUserMapper.select(pointsUser);
	}

	public List<PointsUser> selectUserAndUserType(PointsUser pointsUser) {
		return pointsUserMapper.selectUserAndUserType(pointsUser);
	}
	
	@Override
	public List<PointsUser> getAllPointsUser() {
		return pointsUserMapper.getAllPointsUser();
	}
	
	@Transactional
	public int insertPointsUser(PointsUser pointsUser){
		Date date= new Date();
		if("3".equals(pointsUser.getUserType())){
			pointsUser.setPhoneNumber(pointsUser.getUserName());
			pointsUser.setUserPassword("1234");
		}
		PointsUser pu = new PointsUser(pointsUser.getUserName());
		pu = pointsUserMapper.selectOne(pu);
		if(pu!=null) return 0;
		pointsUser.setUserId(UUIDGenerator.getUUID());
		pointsUser.setCreateTime(date);
		pointsUser.setUpdateTime(date);
		pointsUser.setCreateUser(pointsUser.getUserName());
		pointsUser.setUpdateUser(pointsUser.getUserName());
		pointsUserMapper.insert(pointsUser);
		
		Account acc = new Account();
		acc.setAccountId(UUIDGenerator.getUUID());
		acc.setUserId(pointsUser.getUserId());
		acc.setAccountBalance(0);
		acc.setAccountTypeId(pointsUser.getUserType());
		acc.setCreateTime(date);
		acc.setUpdateTime(date);
		acc.setCreateUser(pointsUser.getUserName());
		acc.setUpdateUser(pointsUser.getUserName());
		return accountMapper.insert(acc);
	}

}
