package com.cui.atcrowdfunding.manager.service;

import com.cui.atcrowdfunding.bean.Permission;
import com.cui.atcrowdfunding.manager.dao.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface PermissionService {

    List<Permission> queryAllPermission();

    List<Integer> queryPermissionForRoleid(Integer roleid);
}
