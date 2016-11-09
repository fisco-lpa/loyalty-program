package com.fiscolpa.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiscolpa.demo.mapper.PointsTransationDetailMapper;
import com.fiscolpa.demo.mapper.PointsTransationExchangeMapper;
import com.fiscolpa.demo.model.PointsTransation;
import com.fiscolpa.demo.model.PointsTransationDetail;
import com.fiscolpa.demo.service.PointsExchangeTransationService;

@Service("pointsExchangeTransationService")
public class PointsExchangeTransationServiceImpl implements PointsExchangeTransationService {

	@Autowired
	private PointsTransationExchangeMapper pointsTransationExchangeMapper;
	
	@Autowired
	private PointsTransationDetailMapper pointsTransationDetailMapper;


	@Override
	public int insertExchange(PointsTransation pointsTransation) {

		return pointsTransationExchangeMapper.insertExchange(pointsTransation);
	}


	@Override
	public int insertDetail(PointsTransationDetail pointsTransationDetail) {
		
		return pointsTransationDetailMapper.insert(pointsTransationDetail);
	}

}