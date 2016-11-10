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
<title>Demo - 首页</title>
<link href="<%=path %>/static/css/sui.min.css" rel="stylesheet">
<script type="text/javascript" src="href="<%=path %>/static/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="href="<%=path %>/static/js/sui.min.js"></script>
</head>
<body>

<%@ include file="include/top.jsp" %>

</body>
</html>