package com.fiscolpa.demo.service;

import java.util.List;

import com.fiscolpa.demo.model.PointsUser;

public interface PointsUserService extends IService<PointsUser> {

	public List<PointsUser> select(PointsUser pointsUser);
	
}
