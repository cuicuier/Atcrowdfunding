package com.cui.atcrowdfunding.controller;

import com.cui.atcrowdfunding.bean.Member;
import com.cui.atcrowdfunding.bean.Permission;
import com.cui.atcrowdfunding.bean.User;
import com.cui.atcrowdfunding.manager.service.UserService;
import com.cui.atcrowdfunding.potal.service.MemberService;
import com.cui.atcrowdfunding.util.AjaxResult;
import com.cui.atcrowdfunding.util.Const;
import com.cui.atcrowdfunding.util.MD5Util;
import com.cui.atcrowdfunding.util.StringUtil;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;


@Controller
public class DispatcherController {

    @Autowired
    private UserService userService;
    @Autowired
    private MemberService memberService;

    @RequestMapping("/index")
    public String index(){
        return "index";  //到WEB-INF/jsp/下找
    }

    /*
    跳转到登录界面
     */
    @RequestMapping("/login")
    public String login(HttpServletRequest request,HttpSession session){
        boolean needLogin=true;
        String logintype=null;
        Cookie[] cookies=request.getCookies();
        if (cookies!=null){
            for (Cookie cookie:cookies){
                if ("logincode".equals(cookie.getName())){
                    String logincode=cookie.getValue();
                    System.out.println("获取cookie中的键值对"+cookie.getName()+"=========="+logincode);
                    String[] split=logincode.split("&");
                    if (split.length==3){
                        String loginacct=split[0].split("=")[1];
                        String userpswd=split[1].split("=")[1];
                        logintype=split[2].split("=")[1];

                        Map<String,Object> paramMap=new HashMap<String, Object>();
                        paramMap.put("loginacct",loginacct);
//                          paramMap.put("userpswd",MD5Util.digest32(userpswd));
                        paramMap.put("userpswd",userpswd);
                        paramMap.put("type",logintype);

                        if ("member".equals(logintype)){

                            Member doLogin = memberService.queryMemberLogin(paramMap);

                            if (doLogin!=null){
                                session.setAttribute(Const.LOGIN_MEMBER,doLogin);
                                needLogin=false;
                                System.out.println("我直接登录了");

                            }

                        }else if ("user".equals(logintype)){
                            User doLogin = userService.queryUserLogin(paramMap);

                            if (doLogin!=null){
                                session.setAttribute(Const.LOGIN_USER,doLogin);
                                needLogin=false;
                                System.out.println("我直接登录了");

                            }
                        }
                    }

                }
            }
        }
        if (needLogin){
            return "login";
        }else {
            if ("member".equals(logintype)){
                return "redirect:/member.htm";
            }else if ("user".equals(logintype)){
                return "redirect:/main.htm";

            }
        }
        return "login";
    }
    @RequestMapping("/main")
    public String main(HttpSession session){

        return "main";
    }
    @RequestMapping("/member")
    public String member(HttpSession session){

        return "member/member";
    }

    @RequestMapping("/logout")
    public String logout(){
        return "redirect:/index.htm";
    }



 /*
    登录真正的操作
     */
    //同步请求
    /*
    @RequestMapping("/doLogin")
    public String doLogin(String loginacct, String userpswd, String type, HttpSession session){

        Map<String,Object> paramMap=new HashMap<String, Object>();
        paramMap.put("loginacct",loginacct);
        paramMap.put("userpswd",userpswd);
        paramMap.put("type",type);
        User user=userService.queryUserLogin(paramMap); //查询得到User对象，放在session域，取出用户名显示
        session.setAttribute(Const.LOGIN_USER,user);
        //重定向，刷新页面不会重新提交表单
        return "redirect:/main.htm";

    }*/
    //异步请求
    @ResponseBody //结合Jackson组件，将返回结果转换为字符串，将json串以流的形式返回给客户端
    @RequestMapping("/doLogin")
    public Object doLogin(String loginacct, String userpswd, String type, String rememberme, HttpSession session, HttpServletResponse response){

        AjaxResult result=new AjaxResult();
        Object loginobj=new Object();

        try {
            Map<String,Object> paramMap=new HashMap<String, Object>();
            paramMap.put("loginacct",loginacct);
//            paramMap.put("userpswd",MD5Util.digest32(userpswd));
            paramMap.put("userpswd",userpswd);
            paramMap.put("type",type);

            if ("member".equals(type)){
                Member member=memberService.queryMemberLogin(paramMap);
                session.setAttribute(Const.LOGIN_MEMBER,member);

                if ("1".equals(rememberme)){
                    String logincode="\"loginacct="+member.getLoginacct()+"&userpswd="+member.getUserpswd()+"&logintype="+type+"\"";

                    System.out.println("用户存放到Cookie中的键值对为:logincode::::"+logincode);

                    Cookie cookie=new Cookie("logincode",logincode);
//                    cookie.setMaxAge(14*24*60*60); //单位秒
                    cookie.setMaxAge(60);
                    cookie.setPath("/");//表示任何请求路径都可以请求cookie

                    response.addCookie(cookie);
                }

            }else if ("user".equals(type)){

                User user=userService.queryUserLogin(paramMap); //查询得到User对象，放在session域，取出用户名显示
                session.setAttribute(Const.LOGIN_USER,user);

                if ("1".equals(rememberme)){
                    String logincode="\"loginacct="+user.getLoginacct()+"&userpswd="+user.getUserpswd()+"&logintype="+type+"\"";

                    System.out.println("用户存放到Cookie中的键值对为:logincode::::"+logincode);

                    Cookie cookie=new Cookie("logincode",logincode);
//                    cookie.setMaxAge(14*24*60*60); //单位秒
                    cookie.setMaxAge(60);
                    cookie.setPath("/");//表示任何请求路径都可以请求cookie

                    response.addCookie(cookie);
                }

                //        加载当前用户所拥有的权限
                List<Permission> myPermissions=userService.queryPermissionByUserid(user.getId());

                Permission permissionRoot=null;
                Map<Integer,Permission> map=new HashMap<Integer,Permission>();
                Set<String> myPermissionUrls=new HashSet<String>();
                for (Permission innerpermission:myPermissions){
                    map.put(innerpermission.getId(),innerpermission);
                    if (StringUtil.isNotEmpty(innerpermission.getUrl())){
                        myPermissionUrls.add("/"+innerpermission.getUrl());
                    }

                }
                session.setAttribute(Const.LOGIN_AUTH_PEREMISSION_URI,myPermissionUrls);

                for (Permission permission:myPermissions){
                    Permission child=permission;
                    if (child.getPid()==0){
                        permissionRoot=permission;
                    }else {
                        Permission parent=map.get(child.getPid());
                        parent.getChildren().add(child);
                    }
                }

                session.setAttribute("permissionRoot",permissionRoot);
                System.out.println("查询该用户所有权限成功");


            }
            result.setData(type);
            result.setSuccess(true);
            //{"success":true}

        }catch (Exception e){
            result.setMessage("登录失败");
            e.printStackTrace();
            result.setSuccess(false);
            //{"success":false, "message":"..."}
        }
        return result;


    }
    @RequestMapping("/error/error")
    public String error(){
        return "error/error";
    }




}
