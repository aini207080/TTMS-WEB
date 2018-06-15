<%@ page pageEncoding="UTF-8" isErrorPage="false" errorPage="../../nopower.jsp"%>
<%
	pageContext.setAttribute("APP_PATH", request.getContextPath());
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>首页</title>   
	<script src="${APP_PATH }/static/js/bootstrap.min.js"></script>
    <link href="${APP_PATH }/static/css/bootstrap.css" rel="stylesheet">
    <link href="${APP_PATH }/static/css/site.css" rel="stylesheet">
    <link href="${APP_PATH }/static/css/bootstrap-responsive.css" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style type="text/css">
    html, body {
        height: 100%;
    }
    </style>
  </head>
  <body>
    <div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container-fluid">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="#">${currentUserName}</a>
          <div class="btn-group pull-right">
            <a class="btn" href="${APP_PATH }/basic/OrdinaryUser/my-profile"><i class="icon-user"></i> ${currentUserName}</a>
            <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
              <span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
              <li><a href="${APP_PATH }/basic/OrdinaryUser/my-profile">个人资料</a></li>
              <li class="divider"></li>
              <li><a href="${APP_PATH }/basic/logout">退出</a></li>
            </ul>
          </div>
          <div class="nav-collapse">
            <ul class="nav">
            <li><a href="${APP_PATH }/basic/OrdinaryUser/nav">首页</a></li>
              <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">员工 <b class="caret"></b></a>
                <ul class="dropdown-menu">
                    <li><a href="#"data-toggle="modal" data-target="#NEWModal" data-whatever="new" >新建员工</a></li>
                    <li class="divider"></li>
                    <li><a href="${APP_PATH }/basic/Administrators/employees">管理员工</a></li>
                </ul>
              </li>
              <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">用户 <b class="caret"></b></a>
                <ul class="dropdown-menu">
                    <li><a href="#"data-toggle="modal" data-target="#NEWModal" data-whatever="new">新建用户</a></li>
                    <li class="divider"></li>
                    <li><a href="${APP_PATH }/basic/Administrators/user">管理用户</a></li>
                </ul>
              </li>
        <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">设施 <b class="caret"></b></a>
                <ul class="dropdown-menu">
                    <li><a href="${APP_PATH }/basic/Administrators/studio">演出厅管理</a></li>
                    <li class="divider"></li>
                    <li><a href="${APP_PATH }/basic/Administrators/seat">座位管理</a></li>
                </ul>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>

    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span3">
          <div class="well sidebar-nav">
            <ul class="nav nav-list">
              <li class="nav-header"><i class="icon-wrench"></i> 管理</li>
              <li ><a href="${APP_PATH }/basic/Administrators/employees">员工</a></li>
              <li><a href="${APP_PATH }/basic/Administrators/user">用户</a></li>
              <li><a href="${APP_PATH }/basic/Administrators/play">剧目</a></li>
              <li><a href="${APP_PATH }/basic/Administrators/schedule">演出计划</a></li>
              <li><a href="${APP_PATH }/basic/Administrators/sale">销售记录</a></li>
              <li><a href="${APP_PATH }/basic/Administrators/saleItem">销售详情</a></li>
              <li class="nav-header"><i class="icon-signal"></i> 设施</li>
              <li ><a href="${APP_PATH }/basic/Administrators/studio">演出厅管理</a></li>
              <li><a href="${APP_PATH }/basic/Administrators/seat">座位管理</a></li>
              <li class="nav-header"><i class="icon-user"></i> 资料</li>
              <li><a href="${APP_PATH }/basic/OrdinaryUser/my-profile">我的资料</a></li>
              <li><a href="${APP_PATH }/basic/OrdinaryUser/saleticket">售票</a></li>
              <li><a href="${APP_PATH }/basic/logout">退出</a></li> 
            </ul>
          </div>
        </div>
</body>
</html>