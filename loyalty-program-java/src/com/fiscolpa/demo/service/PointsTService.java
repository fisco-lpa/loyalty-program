package com.fiscolpa.demo.service;

import java.util.List;

import com.fiscolpa.demo.model.PointsTransation;

public interface PointsTService {

    List<PointsTransation> selectByAccount(PointsTransation record,int page, int rows);
}
