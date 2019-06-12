package com.cui.atcrowdfunding.manager.service;

import com.cui.atcrowdfunding.bean.Advertisement;
import com.cui.atcrowdfunding.util.Page;

import java.util.List;
import java.util.Map;

public interface AdvertService {

    Page queryPage(Map<String,Object> paramMap);

    int insertAdvert(Advertisement advertisement);
}
