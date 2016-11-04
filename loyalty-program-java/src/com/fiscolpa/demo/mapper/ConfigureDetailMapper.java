package com.fiscolpa.demo.mapper;

import com.fiscolpa.demo.model.ConfigureDetail;
import com.fiscolpa.demo.model.ConfigureDetailKey;

public interface ConfigureDetailMapper {
    int deleteByPrimaryKey(ConfigureDetailKey key);

    int insert(ConfigureDetail record);

    int insertSelective(ConfigureDetail record);

    ConfigureDetail selectByPrimaryKey(ConfigureDetailKey key);

    int updateByPrimaryKeySelective(ConfigureDetail record);

    int updateByPrimaryKey(ConfigureDetail record);
}