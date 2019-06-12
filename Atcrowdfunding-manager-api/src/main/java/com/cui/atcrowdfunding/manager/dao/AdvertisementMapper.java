package com.cui.atcrowdfunding.manager.dao;

import com.cui.atcrowdfunding.bean.Advertisement;
import java.util.List;
import java.util.Map;

public interface AdvertisementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Advertisement record);

    Advertisement selectByPrimaryKey(Integer id);

    List<Advertisement> selectAll();

    int updateByPrimaryKey(Advertisement record);

    List<Advertisement> queryList(Map<String,Object> paramMap);

    Integer queryCount(Map<String,Object> paramMap);
}