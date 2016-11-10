<%@ page language="java" pageEncoding="UTF-8"%>

<div class="sui-navbar">
	<div class="navbar-inner"><a href="#" class="sui-brand">Fisco-lpa</a>
		<ul class="sui-nav">
		  	<li class="active"><a href="<%=path%>/">首页</a></li>
		  	<c:forEach items="${menus}" var="menu">
			<li><a href="<%=path%>/${menu.value}">${menu.name }</a></li>
			</c:forEach>
		</ul>
		<ul class="sui-nav pull-right">
			<li><a href="#">当前用户：${user.userName}(${user.userTypeStr})</a></li>
			<li><a href="<%=path%>/demo">样例</a></li>
	      	<li><a href="<%=path%>/toChangePwd">修改密码</a></li>
	      	<li><a href="<%=path%>/logout">退出</a></li>
	    </ul>
	</div>
</div>