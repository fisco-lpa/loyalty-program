package com.fiscolpa.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fiscolpa.demo.mapper.PointsUserMapper;
import com.fiscolpa.demo.model.PointsUser;
import com.fiscolpa.demo.service.ChangePwdService;

@Service("changePwdService")
public class ChangePwdServiceImpl extends BaseService<PointsUser> implements ChangePwdService {

	@Autowired
	private PointsUserMapper pointsUserMapper;
	
	public int updatePwd(PointsUser pointsUser) {
		return pointsUserMapper.updatePwd(pointsUser);
	}

}
