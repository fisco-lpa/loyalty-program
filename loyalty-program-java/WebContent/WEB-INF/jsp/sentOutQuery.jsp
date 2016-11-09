<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<title>Demo - 授信查询</title>
<link href="${pageContext.request.contextPath}/static/css/sui.min.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/sui.min.js"></script>
</head>
<script type="text/javascript">
	function jump(url) {
		$.ajax({
			type : 'POST',
			url : url + '.action',
			dataType : 'json',
			success : function(data) {
				$("#cx tr:not(:first)").remove();
				for (var i = 0; i < data.length; i++) {
      			  var details = "<tr><td style='width: 50px; text-align: center;'>";
      			  details+=i+"</td><td style='width: 50px; text-align: center;'>";
      			  details+=data[i].rollInAccount+"</td><td style='width: 80px; text-align: center;'>";
      			  details+=data[i].transAmount+"</td><td style='width: 50px; text-align: center;'>";
				  details+=data[i].createTime+"</td><td style='width: 80px; text-align: center;'>";
				  details+=data[i].expireTime+"</td><td style='width: 80px; text-align: center;'>";
      			  $("#cx").append(details);
				}
			},
			error : function(data) {
				alert("error")
			}
		});
	}
</script>

<body>
<h2>我的账户</h2>
<div style="float:left;width: 180px;">
	<h3>我的账户</h3>
		<table>
			<tr>
				<td>图片:</td>
			</tr>
			<tr>
				<td>ID:<span id="dff">${id}</span></td>
			</tr>
			<tr>
				<td>名称:<span id="dff">${name}</span></td>
			</tr>
			<tr>
				<td>待发放积分:<span id="dff">${dff}</span></td>
			</tr>
			<tr>
				<td>已发放积分:<span id="yff">${yff}</span></td>
			</tr>
			<tr>
				<td>已兑换积分:<span id="ycd">${ycd}</span></td>
			</tr>
		</table>
</div>

<div style="float:left;">
<h2>查询条件</h2>
<table>
	<tr>
		<td>
		<label>用户手机号</label>
		</td>
		<td>
		<input type="text"/>
		</td>
	</tr>
	<tr>
		<td>
		</td>
		<td>
		<input type="button" value="查询"  onclick="jump('querySentOutQueryList')"/>
		</td>
	</tr>
</table>

<table id="cx">
<tr>
	<td style="width: 50px; text-align: center;">序号</td>
	<td style="width: 80px; text-align: center;">用户手机号</td>
	<td style="width: 50px; text-align: center;">积分</td>
	<td style="width: 80px; text-align: center;">发放日期</td>
	<td style="width: 80px; text-align: center;">到期时间</td>
</tr>
</table>

</div>

</body>
</html>