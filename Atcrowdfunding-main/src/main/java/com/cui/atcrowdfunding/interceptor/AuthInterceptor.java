package com.cui.atcrowdfunding.interceptor;

import com.cui.atcrowdfunding.bean.Permission;
import com.cui.atcrowdfunding.manager.service.PermissionService;
import com.cui.atcrowdfunding.util.Const;
import com.cui.atcrowdfunding.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 权限拦截器，未授权禁止访问系统相关资源
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {


    @Autowired
    private PermissionService permissionService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("权限拦截器");
        String requestURI = request.getRequestURI();
        System.out.println("请求路径为："+requestURI);

        HttpSession session=request.getSession();

        Set<String> allPermissionUris= (Set<String>) session.getServletContext().getAttribute(Const.ALL_PERMISSION_URI);
        if (allPermissionUris.contains(requestURI)){
            //获取当前用户角色的许可菜单，请求路径必须包含在许可菜单中，否则拒绝访问
            Set<String> authUris=(Set<String>) session.getAttribute(Const.LOGIN_AUTH_PEREMISSION_URI);
            if (authUris.contains(requestURI)){
                return true;
            }else {
                response.sendRedirect(request.getContextPath()+"/error/error.htm");
                return false;
            }
        }else {//不需要权限控制的直接放行
            return true;
        }
    }
}
