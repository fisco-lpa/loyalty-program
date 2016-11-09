package com.fiscolpa.demo.mapper;

import com.fiscolpa.demo.model.PointsUser;

import tk.mybatis.mapper.common.Mapper;

public interface PointsUserMapper extends Mapper<PointsUser> {
	
	int updatePwd(PointsUser user);
    
}