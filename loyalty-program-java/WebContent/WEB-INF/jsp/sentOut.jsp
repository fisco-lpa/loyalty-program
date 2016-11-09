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
<title>Demo - 发放积分</title>
<link href="${pageContext.request.contextPath}/static/css/sui.min.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/sui.min.js"></script>
</head>

<body>
<form class="sui-form form-horizontal sui-validate" action="<%=path%>/merchant/sevePoints"  method="post">

<div style="float:left;width: 580px;">
	<table>
	<tr>
		<td>用户手机号:</td>
		<td><input type="text" name="rollInAccount" />   </td>
	</tr>
	<tr>
		<td>发放积分数量:</td>
		<td><input type="text" name="transAmount">   </td>
	</tr>
	<tr>
		<td></td>
		<td><input type="submit" value="确认" />   </td>
	</tr>
</table>
</div>
</form>
</body>
</html>