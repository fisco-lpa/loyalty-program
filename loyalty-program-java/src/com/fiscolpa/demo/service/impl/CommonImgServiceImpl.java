package com.fiscolpa.demo.service.impl;

import com.fiscolpa.demo.model.CommonImg;
import com.fiscolpa.demo.service.CommonImgService;
import com.github.pagehelper.PageHelper;

import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

@Service("commonImgService")
public class CommonImgServiceImpl extends BaseService<CommonImg> implements CommonImgService {

    @Override
    public List<CommonImg> selectByCommonImg(CommonImg ci, int page, int rows) {
        Example example = new Example(CommonImg.class);
        Example.Criteria criteria = example.createCriteria();
        
        if (StringUtil.isNotEmpty(ci.getImgId())) {
            criteria.andEqualTo("imgId", ci.getImgId());
        }
        if (StringUtil.isNotEmpty(ci.getAssociateId())) {
            criteria.andEqualTo("associateId", ci.getAssociateId());
        }
        if (StringUtil.isNotEmpty(ci.getImgType())) {
            criteria.andEqualTo("imgType", ci.getImgType());
        }
        //分页查询
        PageHelper.startPage(page, rows);
        return selectByExample(example);
    }

}
