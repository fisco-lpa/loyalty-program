package com.fiscolpa.demo.service;

import com.fiscolpa.demo.model.PointsUser;

public interface ChangePwdService extends IService<PointsUser>{
	
	public int updatePwd(PointsUser pointsUser);

}
