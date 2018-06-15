<%@ page pageEncoding="UTF-8" isErrorPage="false" errorPage="nopower.jsp"%>
<%
	pageContext.setAttribute("APP_PATH", request.getContextPath());
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>注册 - 星空剧院票务管理系统 - Thousands Film</title>
	<link rel="stylesheet" type="text/css" href="${APP_PATH }/static/css/register-login.css">
</head>
<body>
<div id="box"></div>
<div class="cent-box register-box">
	<div class="cent-box-header">
		<h1 class="main-title hide">星空剧院票务管理系统</h1>
		<h2 class="sub-title">电影改变生活 - Thousands Film</h2>
	</div>

	<div class="cont-main clearfix">
		<div class="index-tab">
			<div class="index-slide-nav">
				<a href="index.jsp">登录</a>
				<a href="register.jsp" class="active">注册</a>
				<div class="slide-bar slide-bar1"></div>				
			</div>
		</div>
		<form method="post" action="${APP_PATH }/basic/registSubmit" onsubmit="return checkForm()" class="register_form">
		<div class="register form">
			<div class="group">
				<div class="group-ipt email">
					<input type="email" name="email" id="email" class="ipt" placeholder="邮箱地址" onblur="checkemail()"  required>
				</div>
				<div class="group-ipt user">
					<input type="text" name="username" id="username" class="ipt" placeholder="输入一个用户名" onblur="checkuser()"  required>
				</div>
				<div class="group-ipt password">
					<input type="password" name="password" id="password" class="ipt" placeholder="设置登录密码" required>
				</div>
				<div class="group-ipt password1">
					<input type="password" name="password1" id="password1" class="ipt" placeholder="重复密码" onblur="checkpassword()"required>
				</div>
			</div>
		</div>

		<div class="button">
			<button type="submit" class="login-btn register-btn" id="button">注册</button>
		</div>
		</form>
		<label class="control-label"><div id="flag" style="color:red"></div></label>
	</div>
</div>

<div class="footer">
	<p>星空剧院票务管理系统 - Thousands Film</p>
	<p>Copyright &copy;2018-2019  All Rights Reserved</p>
</div>

<script src='${APP_PATH }/static/js/particles.js' type="text/javascript"></script>
<script src='${APP_PATH }/static/js/background.js' type="text/javascript"></script>
<script src='${APP_PATH }/static/js/jquery.min.js' type="text/javascript"></script>
<script src='${APP_PATH }/static/js/layer/layer.js' type="text/javascript"></script>
<script type="text/javascript">
var register="";
function checkemail() {
	//alert("开始检查邮箱是否存在.....!");
	var url = "basic/registSubmit?email="
			+ document.getElementById("email").value;
	if (window.XMLHttpRequest)
		req = new XMLHttpRequest();
	else if (window.ActiveXObject)
		req = new ActiveXObject("Microsoft.XMLHTTP");
	if (req) {
		req.open("get", url, true);
		//get方式可不加如下语句
		req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		req.onreadystatechange = complete;
		req.send(null);
		if(!(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/ .test(document.getElementById("email").value)))
			document.getElementById("flag").innerText = "邮箱格式不正确，请重新输入!";
		else
		document.getElementById("flag").innerText = "请稍后，正在检查邮箱!";
	}
}
function checkuser() {
	//alert("开始检查用户名是否存在.....!");
	var url = "basic/registSubmit?username="
			+ document.getElementById("username").value;
	if (window.XMLHttpRequest)
		req = new XMLHttpRequest();
	else if (window.ActiveXObject)
		req = new ActiveXObject("Microsoft.XMLHTTP");
	if (req) {
		req.open("get", url, true);
		//get方式可不加如下语句
		req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		req.onreadystatechange = complete;
		req.send(null);
		document.getElementById("flag").innerText = "请稍后，正在检查用户名!";
	}
}
function checkpassword() {
	//alert("开始检查邮箱是否存在.....!");
	var password=document.getElementById("password").value;
	var password1=document.getElementById("password1").value;
	if (password == password1)
		infoStr = "输入正确，请继续!";
	else
	{	
		infoStr = "两次密码输入不相同，请重新输入!";
		register="passworderror";
	}
	document.getElementById("flag").innerText = infoStr;
}
/*分析返回的XML文档*/
function complete() {
	if (req.readyState == 4) {
		if (req.status == 200) {
			var result = req.responseText;
			if (result == "useryes")
			{
				infoStr = "该用户名已经有人用了，请换个名字!";
				register="usererror";
			}
			else if(result == "userno")
				{
			     infoStr = "该用户名没有人用，请继续!";
			     register="";
				}
			else if(result == "emailyes")
			{
				infoStr = "该邮箱已经有人用了，请换个邮箱!";
				register="emailerror";
			}
			else if(result == "emailno")
				{
				infoStr = "该邮箱没有人用，请继续!";
				 register="";
				}
			document.getElementById("flag").innerText = infoStr;
		}
	}
}
function checkForm(){
	if(register=="usererror")
	{
		 document.getElementById('username').focus();
		 return false;
	}
	if(register=="emailerror")
	{
		 document.getElementById('email').focus();
		 return false;		
	}
	if(register=="passworderror")
	{
		 document.getElementById('password1').focus();
		 return false;		
	}
	else
		return true;
}
</script>
</body>
</html>