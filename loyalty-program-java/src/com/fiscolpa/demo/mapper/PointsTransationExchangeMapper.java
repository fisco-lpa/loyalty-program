package com.fiscolpa.demo.mapper;




import java.util.List;

import com.fiscolpa.demo.model.PointsTransation;
import com.fiscolpa.demo.model.PointsTransationDetail;

public interface PointsTransationExchangeMapper {


    int insertExchange(PointsTransation record);

	int selectAccountBalance(String id);

	List<PointsTransationDetail> selectAllJiFen(PointsTransationDetail ptd);

	int updateByPrimaryKeySelective(PointsTransationDetail record);

 
}