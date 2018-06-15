<%@ page pageEncoding="UTF-8" isErrorPage="false" errorPage="../../nopower.jsp"%>
<%
	pageContext.setAttribute("APP_PATH", request.getContextPath());
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">   
	<title>座位管理</title>	

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<script src="${APP_PATH }/static/js/jquery.min.js"></script>
	<script src="${APP_PATH }/static/js/bootstrap.js"></script>
    <link href="${APP_PATH }/static/css/bootstrap.css" rel="stylesheet">
    <link href="${APP_PATH }/static/css/site.css" rel="stylesheet">
    <link href="${APP_PATH }/static/css/bootstrap-responsive.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="${APP_PATH }/static/css/jquery.seat-charts.css">
	<link rel="stylesheet" type="text/css" href="${APP_PATH }/static/css/salestyle.css">
	<script src="${APP_PATH }/static/js/jquery.seat-charts.js"></script>
	<script  type="text/javascript">
function GetQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}
var seatStatusMap={};
var str=[];
$(document).ready(function() {
var x=GetQueryString("x");     //演出厅座位行
var y=GetQueryString("y");		//演出厅座位列
var name=GetQueryString("name");  //演出厅名字
var studio_id=GetQueryString("studio_id");       //演出厅座位ID
//ajax同步获取数据库中演出厅座位状态字符串
$.ajax({              
	type: "Get", //提交方式，也可以是get
	url: "${APP_PATH }/seat/getstatus",    //请求的url地址
	dataType: "html",   //返回格式为json,也可以不添加这个属性，也可以是（xml、json、script 或 html）。
	async: false, //请求是否异步，默认为异步，这也是ajax重要特性
	data: {studio_id: studio_id},    //参数值 ,id是定义的要传的参数变量名，后台接收一致，value是页面取的传值的变量名，如果有多个，按照格式每组用逗号隔开，没有参数可以不用
	beforeSend: function() {
		//请求前的处理，如果没有可以不用写
	},
	success: function(result) {
		var ss=result+"";
		str=ss.split(",");
	},
	complete: function() {
		//请求完成的处理 ，如果没有，可以不用
	},
	error: function(XMLHttpRequest, textStatus, errorThrown) {
		alert("出错了，没取到啊!"+XMLHttpRequest.status);
	}
});
//将ajax获取到的字符串转换为map
var k=0;
var i,j;
var sssss;
for(i=1;i<=x;i++){
	for(j=1;j<=y;j++){
		sssss=i+"_"+j;
		seatStatusMap[sssss]=str[k];
		k++;
	}
}
//座位状态对应map
var classMap = {'1' : 'available', '-1' : 'sellout', '0' : 'unavailable'};
//改变演出厅名字
document.getElementById('hname').innerHTML =name; 
//初始化演出厅座位图
var map = new Array();
map = [];
for(var i=0;i<x;i++){
    map[i]="";
    for(var j=0;j<y;j++){
        map[i]+="c";
    }
}
$('#seat-maps').empty();
$("#legend").empty();
var sc = $('#seat-maps').seatCharts({
                map: map,
                naming: {
                	top    : true,
					left   : true,
					getId  : function(character, row, column) {
						return row + '_' + column;
					}
                },
                legend: {
                    node: $('#legend'),
                    items: [
                    	['a', 'available', '正常'], 
                    	['b', 'sellout', '损坏'],
                        ['c', 'unavailable', '过道'],
                    ]
                },
                click: function() {
                     if (this.status() == 'available') { //若为可选座状态，添加座位 
                     		return 'sellout';
                     }else if (this.status() == 'unavailable'){
                        return 'available';
                    }else {
                    	return 'unavailable';
                    }
                }
                
});
//根据ajax生成Map改变座位状态
$.each(seatStatusMap, function(key, value){
    sc.get(key).status(classMap[value]);
});
//console.log(seatStatusMap);//火狐控制台打印输出： Object { fileNumber="文件编号", fileName="文件名称"}
//保存演出厅座位
$("#Preservation").click(function(){
	var StatusStringNew=[]; //返回座位状态字符串
	//返回所有座位状态
	sc.find(/^[1-9]_[1-9]+/).each(function () {
		if(this.status() == 'available')
			StatusStringNew.push("1");
		else if(this.status() == 'unavailable')
			StatusStringNew.push("0");
		else
			StatusStringNew.push("-1");
	});
	var ids=StatusStringNew.join(",");
	console.log(ids);
	//向服务器提交数据（座位状态）
			$.post("${APP_PATH }/seat/savestatus",{saveStatus:ids,studio_id:studio_id},function(result){
				if(result.success){
					alert("您已成功保存");
				}else{
					alert("发生了不可预测的错误，请重试");
				}
			},"json");
});  
});
</script> 
  </head>
  <body>
<%@include file="../OrdinaryUser/nav.jsp" %>
        <div class="span9">
		<div class="row-fluid">
			<div class="page-header">
				<h1>Seat <small>座位管理</small></h1>
				<br/>
				<h2 style="margin:auto auto auto 290px" id="hname"></h2>
				<br/>
   	<div id="main">
   		<div class="demo">
       		<div id="seat-maps">
        		<div class="front">屏幕</div>                    
    		</div>
    		<div class="booking-details">
                      
        		<button class="btn btn-primary "  id="Preservation">保存</button>
                      
        		<div id="legend"></div>
    		</div>
		</div>
 </div>
 </div>
 </div>
 </div>
  </body>
</html>