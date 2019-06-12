package com.cui.atcrowdfunding.manager.controller;

import com.cui.atcrowdfunding.bean.Permission;
import com.cui.atcrowdfunding.manager.service.PermissionService;
import com.cui.atcrowdfunding.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/index")
    public String index(){
        return "/permission/index";
    }
    //用Map查询父，做父子结合，减少循环，提高性能
    @ResponseBody
    @RequestMapping("/loadData")
    public Object loadData(){
        AjaxResult result=new AjaxResult();
        try {
            List<Permission> root=new ArrayList<Permission>();
            List<Permission> childrenPermissions=permissionService.queryAllPermission();
           /* for (Permission permission:childrenPermissions){ //遍历许可表中各权限
                Permission child=permission;
                if (child.getPid()==0){
                    root.add(child);
                }else {
                    //父节点
                    for (Permission innerpermission : childrenPermissions){
                        if (child.getPid()==innerpermission.getId()){
                            Permission parent=innerpermission;
                            parent.getChildren().add(child);
                            break;
                        }
                    }
                }
            }*/
            Map<Integer,Permission> map=new HashMap<Integer,Permission>();
            for (Permission innerpermission:childrenPermissions){
                map.put(innerpermission.getId(),innerpermission);  //将每个许可放在map中
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
        return result;
    }


}
