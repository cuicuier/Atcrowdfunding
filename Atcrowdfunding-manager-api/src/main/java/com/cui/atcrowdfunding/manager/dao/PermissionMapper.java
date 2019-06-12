package com.cui.atcrowdfunding.manager.dao;

import com.cui.atcrowdfunding.bean.Permission;
import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Integer id);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);

    List<Permission> queryAllPermission();

    List<Integer> queryPermissionForRoleid(Integer roleid);
}