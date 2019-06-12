package com.cui.atcrowdfunding.manager.service.impl;

import com.cui.atcrowdfunding.bean.Advertisement;
import com.cui.atcrowdfunding.bean.Param;
import com.cui.atcrowdfunding.manager.dao.AdvertisementMapper;
import com.cui.atcrowdfunding.manager.service.AdvertService;
import com.cui.atcrowdfunding.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class AdvertServiceImpl implements AdvertService {


    @Resource
    private AdvertisementMapper advertisementMapper;
    @Override
    public Page queryPage(Map<String, Object> paramMap) {
        Page page=new Page((Integer)paramMap.get("pageno"),(Integer)paramMap.get("pagesize"));
        Integer startIndex=page.getStartIndex();
        paramMap.put("startIndex",startIndex);
        List<Advertisement> advertisements=advertisementMapper.queryList(paramMap);
        page.setData(advertisements);
        Integer total=advertisementMapper.queryCount(paramMap);
        page.setTotalsize(total);
        return page;
    }

    @Override
    public int insertAdvert(Advertisement advertisement) {
        return advertisementMapper.insert(advertisement);
    }
}
