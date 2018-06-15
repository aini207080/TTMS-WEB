<%@ page pageEncoding="UTF-8" isErrorPage="false" errorPage="../../nopower.jsp"%>
<%
	pageContext.setAttribute("APP_PATH", request.getContextPath());
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>个人资料</title>	

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<script src="${APP_PATH }/static/js/jquery.min.js"></script>
	<script src="${APP_PATH }/static/js/bootstrap.js"></script>
    <link href="${APP_PATH }/static/css/bootstrap.css" rel="stylesheet">
    <link href="${APP_PATH }/static/css/site.css" rel="stylesheet">
    <link href="${APP_PATH }/static/css/bootstrap-responsive.css" rel="stylesheet">
    <link rel="stylesheet" href="${APP_PATH }/static/css/validationEngine.jquery.css">
    <script src="${APP_PATH }/static/js/jquery.validationEngine-zh_CN.js"></script>
	<script src="${APP_PATH }/static/js/jquery.validationEngine.js"></script>
	<script src="${APP_PATH }/static/js/fileinput.js"></script>
	<link href="${APP_PATH }/static/css/fileinput.css" rel="stylesheet">
    <script type="text/javascript">
    var errorStatus=0;  //表单验证错误状态 0为正确 1为有错
    $(function(){
    	$.ajax({
    		type: "Get", //提交方式，也可以是get
    		url: "${APP_PATH }/employees/ajax",    //请求的url地址
    		dataType: "json",   //返回格式为json,也可以不添加这个属性，也可以是（xml、json、script 或 html）。
    		async: true, //请求是否异步，默认为异步，这也是ajax重要特性
    		//data: {id: '0'},    //参数值 ,id是定义的要传的参数变量名，后台接收一致，value是页面取的传值的变量名，如果有多个，按照格式每组用逗号隔开，没有参数可以不用
    		beforeSend: function() {
    			//请求前的处理，如果没有可以不用写
    		},
    		success: function(data) {
    			$('#emp_id').val(data.emp_id);
    			$('#emp_no').val(data.emp_no);
    			$('#emp_name').val(data.emp_name);
    			$('#emp_tel_num').val(data.emp_tel_num);
    			$('#emp_addr').val(data.emp_addr);
    			$('#emp_email').val(data.emp_email);
    			$('#role').val(data.type);
    		},
    		complete: function() {
    			//请求完成的处理 ，如果没有，可以不用
    		},
    		error: function(XMLHttpRequest, textStatus, errorThrown) {
    			alert("出错了，没取到啊!"+XMLHttpRequest.status);
    		}
    	});
    });
    $(function () {
		$('#myform').validationEngine('attach', {
		promptPosition: 'inline',      
		focusFirstField: false,          
		autoPositionUpdate: false,       
		addPromptClass: 'formError-white',
	 	maxErrorsPerField: '1',           	
	 	scroll: true,
    });
		$('#Password').validationEngine('attach', {
			promptPosition: 'inline',      
			focusFirstField: false,          
			autoPositionUpdate: false,       
			addPromptClass: 'formError-white',
		 	maxErrorsPerField: '1',           	
		 	scroll: true,
	    });
    });
    function isPic()
    {
        var pic=myform.file.value;
        var ext=pic.substring(pic.lastIndexOf(".")+1);
        if(errorStatus==1){
    		return false;
    	}
        //可以再添加允许类型
        if(ext.toLowerCase()=="jpg" || ext.toLowerCase()=="png" || ext.toLowerCase()=="gif")
            return true;
        else
        {
        	alert("只能上传jpg、png、gif图像!");
            return false;
        }
    }
    function PassChange(){
    	$('#PasswordModal').modal('show');
    }
    function save(){
    	if(!$('#Password').validationEngine('validate')){
    	    return;
    	  }
    	if(errorStatus==1){
    		return;
    	}
    	$.ajax({
    		type: "Get", //提交方式，也可以是get
    		url: "${APP_PATH }/employees/passchange",    //请求的url地址
    		dataType: "json",   //返回格式为json,也可以不添加这个属性，也可以是（xml、json、script 或 html）。
    		async: true, //请求是否异步，默认为异步，这也是ajax重要特性
    		data: $('#Password').serialize(),    //参数值 ,id是定义的要传的参数变量名，后台接收一致，value是页面取的传值的变量名，如果有多个，按照格式每组用逗号隔开，没有参数可以不用
    		beforeSend: function() {
    			//请求前的处理，如果没有可以不用写
    		},
    		success: function(data) {
    			if(data.success){
    				alert("您已成功更改密码！");
    				$('#PasswordModal').modal('hide');
    			}else{
    				alert(data.errorMsg+"");
    			}
    		},
    		complete: function() {
    			//请求完成的处理 ，如果没有，可以不用
    		},
    		error: function(XMLHttpRequest, textStatus, errorThrown) {
    			alert("出错了!"+XMLHttpRequest.status);
    		}
    	});
    }
  //输入框ajax验证
    function ajaxPhone(){
    	var phone="phone";
    	var value=document.getElementById("emp_tel_num").value;
    	$.ajax({
    		type: "get", //提交方式，也可以是get
    		url: "${APP_PATH }/employees/search",    //请求的url地址
    		dataType: "json",   //返回格式为json,也可以不添加这个属性，也可以是（xml、json、script 或 html）。
    		async: true, //请求是否异步，默认为异步，这也是ajax重要特性
    		data: {fieldId:phone,fieldValue:value} ,   //参数值 ,id是定义的要传的参数变量名，后台接收一致，value是页面取的传值的变量名，如果有多个，按照格式每组用逗号隔开，没有参数可以不用
    		beforeSend: function() {
    			//请求前的处理，如果没有可以不用写
    			document.getElementById("phoneStatus").innerText ="* 正在确认电话号码是否有其他人使用，请稍等。";
    		},
    		success: function(result) {
    			if(result.success){
    				document.getElementById("phoneStatus").innerText ="";
    				errorStatus=0;
				}else{
					document.getElementById("phoneStatus").innerText ="* 此电话号码已被其他人使用";
					errorStatus=1;
				}
    		},
    		complete: function() {
    			//请求完成的处理 ，如果没有，可以不用
    		},
    		error: function(XMLHttpRequest, textStatus, errorThrown) {
    			alert("出错了，没查询成功!"+XMLHttpRequest.status);
    		}
    	});
	}
    function ajaxEmail(){
    	var email="email";
    	var value=document.getElementById("emp_email").value;
    	$.ajax({
    		type: "get", //提交方式，也可以是get
    		url: "${APP_PATH }/employees/search",    //请求的url地址
    		dataType: "json",   //返回格式为json,也可以不添加这个属性，也可以是（xml、json、script 或 html）。
    		async: true, //请求是否异步，默认为异步，这也是ajax重要特性
    		data: {fieldId:email,fieldValue:value} ,   //参数值 ,id是定义的要传的参数变量名，后台接收一致，value是页面取的传值的变量名，如果有多个，按照格式每组用逗号隔开，没有参数可以不用
    		beforeSend: function() {
    			//请求前的处理，如果没有可以不用写
    			document.getElementById("emailStatus").innerText ="* 正在确认邮箱是否有其他人使用，请稍等。";
    		},
    		success: function(result) {
    			if(result.success){
    				document.getElementById("emailStatus").innerText ="";
    				errorStatus=0;
				}else{
					document.getElementById("emailStatus").innerText ="* 此邮箱已被其他人使用";
					errorStatus=1;
				}
    		},
    		complete: function() {
    			//请求完成的处理 ，如果没有，可以不用
    		},
    		error: function(XMLHttpRequest, textStatus, errorThrown) {
    			alert("出错了，没查询成功!"+XMLHttpRequest.status);
    		}
    	});
	}
    function EqualsPassWord(){
    	var pass1=document.getElementById("passwordnew").value;
    	var pass2=document.getElementById("passwordnew2").value;
    	document.getElementById("passwordStatus").innerText ="* 正在确认两次密码输入是否相同，请稍等。";
    	if(pass1==pass2){
    		document.getElementById("passwordStatus").innerText ="";
    		errorStatus=0;
    	}else{
    		document.getElementById("passwordStatus").innerText ="* 两次密码输入不相同，请重新输入。";
    		errorStatus=1;
    	} 	
    }
