<%@ page pageEncoding="UTF-8" isErrorPage="false" errorPage="../../nopower.jsp"%>
<%
	pageContext.setAttribute("APP_PATH", request.getContextPath());
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>员工管理</title>	

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="${APP_PATH }/static/js/jquery.min.js"></script>
	<script src="${APP_PATH }/static/js/bootstrap.js"></script>
    <link href="${APP_PATH }/static/css/bootstrap.css" rel="stylesheet">
    <link href="${APP_PATH }/static/css/site.css" rel="stylesheet">
    <link href="${APP_PATH }/static/css/bootstrap-responsive.css" rel="stylesheet">
    <link rel="stylesheet" href="${APP_PATH }/static/css/validationEngine.jquery.css">
    <script src="${APP_PATH }/static/js/jquery.validationEngine-zh_CN.js"></script>
	<script src="${APP_PATH }/static/js/jquery.validationEngine.js"></script>
    <script src="${APP_PATH }/static/js/bootstrap-table/bootstrap-table.min.js"></script>
    <link href="${APP_PATH }/static/js/bootstrap-table/bootstrap-table.min.css" rel="stylesheet" />
    <script src="${APP_PATH }/static/js/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
    <link href="${APP_PATH }/static/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" />  
	<script src="${APP_PATH }/static/bootstrap-datetimepicker/js/moment-with-locales.min.js"></script>  
	<script src="${APP_PATH }/static/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>  
	<script type="text/javascript">
	var errorStatus=0;  //表单验证错误状态 0为正确 1为有错
	$(document).ready(function () {
		    $('#schedTime').datetimepicker({  
		        format: 'YYYY-MM-DD hh:mm:ss',
		        locale: moment.locale('zh-cn')  
		    });
	        $('#scheduletable').bootstrapTable({
	            url: '${APP_PATH }/schedule/searchByPage',         //请求后台的URL（*）
	            method: 'get',                      //请求方式（*）
	            toolbar: '#toolbar',                //工具按钮用哪个容器
	            striped: true,                      //是否显示行间隔色
	            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
	            pagination: true,                   //是否显示分页（*）
	            sortable: false,                     //是否启用排序
	            sortOrder: "asc",                   //排序方式
	            queryParams: queryParams,//传递参数（*）
	            
	            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
	            pageNumber: 1,                       //初始化加载第一页，默认第一页
	            pageSize: 10,                       //每页的记录行数（*）
	            pageList:[5, 10, 20, 50],        //可供选择的每页的行数（*）
	            undefinedText: "空",				//当数据为 undefined 时显示的字符  
	            search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
	            contentType: "application/x-www-form-urlencoded",
	            strictSearch: false,
	            searchOnEnterKey:true,				//按回车搜索
	            showColumns: true,                  //是否显示所有的列
	            showRefresh: true,                  //是否显示刷新按钮
	            minimumCountColumns: 2,             //最少允许的列数
	            clickToSelect: true,                //是否启用点击选中行
	           
	            uniqueId: "operate",                     //每一行的唯一标识，一般为主键列
	            showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
	            cardView: false,                    //是否显示详细视图
	            detailView: false,                   //是否显示父子表
	            paginationPreText: "上一页",  
	            paginationNextText: "下一页",
	            columns: [
	            {
	               checkbox: true
	            },
	            {
	                field: 'schedId',
	                title: 'schedId',
	                align: 'center'//水平居中
	            }, {
	            	visible:false,
	            	field: 'studioId',
	            	title: 'studioId',
	            	align: 'center'//水平居中
	            },{
	            	field: 'studioName',
	            	title: 'studioName',
	            	align: 'center'//水平居中
	            },{
	            	visible:false,
	                field: 'playId',
	                title: 'playId',
	                align: 'center'//水平居中
	            }, {
	            	field: 'playName',
	            	title: 'playName',
	            	align: 'center'//水平居中
	            },{
	            	field: 'schedTime',
	            	title: 'schedTime',
	            	align: 'center'//水平居中
	            },{
	            	field: 'schedTicketPrice',
	                title: 'schedTicketPrice',
	                align: 'center'//水平居中
	            },
	            {
	                field: 'operate',
	                title: 'Action',
	                align: 'center',//水平居中
	                formatter: operateFormatter //自定义方法，添加操作按钮
	            },
	            ],
	            queryParamsType : "undefined",
	        });

	    });


	    //得到查询的参数
	    function queryParams(params) {  
	    	var tschedule = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
	    			pageNumber: params.pageNumber, 
	    		    pageSize: params.pageSize,
	    		    playId: $('#playsId').val()   
		        };
		        return tschedule;  
    }  

	    
	function operateFormatter(value, row, index) {//赋予的参数
	    return [
	    	'<a href="javascript:Update()">编辑</a>',
	    	'&nbsp&nbsp',
            '<a href="javascript:Delete()">删除</a>' 
	    ].join('');
	}
	//查询事件 
	function Search(){
		$('#scheduletable').bootstrapTable('refresh',{pageNumber:1});
    } 
  
    //编辑操作  
  	function Update(){
  		$('#dialog').validationEngine('hideAll');
  		resetValue();
  		$.ajax({
    		type: "get", //提交方式，也可以是get
    		url: "${APP_PATH }/schedule/scheduleadd",    //请求的url地址
    		dataType: "html",   //返回格式为json,也可以不添加这个属性，也可以是（xml、json、script 或 html）。
    		async: true, //请求是否异步，默认为异步，这也是ajax重要特性
    		//data: $('#useradd').serialize(),    //参数值 ,id是定义的要传的参数变量名，后台接收一致，value是页面取的传值的变量名，如果有多个，按照格式每组用逗号隔开，没有参数可以不用
    		beforeSend: function() {
    			//请求前的处理，如果没有可以不用写
    		},
    		success: function(result) {
    			var ss=result+"";
    			var str=ss.split("|");
				var i,j;
				var s1=str[0].split(",");
				var s2=str[1].split(",");
				var p1=str[2].split(",");
				var p2=str[3].split(",");
				$("#studioId").empty();
				$("#playId").empty();
    			for(i=0;i<s1.length-1;i++){
    				$("#studioId").append("<option value='"+s1[i]+"'>"+s2[i]+"</option>");
    			}
    			for(j=0;j<p1.length-1;j++){
    				$("#playId").append("<option value='"+p1[j]+"'>"+p2[j]+"</option>");
    			}
    			var selectedRows=$('#scheduletable').bootstrapTable('getSelections');
    			if(selectedRows.length!=1){
    				alert("请选择一条要修改的数据！");
    				return;
    			}
    			var row=selectedRows[0].schedId;
    			$('#scheduleModalLabel').text("信息修改");
    			$("#schedId").val(selectedRows[0].schedId);
    			$("#studioId").val(selectedRows[0].studioId);
    	   	 	$("#playId").val(selectedRows[0].playId);
    	   	 	$("#schedTime").val(selectedRows[0].schedTime);
    	   	 	$("#schedTicketPrice").val(selectedRows[0].schedTicketPrice);
    	    	$('#scheduleModal').modal('show');
    		},
    		complete: function() {
    			//请求完成的处理 ，如果没有，可以不用
    		},
    		error: function(XMLHttpRequest, textStatus, errorThrown) {
    			alert("出错了，没获取成功!"+XMLHttpRequest.status);
    		}
    	});
	}
    
    //增加操作
    function Add(){
    	$('#dialog').validationEngine('hideAll');
    	resetValue();
    	$('#scheduleModalLabel').text("增加演出计划");
    	$.ajax({
    		type: "get", //提交方式，也可以是get
    		url: "${APP_PATH }/schedule/scheduleadd",    //请求的url地址
    		dataType: "html",   //返回格式为json,也可以不添加这个属性，也可以是（xml、json、script 或 html）。
    		async: true, //请求是否异步，默认为异步，这也是ajax重要特性
    		//data: $('#useradd').serialize(),    //参数值 ,id是定义的要传的参数变量名，后台接收一致，value是页面取的传值的变量名，如果有多个，按照格式每组用逗号隔开，没有参数可以不用
    		beforeSend: function() {
    			//请求前的处理，如果没有可以不用写
    		},
    		success: function(result) {
    			var ss=result+"";
    			var str=ss.split("|");
				var i,j;
				var s1=str[0].split(",");
				var s2=str[1].split(",");
				var p1=str[2].split(",");
				var p2=str[3].split(",");
				$("#studioId").empty();
				$("#playId").empty();
    			for(i=0;i<s1.length-1;i++){
    				$("#studioId").append("<option value='"+s1[i]+"'>"+s2[i]+"</option>");
    			}
    			for(j=0;j<p1.length-1;j++){
    				$("#playId").append("<option value='"+p1[j]+"'>"+p2[j]+"</option>");
    			}
    			$('#scheduleModal').modal('show');
    		},
    		complete: function() {
    			//请求完成的处理 ，如果没有，可以不用
    		},
    		error: function(XMLHttpRequest, textStatus, errorThrown) {
    			alert("出错了，没获取成功!"+XMLHttpRequest.status);
    		}
    	});
	}
    $(function () {
		$('#dialog').validationEngine('attach', {
		promptPosition: 'inline',      
		focusFirstField: false,          
		autoPositionUpdate: false,       
		addPromptClass: 'formError-white',
	 	maxErrorsPerField: '1',           	
	 	scroll: true,
    });
    });

    //保存操作
    function resetValue(){
    	$("#schedId").val("");
		$("#studioId").val("");
   	 	$("#playId").val("");
   	 	$("#schedTime").val("");
   	 	$("#schedTicketPrice").val("");
 	}
    function save(){
    	if(!$('#dialog').validationEngine('validate')){
    	    return;
    	  }
    	if(errorStatus==1){
    		return;
    	}
    	$.ajax({
    		type: "post", //提交方式，也可以是get
    		url: "${APP_PATH }/schedule/save",    //请求的url地址
    		dataType: "json",   //返回格式为json,也可以不添加这个属性，也可以是（xml、json、script 或 html）。
    		async: true, //请求是否异步，默认为异步，这也是ajax重要特性
    		data: $('#dialog').serialize(),    //参数值 ,id是定义的要传的参数变量名，后台接收一致，value是页面取的传值的变量名，如果有多个，按照格式每组用逗号隔开，没有参数可以不用
    		beforeSend: function() {
    			//请求前的处理，如果没有可以不用写
    		},
    		success: function(result) {
    			if(result.errorMsg){
					alert(result.errorMsg);
					return;
				}else{
					alert("保存成功");
					resetValue();
					$('#scheduleModal').modal('hide');
					$('#scheduletable').bootstrapTable('refresh');
				}
    		},
    		complete: function() {
    			//请求完成的处理 ，如果没有，可以不用
    		},
    		error: function(XMLHttpRequest, textStatus, errorThrown) {
    			alert("出错了，没提交成功!"+XMLHttpRequest.status);
    		}
    	});
    }
    
    //删除操作 
    function Delete(){
		var selectedRows=$('#scheduletable').bootstrapTable('getSelections');
		if(selectedRows.length==0){
			alert("请选择要删除的数据！");
			return;
		}
		var strIds=[];
		for(var i=0;i<selectedRows.length;i++){
			strIds.push(selectedRows[i].schedId);
		}
		var ids=strIds.join(",");
		if(confirm("您确认要删掉这"+selectedRows.length+"条数据吗？"))
		{
				$.post("${APP_PATH }/schedule/delete",{delIds:ids},function(result){
					if(result.success){
						alert("您已成功删除"+result.delNums+"条数据！");
						$('#scheduletable').bootstrapTable('refresh');
						location.reload();
					}else{
						alert(selectedRows[result.errorIndex].ID+result.errorMsg);
						location.reload();
					}
				},"json");
		}
	}
    
    //输入框ajax验证
    function ajaxNumber(){
    	var number="number";
    	var value=document.getElementById("number").value;
    	$.ajax({
    		type: "get", //提交方式，也可以是get
    		url: "${APP_PATH }/schedule/search",    //请求的url地址
    		dataType: "json",   //返回格式为json,也可以不添加这个属性，也可以是（xml、json、script 或 html）。
    		async: true, //请求是否异步，默认为异步，这也是ajax重要特性
    		data: {fieldId:number,fieldValue:value} ,   //参数值 ,id是定义的要传的参数变量名，后台接收一致，value是页面取的传值的变量名，如果有多个，按照格式每组用逗号隔开，没有参数可以不用
    		beforeSend: function() {
    			//请求前的处理，如果没有可以不用写
    			document.getElementById("numberStatus").innerText ="* 正在确认编号是否有其他人使用，请稍等。";
    		},
    		success: function(result) {
    			if(result.success){
    				document.getElementById("numberStatus").innerText ="";
    				errorStatus=0;
				}else{
					document.getElementById("numberStatus").innerText ="* 此编号已被其他人使用";
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
	</script>
  </head>
  <body>
   <%@include file="../OrdinaryUser/nav.jsp" %>
        <div class="span9">
		  <div class="row-fluid">
			<div class="page-header">
				<h1>schedule <small>所有演出计划</small></h1>
				<div class="input-group">
				<input type="text" class="form-control"  id="playsId" placeholder="请输入检索关键字" />
				<span class="input-group-btn">
					<button class="btn btn-primary" id="search_btn" onclick="Search()">检索</button>
				</span>
			</div>
				
        <div id="toolbar" class="btn-group">
            <button id="btn_add" type="button" class="btn btn-default" onclick="Add()">
                <span aria-hidden="true"></span><i class="icon-plus"></i>新增
            </button>
            <button id="btn_edit" type="button" class="btn btn-default" onclick="Update()">
                <span aria-hidden="true"></span><i class="icon-pencil"></i>修改
            </button>
            <button id="btn_delete" type="button" class="btn btn-default" onclick="Delete()">
                <span aria-hidden="true"></span><i class="icon-remove"></i>删除
            </button>
        </div>
        <table id="scheduletable"></table>
    </div>
			
		  </div>
        </div>

      <hr>

      <footer class="well">
       <div class="ff">
			本站介绍:售票管理 信息管理及分析 方便快捷
		</div>
		<div class="fl">Copyright &copy;2018-2019 星空影院管理系统 All Rights Reserved</div>
      </footer>
<div class="modal fade" id="scheduleModal" tabindex="-1" role="dialog" aria-labelledby="scheduleModalLabel" aria-hidden="true">
  	<div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="scheduleModalLabel">演出计划模态框</h4>
      </div>
      <div class="modal-body">
        <form id="dialog"  name="dialog" method="post" action="#">
      						<div class="control-group">
						<label class="control-label" for="schedId">schedId</label>
						<div class="controls">
							<input type="text" class="input-xlarge" id="schedId"  name="schedId"  readonly="readonly"/>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="studioId">studioId</label>
							<div class="controls">
								<select  id="studioId"  name="studioId"></select>
							</div>
					</div>
						<div class="control-group">
						<label class="control-label" for="playId">playId</label>
						<div class="controls">
							<select  id="playId"  name="playId"></select>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="schedTime">schedTime</label>
						<div class="controls">
							<input type="text" class="input-xlarge" id="schedTime" name="schedTime" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="schedTicketPrice">schedTicketPrice</label>
						<div class="controls">
							<input type="text" class="validate[maxSize[12]] input-xlarge" id="schedTicketPrice" name= "schedTicketPrice"       placeholder="schedTicketPrice" />
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
