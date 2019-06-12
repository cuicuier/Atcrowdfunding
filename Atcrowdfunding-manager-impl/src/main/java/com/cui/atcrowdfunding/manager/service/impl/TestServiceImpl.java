package com.cui.atcrowdfunding.manager.service.impl;

import com.cui.atcrowdfunding.manager.dao.TestDao;
import com.cui.atcrowdfunding.manager.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class TestServiceImpl implements TestService {

    @Resource
    private TestDao testDao;

    @Override
    public void insert() {
        Map map=new HashMap();
        map.put("name","cui崔");
        testDao.insert(map);

    }
}
