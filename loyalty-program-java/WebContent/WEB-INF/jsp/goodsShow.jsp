<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>

<head>

<script
	src="${pageContext.request.contextPath}/static/js/jquery-1.11.1.min.js"></script>
<link
	href="${pageContext.request.contextPath}/static/css/goodsStyle.css"
	rel="stylesheet" type="text/css" />

<link
	href="${pageContext.request.contextPath}/static/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<style>
body {
	background: #303030;
	font-family: 'Lato', sans-serif;
	font-size: 100%;
}

.c-head:hover {
	color: red;
	cursor: pointer;
}

img {
	box-shadow: -5px -5px 5px rgba(37, 97, 140, 0.4);
	border-radius: 10px;
}

.first:hover {active;
	
}
</style>

</head>
<body>
	<div class="navbar-wrapper">
		<div class="container">

			<nav class="navbar navbar-inverse navbar-static-top">
				<div class="container">
					<div class="navbar-header">
						<a class="navbar-brand" href="${pageContext.request.contextPath}">富德商城</a>
					</div>
					<div id="navbar" class="navbar-collapse collapse">
						<ul class="nav navbar-nav navbar-right"
							style="margin-right: 16px;">
							<li id="first"><a
								href="${pageContext.request.contextPath}">返回首页</a></li>
						
						</ul>
					</div>
				</div>
			</nav>


			<section id="main">
				<div class="content">


					<div class="coats">
						<h3 class="c-head" onclick="showMoreDQ(this);">热门商品</h3>
						<small><a>Show More</a></small>
						<div class="coat-row">

							<div class="coat-column">
								<a> <img
									src='${pageContext.request.contextPath}/static/images/1.jpg'
									class="img-responsive" alt="">
									<div>
										<h4>商品名称：购物卡</h4>
										<small>所需积分：200</small>
									</div>
								</a>
								<p>
									<a class="btn btn-danger" style="width: 150px"
										onclick='gotobuy("购物卡","200");' role="button">购买</a>

								</p>
							</div>
							<div class="coat-column">
								<a> <img
									src='${pageContext.request.contextPath}/static/images/1.jpg'
									class="img-responsive" alt="">
									<div>
										<h4>商品名称：优惠卷</h4>
										<small>所需积分：140</small>
									</div>
								</a>
								<p>
									<a class="btn btn-danger" style="width: 150px"
										onclick='gotobuy("优惠卷","140");' role="button">购买</a>

								</p>
							</div>

							<div class="clearfix"></div>
						</div>
					</div>

				</div>
			</section>
		</div>
	</div>


	<script>
		function gotobuy(style, number) {
		//	alert(number);
			console.log(style);
			$.ajax({
				type: "GET",
	            async: false,
				url :"${pageContext.request.contextPath}/buyGoods",
				data : {goodsName : style,pointsNum : number,accountMallId:'2'},
				dataType : "json",
				success : function(msg) {
					console.log(msg);
					if(msg=='1111')
					alert("购买成功！！！");
					else
						alert("余额不够！！！");
				}
			});
		}
	</script>
</body>

</html>
