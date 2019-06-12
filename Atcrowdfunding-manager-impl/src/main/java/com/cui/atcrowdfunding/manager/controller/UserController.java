package com.cui.atcrowdfunding.manager.controller;

import com.cui.atcrowdfunding.bean.Role;
import com.cui.atcrowdfunding.bean.User;
import com.cui.atcrowdfunding.manager.service.UserService;

import com.cui.atcrowdfunding.util.AjaxResult;
import com.cui.atcrowdfunding.util.Const;
import com.cui.atcrowdfunding.util.Page;
import com.cui.atcrowdfunding.util.StringUtil;
import com.cui.atcrowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //同步请求
   /* @RequestMapping("/index")
    public String index(@RequestParam(value = "pageno",required = false,defaultValue = "1") Integer pageno,
                        @RequestParam(value = "pagesize",required = false,defaultValue = "10") Integer pagesize, Map map){
        Page page=userService.queryPage(pageno,pagesize);
        map.put("page",page);
        return "user/index";
    }*/

   /*//异步请求
    @RequestMapping("/toIndex")
    public String toIndex(){
        return "user/index";
    }
    @ResponseBody
    @RequestMapping("/index")
    public Object index(@RequestParam(value = "pageno",required = false,defaultValue = "1") Integer pageno,
                        @RequestParam (value = "pagesize",required = false,defaultValue = "10")Integer pagesize){
        AjaxResult result=new AjaxResult();
        try{
            Page page=userService.queryPage(pageno,pagesize);
            System.out.println("查到了");
            result.setSuccess(true);
            result.setPage(page);

        }catch (Exception e){
            System.out.println("没查到");
            result.setSuccess(false);
            result.setMessage("查询数据失败！");

        }
        return result; //将对象序列化为json字符串，以流的形式返回
    }*/

    //异步请求———条件查询
    @RequestMapping("/index")
    public String toIndex(){
        return "user/index";
    }
    @ResponseBody
    @RequestMapping("/doIndex")
    public Object index(@RequestParam(value = "pageno",required = false,defaultValue = "1") Integer pageno,
                        @RequestParam (value = "pagesize",required = false,defaultValue = "10")Integer pagesize,
                        String queryText){
        AjaxResult result=new AjaxResult();
        try{
            Map<String,Object> paramMap = new HashMap();
            paramMap.put("pageno",pageno);
            paramMap.put("pagesize",pagesize);
            if (StringUtil.isNotEmpty(queryText)){
                if (queryText.contains("%")){
                    queryText=queryText.replaceAll("%","\\\\%");

                }
                paramMap.put("queryText",queryText);
            }


            Page page=userService.queryPage(paramMap);
            System.out.println("查到用户列表了");
            result.setSuccess(true);
            result.setPage(page);

        }catch (Exception e){
            System.out.println("没查到");
            result.setSuccess(false);
//            e.printStackTrace();
            result.setMessage("查询数据失败！");

        }
        return result; //将对象序列化为json字符串，以流的形式返回
    }

    @RequestMapping("add")
    public String add(){
        return "/user/add";
    }
    @ResponseBody
    @RequestMapping("/doAdd")
    public Object doAdd(User user){
        AjaxResult result=new AjaxResult();
        try{
            int count=userService.saveUser(user);
            result.setSuccess(count==1);


        }catch (Exception e){
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("保存数据失败！");
        }
        return result; //将对象序列化为json字符串，以流的形式返回
    }
    @RequestMapping("/update")
    public String update(Integer id,Map map){
        User user=userService.queryUserById(id);
        map.put(Const.UPDATE_USER,user);
        return "user/update";
    }
    @ResponseBody
    @RequestMapping("/doUpdate")
    public Object doUpdate(User user){
        AjaxResult result=new AjaxResult();
        try{
            int count=userService.updateUser(user);
            result.setSuccess(count==1);


        }catch (Exception e){
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("修改数据失败！");
        }
        return result; //将对象序列化为json字符串，以流的形式返回
    }
    @ResponseBody
    @RequestMapping("/deleteUser")
    public Object delete(Integer id){
        AjaxResult result=new AjaxResult();
        try{
            int count=userService.deleteUser(id);
            result.setSuccess(count==1);


        }catch (Exception e){
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("删除数据失败！");
        }
        return result; //将对象序列化为json字符串，以流的形式返回
    }

    /*//接收一个参数名带多个值
    @ResponseBody
    @RequestMapping("/deleteBatch")
    public Object deleteBatch(Integer[] id){
        AjaxResult result=new AjaxResult();
        try{
            int count=userService.deleteBatchUser(id);
            result.setSuccess(count==id.length);


        }catch (Exception e){
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("删除数据失败！");
        }
        return result; //将对象序列化为json字符串，以流的形式返回
    }*/
    //接收多条数据
    @ResponseBody
    @RequestMapping("/deleteBatch")
    public Object deleteBatch(Data data){
        AjaxResult result=new AjaxResult();
        try{
            int count=userService.deleteBatchUserByVO(data);
            result.setSuccess(count==data.getDatas().size());


        }catch (Exception e){
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("删除数据失败！");
        }
        return result; //将对象序列化为json字符串，以流的形式返回
    }

    @RequestMapping("/assignrole")
    public String role(Integer id,Map map){
        System.out.println("跳转角色分配页面......");
        List<Role> allListRole = userService.queryAllRole();
        List<Integer> roleIds = userService.queryRoleByUserId(id);

        List<Role> leftRoleList = new ArrayList<Role>();  //未分配角色
        List<Role> rightRoleList = new ArrayList<Role>(); //已分配角色
        for (Role role:allListRole){
            if (roleIds.contains(role.getId())){
                rightRoleList.add(role);
            }else {
                leftRoleList.add(role);
            }
        }

        map.put("leftRoleList",leftRoleList);
        map.put("rightRoleList",rightRoleList);
        System.out.println("跳转成功！");
        return "/user/assignrole";

    }
    //分配角色
    @ResponseBody
    @RequestMapping("/doAssignRole")
    public Object doAssignRole(Integer userid,Data data){
        AjaxResult result=new AjaxResult();
        try{
            int count=userService.saveUserRole(userid,data);
            System.out.println("分配角色成功");
            result.setSuccess(true);


        }catch (Exception e){
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("分配角色失败！");
        }
        return result; //将对象序列化为json字符串，以流的形式返回
    }
    //取消角色
    @ResponseBody
    @RequestMapping("/doUnAssignRole")
    public Object doUnAssignRole(Integer userid,Data data){
        AjaxResult result=new AjaxResult();
        try{
            int count=userService.deleteUserRole(userid,data);
            result.setSuccess(true);


        }catch (Exception e){
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("取消角色失败！");
        }
        return result; //将对象序列化为json字符串，以流的形式返回
    }



}
