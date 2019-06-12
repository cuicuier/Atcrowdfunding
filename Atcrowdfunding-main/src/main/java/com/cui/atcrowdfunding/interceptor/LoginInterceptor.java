package com.cui.atcrowdfunding.interceptor;

import com.cui.atcrowdfunding.bean.Member;
import com.cui.atcrowdfunding.bean.User;
import com.cui.atcrowdfunding.util.Const;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("登录权限拦截器");
        //1.定义哪些路径不需要拦截（白名单，如注册功能)
        Set<String> uri=new HashSet<String>();
        uri.add("/user/reg.do");//注册
        uri.add("/user/reg.htm");//注册
        uri.add("/doLogin.do");//登录
        uri.add("/login.htm");//登录
        uri.add("/logout.do");//注销
        uri.add("/index.htm");//注销

        String servletPath=request.getServletPath(); //获取请求路径
        System.out.println("请求路径为："+servletPath);
        if (uri.contains(servletPath)){//请求路径在白名单中，放行
            return true;
        }

        //2.判断用户是否登录,如果登录就放行
        HttpSession session=request.getSession();
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        Member member= (Member) session.getAttribute(Const.LOGIN_MEMBER);
        if (user!=null || member!=null){
            return true;

        }else {
            response.sendRedirect(request.getContextPath()+"/login.htm");
            return false;
        }
    }



}
