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
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/sui.min.js"></script>
</head>
<body>
<div style="position:absolute; top:30%; left:50%; width:480px; height:320px; margin-left:-240px; margin-top:-120px; border: 1px solid #333333;">
	<div style="margin-top:70px;margin-left:60px;">
	<form class="sui-form form-horizontal sui-validate" action="<%=path%>/changePwd" method="post" onsubmit="return preparesubmit()">
		<div class="control-group">
			<label for="userName" class="control-label">用户名：</label>
			<div class="controls">
				<input id="userName" name="userName" placeholder="用户名" type="text" data-rules="required|minlength=4|maxlength=256"/>
			</div>
		</div>
		<div class="control-group">
			<label for="oldpassword" class="control-label">旧密码：</label>
			<div class="controls">
				<input id="oldpassword" name="oldpassword" placeholder="旧密码" type="password" data-rules="required|minlength=4|maxlength=256"/>
			</div>
		</div>
		<div class="control-group">
			<label for="password" class="control-label">新密码：</label>
			<div class="controls">
				<input id="password" name="password" placeholder="新密码" type="password" data-rules="required|minlength=4|maxlength=256"/>
			</div>
		</div>
		<div class="control-group">
			<label for="subpassword" class="control-label">确认密码：</label>
			<div class="controls">
				<input id="subpassword" name="subpassword" placeholder="确认密码" type="password" data-rules="required|minlength=4|maxlength=256"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"></label>
			<div class="controls">
				<button type="submit" class="sui-btn btn-primary">修改密码</button>
			</div>
		</div>
		</form>
	</div>
</div>
</body>
<script type="text/javascript">
	function preparesubmit(){
		var password=$("#password").val();
		var subpassword=$("#subpassword").val();
		if(password!=subpassword){
			alert("两次输入的密码不相同，请重新输入");
			return false;
		}
		var userName=$("#userName").val();
		var oldpassword=$("#oldpassword").val();
		var flag=false;
		$.ajax({
             type: "GET",
             async: false,
             url: "${pageContext.request.contextPath}/checkPwd",
             data: {userName:userName,userPassword:oldpassword},
             dataType: "json",
             success: function(msg){
            	 if(msg=="111"){
            		 alert(11111);
            		 flag=true;
            	 }
             }
         });
		//alert(flag);
		if(flag){
			return true;
		}
		return false;
	}

</script>
</html>