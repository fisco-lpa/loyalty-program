package com.fiscolpa.demo.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiscolpa.demo.mapper.UserPointsTransationMapper;
import com.fiscolpa.demo.model.PointsTransation;
import com.fiscolpa.demo.service.PointsTService;
import com.github.pagehelper.PageHelper;

@Service("pointsTService")
public class PointsTServiceImpl implements PointsTService {

	private static final Logger logger = LoggerFactory.getLogger(PointsTDServiceImpl.class);
	
	@Autowired  
    private UserPointsTransationMapper userPointsTransationMapper;  
	
	@Override
	public List<PointsTransation> selectByAccount(PointsTransation record, int page, int rows) {
		PageHelper.startPage(page, rows);
		List<PointsTransation> ptd = userPointsTransationMapper.selectByAccount(record);
		return ptd;
	}

}
