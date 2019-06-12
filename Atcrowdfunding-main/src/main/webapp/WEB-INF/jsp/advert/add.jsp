<%--
  Created by IntelliJ IDEA.
  User: cuicui
  Date: 2019/5/22
  Time: 16:04
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
    <meta name="author" content="">

    <link rel="stylesheet" href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/main.css">
    <link rel="stylesheet" href="${APP_PATH}/css/doc.min.css">
    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
    </style>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="advert.html">众筹平台 - 广告管理</a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <%@ include file="/WEB-INF/jsp/common/top.jsp"%>

            </ul>
            <form class="navbar-form navbar-right">
                <input type="text" class="form-control" placeholder="Search...">
            </form>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <%@ include file="/WEB-INF/jsp/common/left.jsp"%>


            <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="#">首页</a></li>
                <li><a href="#">数据列表</a></li>
                <li class="active">新增</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <form role="form" id="advertForm" action="" enctype="multipart/form-data" method="post">
                        <div class="form-group">
                            <label for="fadvertname">广告名称</label>
                            <input type="text" class="form-control" id="fadvertname" placeholder="请输入广告名称">
                        </div>
                        <div class="form-group">
                            <label for="fadverturl">广告地址</label>
                            <input type="text" class="form-control" id="fadverturl" placeholder="请输入广告地址">
                        </div>
                        <div class="form-group">
                            <label for="fadvertimage">广告图片</label>
                            <input type="file" class="form-control" id="fadvertimage" name="advimage" placeholder="请输入广告图片">
                        </div>
                        <button id="addAdvBtn" type="button" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                        <button type="button" id="resetAdvBtn" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/script/docs.min.js"></script>
<script src="${APP_PATH}/jquery/layer/layer.js"></script>
<script src="${APP_PATH}/jquery/jquery-form/jquery-form.min.js"></script>

<script type="text/javascript">
    $(function () {
        $(".list-group-item").click(function(){
            if ( $(this).find("ul") ) {
                $(this).toggleClass("tree-closed");
                if ( $(this).hasClass("tree-closed") ) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });
    });
    $("#addAdvBtn").click(function () {
        var options={
            url:"${APP_PATH}/advert/doAdd.do",
            data:{
                "name":$("#fadvertname").val(),
                "url":$("#fadverturl").val()
                // "icon":$("#fadvertimage").val()
            },
            beforeSubmit:function () {
                loadingIndex=layer.msg("数据正在保存中",{icon:6});
                return true;
            },
            success:function (result) {
                layer.close(loadingIndex);
                if (result.success){
                    layer.msg("广告数据保存成功",{time:1000,icon:6});
                    window.location.href="${APP_PATH}/advert/index.htm";
                }else {
                    layer.msg("广告数据保存失败",{time:1000,icon:5});

                }

            }
        };
        $("#advertForm").ajaxSubmit(options);//异步提交

        <%--同步请求--%>
        <%--$("#advertForm").attr("action","${APP_PATH}/advert/doAdd.do");--%>
        <%--$("#advertForm").submit();--%>
    });
    $("#resetAdvBtn").click(function () {
        $("#addForm")[0].reset();
    });
</script>
</body>
</html>

