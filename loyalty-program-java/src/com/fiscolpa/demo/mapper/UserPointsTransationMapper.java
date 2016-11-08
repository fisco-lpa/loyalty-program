package com.fiscolpa.demo.mapper;

import java.util.List;

import com.fiscolpa.demo.model.PointsTransation;

public interface UserPointsTransationMapper {

	List<PointsTransation> selectByAccount(PointsTransation record);
}