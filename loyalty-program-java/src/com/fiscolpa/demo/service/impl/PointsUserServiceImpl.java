package com.fiscolpa.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiscolpa.demo.mapper.PointsUserMapper;
import com.fiscolpa.demo.model.PointsUser;
import com.fiscolpa.demo.service.PointsUserService;

@Service("pointsUserService")
public class PointsUserServiceImpl extends BaseService<PointsUser> implements PointsUserService {

	@Autowired
	private PointsUserMapper pointsUserMapper;
	
	public List<PointsUser> select(PointsUser pointsUser) {
		return pointsUserMapper.select(pointsUser);
	}

}
