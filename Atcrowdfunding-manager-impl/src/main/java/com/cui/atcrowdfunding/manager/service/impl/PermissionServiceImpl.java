package com.cui.atcrowdfunding.manager.service.impl;

import com.cui.atcrowdfunding.bean.Permission;
import com.cui.atcrowdfunding.manager.dao.PermissionMapper;
import com.cui.atcrowdfunding.manager.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> queryAllPermission() {
        return permissionMapper.queryAllPermission();
    }

    @Override
    public List<Integer> queryPermissionForRoleid(Integer roleid) {
        return permissionMapper.queryPermissionForRoleid(roleid);
    }
}
