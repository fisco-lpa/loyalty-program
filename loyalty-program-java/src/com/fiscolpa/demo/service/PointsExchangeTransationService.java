package com.fiscolpa.demo.service;

import java.util.List;

import com.fiscolpa.demo.model.PointsTransation;
import com.fiscolpa.demo.model.PointsTransationDetail;


public interface PointsExchangeTransationService {

	int insertExchange(PointsTransation pointsTransation);
	int insertDetail(PointsTransationDetail pointsTransationDetail);
	
	  int updateByPrimaryKeySelective(PointsTransationDetail record);
	int selectAccountBalance(String id);
	/**
	 * 会员用积分购物
	 * @param ptd。credit_party、merchant、credit_create_time
	 * @return
	 */
	public List<PointsTransationDetail> queryTransationDetailList(PointsTransationDetail ptd);
	
}