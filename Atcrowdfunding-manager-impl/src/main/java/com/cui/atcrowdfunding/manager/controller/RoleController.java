package com.cui.atcrowdfunding.manager.controller;

import com.cui.atcrowdfunding.bean.Permission;
import com.cui.atcrowdfunding.bean.Role;
import com.cui.atcrowdfunding.manager.service.PermissionService;
import com.cui.atcrowdfunding.manager.service.RoleService;
import com.cui.atcrowdfunding.util.AjaxResult;
import com.cui.atcrowdfunding.util.Page;
import com.cui.atcrowdfunding.util.StringUtil;
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
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/index")
    public String index(){
        return "role/index";
    }

    @ResponseBody
    @RequestMapping("/doIndex")
    public Object doIndex(@RequestParam(value = "pageno",required = false,defaultValue = "1") Integer pageno,
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


            Page page=roleService.queryPage(paramMap);
            System.out.println("查到角色列表了");
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

    @RequestMapping("/assignpermission")
    public String assignPermission(){
        return "role/assignpermission";
    }

    @ResponseBody
    @RequestMapping("/loadData")
    public Object loadData(Integer roleid){
        AjaxResult result=new AjaxResult();
        List<Permission> root=new ArrayList<Permission>();

        try {
            List<Permission> childrenPermissions=permissionService.queryAllPermission();

            //根据角色id查询该角色已有的许可
            List<Integer> permissionIdsForRoleid = permissionService.queryPermissionForRoleid(roleid);

            Map<Integer,Permission> map=new HashMap<Integer,Permission>();
            for (Permission innerpermission:childrenPermissions){
                map.put(innerpermission.getId(),innerpermission);  //将每个许可放在map中
                if (permissionIdsForRoleid.contains(innerpermission.getId())){
                    innerpermission.setChecked(true);
                }
            }
            for (Permission permission:childrenPermissions){
                //通过子查找父
                Permission child=permission; //假设为子菜单
                if (child.getPid()==0){
                    root.add(permission);
                }else {
                    Permission parent = map.get(child.getPid());
                    parent.getChildren().add(child);
                }
            }

            System.out.println("许可树根的数目："+root.size());

            result.setData(root);

            result.setSuccess(true);


        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage("加载许可树数据失败");
        }
        return root;
    }



}
