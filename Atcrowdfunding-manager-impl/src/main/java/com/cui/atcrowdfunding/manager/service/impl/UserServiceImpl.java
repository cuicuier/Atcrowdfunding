package com.cui.atcrowdfunding.manager.service.impl;

import com.cui.atcrowdfunding.bean.Permission;
import com.cui.atcrowdfunding.bean.Role;
import com.cui.atcrowdfunding.bean.User;
import com.cui.atcrowdfunding.exception.LoginFailException;
import com.cui.atcrowdfunding.manager.dao.UserMapper;
import com.cui.atcrowdfunding.manager.service.UserService;
import com.cui.atcrowdfunding.util.Const;
import com.cui.atcrowdfunding.util.DateUtil;
import com.cui.atcrowdfunding.util.MD5Util;
import com.cui.atcrowdfunding.util.Page;
import com.cui.atcrowdfunding.vo.Data;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;


    @Override
    public User queryUserLogin(Map<String, Object> paramMap) {
        User user=userMapper.queryUserLogin(paramMap);
        if (user==null){
            throw new LoginFailException("用户账号或密码不正确！");
        }
        return user;
    }


    @Override
    public Page queryPage( Map<String, Object> paramMap) {
        Page page=new Page((Integer) paramMap.get("pageno"),(Integer) paramMap.get("pagesize"));
        Integer startIndex=page.getStartIndex();
        paramMap.put("startIndex",startIndex);
        List<User> datas = userMapper.queryList(paramMap);
        page.setData(datas);
        Integer totalsize=userMapper.queryCount(paramMap);
        page.setTotalsize(totalsize);
        return page;
    }

    @Override
    public int saveUser(User user) {

        String createtime=DateUtil.getCurrentDate();
        user.setCreatetime(createtime);
        user.setUserpswd(MD5Util.digest32(Const.PASSWORD));
        return userMapper.insert(user);

    }

    @Override
    public User queryUserById(Integer id) {

        return userMapper.queryUserById(id);
    }

    @Override
    public int updateUser(User user) {

        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public int deleteUser(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteBatchUser(Integer[] id) {
        int totalDelete=0;
        for (Integer i:id){
            int count=userMapper.deleteByPrimaryKey(i);
            totalDelete+=count;
        }
        if (totalDelete!=id.length){
            throw new RuntimeException("批量删除失败");
        }
        return totalDelete;
    }

    @Override
    public int deleteBatchUserByVO(Data data) {

        return userMapper.deleteBatchUserByVO(data);
    }

    @Override
    public List<Role> queryAllRole() {
        return userMapper.queryAllRole();
    }

    @Override
    public List<Integer> queryRoleByUserId(Integer id) {
        return userMapper.queryRoleByUserId(id);
    }

    @Override
    public int saveUserRole(Integer userid, Data data) {
        return userMapper.saveUserRole(userid,data);
    }

    @Override
    public int deleteUserRole(Integer userid, Data data) {
        return userMapper.deleteUserRole(userid,data);
    }

    @Override
    public List<Permission> queryPermissionByUserid(Integer id) {
        return userMapper.queryPermissionByUserid(id);
    }
}
