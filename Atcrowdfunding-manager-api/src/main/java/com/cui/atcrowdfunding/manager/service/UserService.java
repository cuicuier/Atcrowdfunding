package com.cui.atcrowdfunding.manager.service;

import com.cui.atcrowdfunding.bean.Permission;
import com.cui.atcrowdfunding.bean.Role;
import com.cui.atcrowdfunding.bean.User;
import com.cui.atcrowdfunding.util.Page;
import com.cui.atcrowdfunding.vo.Data;

import java.util.List;
import java.util.Map;

public interface UserService {
    User queryUserLogin(Map<String,Object> paramMap);


//    @Deprecated
//    Page queryPage(Integer pageno, Integer pagesize);

    Page queryPage(Map<String,Object> paramMap);

    int saveUser(User user);

    User queryUserById(Integer id);

    int updateUser(User user);

    int deleteUser(Integer id);

    int deleteBatchUser(Integer[] ids);

    int deleteBatchUserByVO(Data data);

    List<Role> queryAllRole();

    List<Integer> queryRoleByUserId(Integer id);

    int saveUserRole(Integer userid, Data data);

    int deleteUserRole(Integer userid, Data data);

    List<Permission> queryPermissionByUserid(Integer id);
}
