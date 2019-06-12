<%--
  Created by IntelliJ IDEA.
  User: cuicui
  Date: 2019/5/18
  Time: 20:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
    <link rel="stylesheet" href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/login.css">
    <style>

    </style>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <div><a class="navbar-brand" href="index.html" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
    </div>
</nav>

<div class="container">


    <form id="loginForm" action="${APP_PATH}/doLogin.do" method="post" class="form-signin" role="form">
        ${exception.message}
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 用户登录</h2>

        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="floginacct" name="loginacct" placeholder="请输入登录账号" autofocus>
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="password" class="form-control" id="fuserpswd" name="userpswd" placeholder="请输入登录密码" style="margin-top:10px;">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <select id="ftype" class="form-control" name="type">
                <option value="member">会员</option>
                <option value="user" >管理</option>
            </select>
        </div>
        <div class="checkbox">
            <label>
                <input type="checkbox" value="1" id="rememberme"> 记住我两周
            </label>
            <br>
            <label>
                忘记密码
            </label>
            <label style="float:right">
                <a href="reg.html">我要注册</a>
            </label>
        </div>
        <a class="btn btn-lg btn-success btn-block" onclick="dologin()" > 登录</a>
    </form>
</div>
<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/jquery/layer/layer.js"></script>
<script>
    function dologin() {
        // $("#loginForm").submit(); //同步提交表单

        //异步请求
        var floginacct = $("#floginacct");
        var fuserpswd = $("#fuserpswd");
        var ftype = $("#ftype");

        //对于表单数据而言，不能用null进行判断。如果文本框不输入，获取到的是空串
        if (floginacct.val().trim() == ""){
            layer.msg("用户账号不能为空，请重新输入！",{time:1000,icon:5,shift:6},function(){

                floginacct.val("");
                floginacct.focus();//获取焦点

                });
            return false;

        }
        if (fuserpswd.val().trim() == ""){
            layer.msg("密码不能为空，请重新输入！",{time:1000,icon:6,shift:6},function () {
                fuserpswd.focus();//获取焦点
            });

            return false;
        }
        var loadingIndex=-1;

        var flag=$("#rememberme")[0].checked;

        $.ajax({ //键值对
            type:"POST",
            data:{//封装成json对象,由id选择器获取参数
                "loginacct":floginacct.val(),
                "userpswd":fuserpswd.val(),
                "type":ftype.val(),
                "rememberme":flag?"1":"0"
            },
            url:"${APP_PATH}/doLogin.do", //请求地址
            beforeSend:function(){//发送请求前，异步扩展功能
                loadingIndex=layer.msg("正在登录，请稍后...",{time:5000,icon:16});
                //一般做表单数据校验

                return true;
            },
            success:function (result) {//{"success":true} 或 {"success":false, "message":"..."}
                layer.close(loadingIndex);
                if (result.success){
                    if ("member"==result.data){
                        window.location.href="${APP_PATH}/member.htm"; //由页面发出同步请求到达主页面

                    }else if ("user"==result.data) {
                        //跳转主页面
                        window.location.href="${APP_PATH}/main.htm"; //由页面发出同步请求到达主页面
                    }else {
                        layer.msg("登录类型不合法")
                    }

                }else {


                    layer.msg(result.message,{time:1000,icon:5});
                }

            },
            error:function () {
                layer.msg("登录失败！",{time:1000,icon:5});


            }
        });
        // var type = $(":selected").val();
        // if ( type == "user" ) {
        //     window.location.href = "main.html";
        // } else {
        //     window.location.href = "index.html";
        // }
    }
</script>
</body>
</html>
