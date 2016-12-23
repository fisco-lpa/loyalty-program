package com.fiscolpa.demo.service;

import java.util.List;

import com.fiscolpa.demo.model.CommonImg;

public interface CommonImgService extends IService<CommonImg> {

    /**
     * 根据条件分页查询
     *
     * @param country
     * @param page
     * @param rows
     * @return
     */
    List<CommonImg> selectByCommonImg(CommonImg ci, int page, int rows);

}
