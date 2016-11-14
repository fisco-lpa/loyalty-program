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
<title>Demo - 登录</title>
<link href="${pageContext.request.contextPath}/static/css/sui.min.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/sui.min.js"></script>
</head>
<body>
<%@ include file="include/top.jsp" %>
<div style="position:absolute; top:50%; left:50%; width:480px; height:240px; margin-left:-240px; margin-top:-120px; border: 1px solid #333333;">
	<div style="margin-top:70px;margin-left:60px;">
		<h3 style="padding: 0 0 10px 0"><a href="${pageContext.request.contextPath}/">返回首页</a></h3>
		<p>${msg}</p>
	</div>
</div>
</body>
</html>