package com.cui.atcrowdfunding.manager.service.impl;

import com.cui.atcrowdfunding.bean.Role;
import com.cui.atcrowdfunding.bean.User;
import com.cui.atcrowdfunding.manager.dao.RoleMapper;
import com.cui.atcrowdfunding.manager.service.RoleService;
import com.cui.atcrowdfunding.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;


    @Override
    public Page queryPage(Map<String, Object> paramMap) {
        Page page=new Page((Integer) paramMap.get("pageno"),(Integer) paramMap.get("pagesize"));
        Integer startIndex=page.getStartIndex();
        paramMap.put("startIndex",startIndex);
        List<Role> datas = roleMapper.queryList(paramMap);
        page.setData(datas);
        Integer totalsize=roleMapper.queryCount(paramMap);
        page.setTotalsize(totalsize);
        return page;
    }
}
