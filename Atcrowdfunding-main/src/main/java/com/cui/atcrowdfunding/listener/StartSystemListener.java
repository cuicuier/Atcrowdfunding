package com.cui.atcrowdfunding.listener;

import com.cui.atcrowdfunding.bean.Permission;
import com.cui.atcrowdfunding.manager.service.PermissionService;
import com.cui.atcrowdfunding.util.Const;
import com.cui.atcrowdfunding.util.StringUtil;
import com.vaadin.service.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StartSystemListener implements ServletContextListener {


    //在服务器启动时，创建Application时，需要执行的方法
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //将项目的上下文路径放置到application中
        ServletContext application=sce.getServletContext();
        String contextPath=application.getContextPath();
        application.setAttribute("APP_PATH",contextPath);
        System.out.println("APP_PATH......"+contextPath);

        //获取系统所有许可权限菜单
        WebApplicationContext ioc=  WebApplicationContextUtils.getWebApplicationContext(application);    //获取服务器创建的IOC容器
        PermissionService permissionService=ioc.getBean(PermissionService.class);
        List<Permission> allPermission=permissionService.queryAllPermission();
        Set<String> allPermissionUris=new HashSet<String>();
        for (Permission permission:allPermission){
            if (StringUtil.isNotEmpty(permission.getUrl())){
                allPermissionUris.add("/"+permission.getUrl());
            }
        }
        application.setAttribute(Const.ALL_PERMISSION_URI,allPermissionUris);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
