package com.fiscolpa.demo.mapper;

import com.fiscolpa.demo.model.PointsTransationDetail;

public interface PointsTransationDetailMapper {
    int deleteByPrimaryKey(String detailId);

    int insert(PointsTransationDetail record);

    int insertSelective(PointsTransationDetail record);

    PointsTransationDetail selectByPrimaryKey(String detailId);

    int updateByPrimaryKeySelective(PointsTransationDetail record);

    int updateByPrimaryKey(PointsTransationDetail record);
}