</script>
  </head>
  <body>
   <%@include file="nav.jsp" %>
        <div class="span9">
		  <div class="row-fluid">
			<div class="page-header">
				<h1>My profile <small>更新信息</small></h1>
			</div>
			<form id="myform" name="myform" class="form-horizontal" action="${APP_PATH }/employees/updateMyprofile" class="saveEmployee" method="post" enctype="multipart/form-data">
				<fieldset>
					<div class="control-group">
						<label class="control-label form-label" for="name">HeadPortrait</label>
						<div class="controls">
							<div class="file-loading">
								<input type="file" class="file" name="file" id="file">
							</div>
						</div>
					</div>
					<div class="control-group" style="display:none;">
						<label class="control-label" for="name">Id</label>
						<div class="controls">
							<input type="text" class="input-xlarge" id="emp_id" name="emp_id" value="" />
						</div>
					</div>
					<div class="control-group" style="display:none;">
						<label class="control-label" for="name">Number</label>
						<div class="controls">
							<input type="text" class="input-xlarge" id="emp_no" name="emp_no" value="" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="name">Name<span style="color:#F00">*</span></label>
						<div class="controls">
							<input type="text" class="validate[required,maxSize[100],custom[chinese]] input-xlarge" id="emp_name" name="emp_name" value="" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="email">E-mail</label>
						<div class="controls">
							<input type="text" class="validate[maxSize[100],custom[email]] input-xlarge" id="emp_email" name="emp_email" onblur="ajaxEmail()" value="email" />
							<span  class="help-inline" style="color:#F00" id="emailStatus"></span>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="phone">Phone</label>
						<div class="controls">
							<input type="text" class="validate[maxSize[20],custom[phone]] input-xlarge" id="emp_tel_num" name="emp_tel_num" onblur="ajaxPhone()" value="phone" />
							<span  class="help-inline" style="color:#F00" id="phoneStatus"></span>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="address">Address</label>
						<div class="controls">
							<input type="text" class="input-xlarge" id="emp_addr" name="emp_addr" value="address" />
						</div>
					</div>	
					<div class="control-group">
						<label class="control-label" for="role">Role</label>
						<div class="controls">
							<input type="text" class="input-xlarge" id="role"  name="role" value="role"  readonly="true"/>
						</div>
					</div>	
					<div class="control-group">
						<label class="control-label" for="password">Modify_password</label>
						<div class="controls">
							<input type="button" class="input-xlarge" id="password" value="Modify" onclick="PassChange()"/>
						</div>
					</div>
					<div style="color:red">${desc}</div>
					<div class="form-actions">
						<input type="submit" class="btn btn-success btn-large" onclick="return isPic()" value="Save Changes" /> <a class="btn" href="my-profile.jsp">Cancel</a>
					</div>					
				</fieldset>
			</form>
		  </div>
        </div>
      </div>

      <hr>

      <footer class="well">
        <div class="ff">
			本站介绍:售票管理 信息管理及分析 方便快捷
		</div>
		<div class="fl">Copyright &copy;2017-2018 星空影院管理系统 All Rights Reserved</div>
      </footer>
	
    </div>
    <div class="modal fade" id="PasswordModal" tabindex="-1" role="dialog" aria-labelledby="PasswordModalLabel" aria-hidden="true">
  	<div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="PasswordModalLabel">修改密码</h4>
      </div>
      <div class="modal-body">
        <form id="Password"  name="Password" method="post" action="#">
        				<input type="text" class="input-xlarge" id="id" name="id" value="${currentUserName}" style="display:none"/>
      						<div class="control-group">
						<label class="control-label" for="passwordold">原始密码<span style="color:#F00">*</span></label>
						<div class="controls">
							<input type="password" class="validate[required,maxSize[20],custom[onlyLetterNumber]] input-xlarge" id="passwordold"  name="passwordold" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="passwordnew">新密码<span style="color:#F00">*</span></label>
						<div class="controls">
							<input type="password" class="validate[required,maxSize[20],custom[onlyLetterNumber]] input-xlarge" id="passwordnew" name="passwordnew"  />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="passwordnew2">再次输入<span style="color:#F00">*</span></label>
						<div class="controls">
							<input type="password" class="validate[required,maxSize[20],custom[onlyLetterNumber]] input-xlarge"  onblur="EqualsPassWord()" id="passwordnew2" name="passwordnew2"  />
							<span  class="help-inline" style="color:#F00" id="passwordStatus"></span>
						</div>
					</div>
        </form>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" onclick="save()">确认修改</button>
      </div>
    </div>
  </div>
</div>
    </div>
  </body>
</html>
