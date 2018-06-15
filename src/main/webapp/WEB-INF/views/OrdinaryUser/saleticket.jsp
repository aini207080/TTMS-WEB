<%@ page pageEncoding="UTF-8" isErrorPage="false" errorPage="../../nopower.jsp"%>
<%
	pageContext.setAttribute("APP_PATH", request.getContextPath());
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>售票</title>	

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
	<script type="text/javascript">
	var errorStatus=0;  //表单验证错误状态 0为正确 1为有错
	$(document).ready(function () { 
	        $('#playtable').bootstrapTable({
	            url: '${APP_PATH }/play/saleticket',         //请求后台的URL（*）
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
	            	visible:false,
	                field: 'playId',
	                title: 'ID',
	                align: 'center'//水平居中
	            }, {
	            	field: 'playTypeId',
	            	title: 'TypeId',
	            	align: 'center',//水平居中
	            	formatter : function(value, row, index) {
						var s = row.playTypeId + "";
						var ss;
						if (s == '5')
							ss = '古装剧';
						else if (s == '6')
							ss = '动漫片';
						else if (s == '7')
							ss = '生活剧';
						else if (s == '8')
							ss = '恐怖片';
						else if (s == '9')
							ss = '战争片';
						else if (s == '10')
							ss = '科幻片';
						else
							ss = '未知类型';
						return ss;
					}
	            },{
	                field: 'playLangId',
	                title: 'LangId',
	                align: 'center',//水平居中
	                formatter : function(value, row, index) {
						var s = row.playLangId + "";
						var ss;
						if (s == '11')
							ss = '国语';
						else if (s == '12')
							ss = '英语';
						else if (s == '13')
							ss = '粤语';
						else if (s == '14')
							ss = '法语';
						else if (s == '15')
							ss = '德语';
						else
							ss = '未知语言';
						return ss;
					}
	            }, {
	            	field: 'playName',
	            	title: 'Name',
	            	align: 'center'//水平居中
	            },{
	            	visible:false,
	            	field: 'playIntroduction',
	                title: 'Introduction', 
	                align: 'center'//水平居中
	            },{
	                field: 'playImage',
	                title: 'Image',
	                align: 'center',//水平居中
	                formatter:function(value,row,index){
	                     var s = '<a class = "view"  href="javascript:void(0)"><img style="width:50;height:50px;"  src="'+row.playImage+'" /></a>';
	                     return s;
	                    }
	            },{
	                field: 'playLength',
	                title: 'Length',
	                align: 'center'//水平居中
	            },{
	                field: 'playTicketPrice',
	                title: 'TicketPrice',
	                align: 'center'//水平居中
	            },{
	                field: 'playStatus',
	                title: 'Status',
	                align: 'center',//水平居中
	                formatter : function(value, row, index) {
						var s = row.playStatus + "";
						var ss;
						if (s == '1')
							ss = '正在热映';
						else if (s == '-1')
							ss = '已下线';
						else
							ss = '未上映';
						return ss;
					}
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
	    	var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
	    			pageNumber: params.pageNumber, 
	    		    pageSize: params.pageSize,
	    		    PlayName: $('#PlayName').val()   
		        };
		        return temp;  
    }  


	function operateFormatter(value, row, index) {//赋予的参数
	    return [
	    	'<a href="javascript:Sale()">查询</a>'
	    ].join('');
	}
	//查询事件 
	function Search(){
		$('#playtable').bootstrapTable('refresh',{pageNumber:1});
    } 
  
	function Sale(){
		var selectedRows=$('#playtable').bootstrapTable('getSelections');
		if(selectedRows.length!=1){
			alert("请选择一场电影！");
			return;
		}
		var Id=selectedRows[0].playId;
		window.location.href = "${APP_PATH }/basic/OrdinaryUser/saleschedule?playId="+ Id;
	}
    
	</script>
  </head>
  <body>
   <%@include file="../OrdinaryUser/nav.jsp" %>
        <div class="span9">
		  <div class="row-fluid">
			<div class="page-header">
				<h1>plays <small>正在上映的电影</small></h1>
				<div class="input-group">
				<input type="text" class="form-control"  id="PlayName" placeholder="请输入检索关键字" />
				<span class="input-group-btn">
					<button class="btn btn-primary" id="search_btn" onclick="Search()">检索</button>
				</span>
			</div>
				
   
        <table id="playtable"></table>
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
  </body>
</html>
