<%@ page pageEncoding="UTF-8" isErrorPage="false" errorPage="nopower.jsp"%>
<%
	pageContext.setAttribute("APP_PATH", request.getContextPath());
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>登录 - 星空剧院票务管理系统 - Thousands Film</title>
	<link rel="stylesheet" type="text/css" href="${APP_PATH }/static/css/register-login.css">
<script  type="text/javascript">
function checkusername()
{
    var regex=/^[0-9a-zA-Z]+$/;
    var s=document.getElementById("username").value;
    if(regex.test(s)){
    	document.getElementById("errormess").innerHTML="";
    	return true;
    }else{
    	 document.getElementById("errormess").innerHTML="用户名只能填写数字与英文字母";
    	 return false;
    }
}

</script>
</head>
<body>
<div id="box"></div>
<div class="cent-box">
	<div class="cent-box-header">
		<h1 class="main-title hide">星空剧院票务管理系统</h1>
		<h2 class="sub-title">电影改变生活 - Thousands Film</h2>
	</div>

	<div class="cont-main clearfix">
		<div class="index-tab">
			<div class="index-slide-nav">
				<a href="index.jsp" class="active">登录</a>
				<a href="register.jsp">注册</a>
				<div class="slide-bar"></div>				
			</div>
		</div>
		<form method="post" action="${APP_PATH }/basic/loginSubmit" class="login">
		<div class="login form">
			<div class="group">
				<div class="group-ipt username">
					<input type="text" name="username" id="username" class="ipt" placeholder="输入您的用户名" required>
				</div>
				<div class="group-ipt password">
					<input type="password" name="password" id="password" class="ipt" placeholder="输入您的登录密码" required>
				</div>
			</div>
		</div>

		<div class="button">
			<button type="submit" class="login-btn register-btn" onclick="return checkusername()" id="button">登录</button>
		</div>
		</form>
		<div class="remember clearfix">
			<label class="remember-me"><span class="icon"><span class="zt"></span></span><input type="checkbox" name="remember-me" id="remember-me" class="remember-mecheck" checked>记住我</label>
			<label class="forgot-password">
				<a href="#">忘记密码？</a>
			</label>
		<div style="color:red" id="errormess">${desc}</div>
		</div>
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
<script>
	$("#remember-me").click(function(){
		var n = document.getElementById("remember-me").checked;
		if(n){
			$(".zt").show();
		}else{
			$(".zt").hide();
		}
	});
</script>
</body>
</html>