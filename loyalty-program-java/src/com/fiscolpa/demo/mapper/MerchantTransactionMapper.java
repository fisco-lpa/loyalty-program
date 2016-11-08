package com.fiscolpa.demo.mapper;

import java.util.List;

import com.fiscolpa.demo.model.PointsTransationExtends;
import com.fiscolpa.demo.model.PointsTransationDetailExtends;

public interface MerchantTransactionMapper {
	
	String queryPoints(PointsTransationExtends pt);
	
	List<PointsTransationExtends> queryTransationList(PointsTransationExtends pt);
	
	List<PointsTransationDetailExtends> queryTransationDetailList(PointsTransationDetailExtends ptd);
		
	void insertTransation(List<PointsTransationExtends> ptList);
	
	void insertTransationDetail(List<PointsTransationDetailExtends> ptdList);
	
	void updateCurBalance(List<PointsTransationDetailExtends> ptdList);
}
