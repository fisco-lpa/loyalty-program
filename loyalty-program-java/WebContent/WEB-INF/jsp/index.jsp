<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<title>首页</title>
<link href="${pageContext.request.contextPath}/static/css/sui.min.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/sui.min.js"></script>
</head>
<body>

<div class="sui-navbar">
	<div class="navbar-inner"><a href="#" class="sui-brand">Fisco-lpa</a>
		<ul class="sui-nav">
		  	<li class="active"><a href="<%=path%>/">首页</a></li>
		  	<c:forEach items="${menus}" var="menu">
			<li><a href="<%=path%>/${menu.value}">${menu.name }</a></li>
			</c:forEach>
		</ul>
		<ul class="sui-nav pull-right">
	      	<li><a href="<%=path%>/toChangePwd">修改密码</a></li>
	    </ul>
	</div>
</div>

</body>
</html>