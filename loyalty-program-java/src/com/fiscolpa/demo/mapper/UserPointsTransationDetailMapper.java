package com.fiscolpa.demo.mapper;

import java.util.List;

import com.fiscolpa.demo.model.PointsTransationDetail;

public interface UserPointsTransationDetailMapper {
    //增加的方法
    int sumByRollInAccount(String rollInAccount);
    
    int sumByRollOutAccount(String rollOutAccount);
    
    List<PointsTransationDetail> selectByAccount(PointsTransationDetail record);
}