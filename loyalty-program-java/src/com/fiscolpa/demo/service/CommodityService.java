package com.fiscolpa.demo.service;

import java.util.List;

import com.fiscolpa.demo.model.Commodity;

public interface CommodityService extends IService<Commodity> {

    /**
     * 根据条件分页查询
     *
     * @param country
     * @param page
     * @param rows
     * @return
     */
    List<Commodity> selectByCommodity(Commodity commodity, int page, int rows);

}
