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
<title>授信页面</title>
<link href="${pageContext.request.contextPath}/static/css/sui.min.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/sui.min.js"></script>
</head>
<body>

<div style="position:absolute; top:50%; left:50%; width:480px; height:240px; margin-left:-240px; margin-top:-120px; border: 1px solid #333333;">
	<div style="margin-top:70px;margin-left:60px;">
	<form class="sui-form form-horizontal sui-validate" action="<%=path%>/creditParty/credit" method="post">
		<div class="control-group">
			<label for="userName" class="control-label">商户名称：</label>
			<input type="hidden" name="rollOutAccount" value="${rollOutAccount}"/>
			<div class="controls">
				<select name="rollInAccount">
					<c:forEach items="${merchants}" var="merchant">
						<option value="${merchant.accountId }">${merchant.userName }</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">授予积分额度：</label>
			<div class="controls">
				<input id="transAmount" name="transAmount" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"></label>
			<div class="controls">
				<button type="submit" class="sui-btn btn-primary">确认</button>
			</div>
		</div>
	</form>
	</div>
</div>
</body>
</html>