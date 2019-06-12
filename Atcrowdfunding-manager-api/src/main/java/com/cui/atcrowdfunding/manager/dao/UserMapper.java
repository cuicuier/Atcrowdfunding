package com.cui.atcrowdfunding.manager.dao;

import com.cui.atcrowdfunding.bean.Permission;
import com.cui.atcrowdfunding.bean.Role;
import com.cui.atcrowdfunding.bean.User;
import com.cui.atcrowdfunding.vo.Data;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    User queryUserLogin(Map<String, Object> paramMap);

    List<User> queryList(Map<String,Object> paramMap);

    Integer queryCount(Map<String,Object> paramMap);

    User queryUserById(@Param("id") Integer id);

    int deleteBatchUserByVO(Data data);

    List<Role> queryAllRole();

    List<Integer> queryRoleByUserId(Integer id);

    int saveUserRole(@Param("userid") Integer userid, @Param("data") Data data);

    int deleteUserRole(@Param("userid") Integer userid, @Param("data") Data data);

    List<Permission> queryPermissionByUserid(@Param("userid") Integer userid);

//    List<User> queryList(@Param("startIndex") Integer startIndex,  @Param("pagesize") Integer pagesize);
//
//    Integer queryCount();
    
}