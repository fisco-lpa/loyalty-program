package com.fiscolpa.demo.mapper;

import com.fiscolpa.demo.model.ConfigureCategory;

public interface ConfigureCategoryMapper {
    int deleteByPrimaryKey(String categoryId);

    int insert(ConfigureCategory record);

    int insertSelective(ConfigureCategory record);

    ConfigureCategory selectByPrimaryKey(String categoryId);

    int updateByPrimaryKeySelective(ConfigureCategory record);

    int updateByPrimaryKey(ConfigureCategory record);
}