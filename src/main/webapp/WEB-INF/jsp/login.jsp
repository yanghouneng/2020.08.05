<<%@ page language="java" contentType="text/html; charset=UTF-8"
          pageEncoding="UTF-8"%>
<%@taglib uri="http://shiro.apache.org/tags"  prefix="shiro"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!doctype html>
<html lang="en">
<head>
    <base href="<%=basePath%>" />
    <meta charset="UTF-8">
    <title>企业资产管理系统</title>
    <meta name="renderer" content="webkit|ie-comp|ie-stand"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="/static/css/font.css">
    <link rel="stylesheet" href="/static/css/xadmin.css">
    <script type="text/javascript"
            src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript" src="/static/lib/layui/layui.js"
            charset="utf-8"></script>
    <script type="text/javascript" src="/static/js/xadmin.js"></script>
    <script type="text/javascript">
        //如果该页面在iframe中出现，那么会自动调用最外层窗口刷新本链接
        if (window != top) {
            top.location.href = location.href;
        }
    </script>

</head>
<body class="login-bg">
<%--<shiro:guest>
	游客身份
</shiro:guest><br>--%>
<div class="login" >
    <div class="message">xxxx管理系统-用户登录</div>
    <font  id="error" size="10" class="text-align:center" color="red"></font>
    <div id="darkbannerwrap"></div>
    <form method="post" class="layui-form">
        <input  placeholder="用户名" type="text" name="username" id="a1"
                class="layui-input">
        <hr class="hr15">
        <input  placeholder="密码" type="password" name="password" id="a2"
                class="layui-input">
        <hr class="hr15">
        <input lay-submit lay-filter="login" style="width: 100%;"
               type="button" value="登录" onclick="login()" />
        <hr class="hr20">
        <div class="layui-form-item">
            <div class="layui-form-item" pane="">

                <input type="checkbox" lay-filter="filter"  checked="c"  id="memoryuser" lay-skin="primary" title="30天内自动登录" >

            </div>
        </div>
    </form>

</div>

<script>
    //该文档加载事件完成的功能时：退出shiro用户
    $(function () {
        $.ajax({
            type:"post",
            url:"/user/logout.do",
            dataType:"json",
            success:function (info) {
                /*alert(info.personName);
                alert(info.personPassword);*/
                if(info.personName!=""){
                    //将值显示到表单中
                    $("#a1").val(info.personName);
                    $("#a2").val(info.personPassword);
                    $("#memoryuser").prop("checked","checked");
                }

            },
            error:function () {
                layer.msg('ajax请求失败', {icon: 2});
            }
        })
    })
    //进行登录处理
    function login() {
        //得到复选框的值
        alert($("form").serialize())
        var checkState = $("#memoryuser").prop("checked");
        var remember = "NO";
        if(checkState){
            remember = "YES";
        }
        $.ajax({
            type:"post",
            url:"/user/checkLogin.do?remember="+remember,
            dataType:"text",
            data:$("form").serialize(),
            success:function (info) {
                console.log(info)
                if(info=="success"){
                    /*layer.msg("登录成功", {icon: 1});*/
                    window.location.href = "/user/success.do";
                }else{
                    layer.msg('账号或者密码错误！', {icon: 2});
                }
            },
            error:function () {
                layer.msg('ajax请求失败', {icon: 2});
            }
        })
    }



</script>


</body>
</html>
