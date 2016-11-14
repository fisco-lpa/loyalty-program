package com.fiscolpa.demo.mapper;

import java.util.List;

import com.fiscolpa.demo.model.PointsUser;

import tk.mybatis.mapper.common.Mapper;

public interface PointsUserMapper extends Mapper<PointsUser> {
	
	int updatePwd(PointsUser user);
    
	List<PointsUser> selectUserAndUserType(PointsUser pointsUser);
	
	List<PointsUser> getAllPointsUser();
}