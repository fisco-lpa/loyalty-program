package com.fiscolpa.demo.service;

import com.fiscolpa.demo.model.PointsTransation;
import com.fiscolpa.demo.model.PointsTransationDetail;


public interface PointsExchangeTransationService {

	int insertExchange(PointsTransation pointsTransation);
	int insertDetail(PointsTransationDetail pointsTransationDetail);
	
}