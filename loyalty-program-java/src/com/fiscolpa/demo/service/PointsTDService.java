package com.fiscolpa.demo.service;

import java.util.List;

import com.fiscolpa.demo.model.PointsTransationDetail;

public interface PointsTDService  extends IService<PointsTransationDetail> {

	List<PointsTransationDetail> selectByPointsTransationDetail(PointsTransationDetail pointsTransationDetail, int page, int rows);
	
    int sumByRollInAccount(String rollInAccount);
    
    int sumByRollOutAccount(String rollOutAccount);
    
    List<PointsTransationDetail> selectByAccount(PointsTransationDetail record,int page, int rows);
}
