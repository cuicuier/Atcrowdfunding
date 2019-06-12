package com.cui.atcrowdfunding.manager.dao;

import com.cui.atcrowdfunding.bean.Role;
import com.cui.atcrowdfunding.bean.User;
import com.cui.atcrowdfunding.util.Page;

import java.util.List;
import java.util.Map;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    Role selectByPrimaryKey(Integer id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    List<Role> queryList(Map<String,Object> paramMap);

    Integer queryCount(Map<String,Object> paramMap);
}