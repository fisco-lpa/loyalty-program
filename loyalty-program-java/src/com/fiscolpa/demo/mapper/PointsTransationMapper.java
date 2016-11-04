package com.fiscolpa.demo.mapper;

import com.fiscolpa.demo.model.PointsTransation;

public interface PointsTransationMapper {
    int deleteByPrimaryKey(String transId);

    int insert(PointsTransation record);

    int insertSelective(PointsTransation record);

    PointsTransation selectByPrimaryKey(String transId);

    int updateByPrimaryKeySelective(PointsTransation record);

    int updateByPrimaryKey(PointsTransation record);
}