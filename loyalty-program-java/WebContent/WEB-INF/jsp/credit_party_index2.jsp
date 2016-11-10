<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>授信查询页</title>
    <script src="${pageContext.request.contextPath}/static/js/jquery-1.11.1.min.js"></script>
    <script language="JavaScript" src="${pageContext.request.contextPath}/static/js/mydate.js"></script>
    <link href="${pageContext.request.contextPath}/static/css/style.css" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        .pageDetail {
            display: none;
        }
        .show {
            display: table-row;
        }
    </style>
    <script>
        $(function () {
            $('#list').click(function () {
                $('.pageDetail').toggleClass('show');
            });
        });
    </script>
</head>
<body>
<div class="wrapper">
    <div class="middle">
        <h1 style="padding: 50px 0 20px;">当前用户【${user.userName}】</h1>
		<h3 style="padding: 0 0 10px 0"><a href="${pageContext.request.contextPath}/">返回首页</a></h3>
        <form action="${pageContext.request.contextPath}/creditParty/goCreditQueryPage" method="get">
        <input type="hidden" name="rollOutAccount" value="${user.accountId}"/>
            <table class="gridtable" style="width:100%;">
                <tr>
                    <th>商户ID：</th>
                    <td><input type="text" name="rollInAccount" value="${queryParam.rollInAccount}"/></td>
                    <th>商户名称：</th>
                    <td><input type="text" name="rollInAccountName" value="${queryParam.rollInAccountName}"/></td>
                    <td rowspan="3"><input type="submit" value="查询"/></td>
                </tr>
                <tr>
                    <th>起始日期：</th>
                    <td><input type="text" id="startTime" name="startTime" onfocus="MyCalendar.SetDate(this)" value="${queryParam.startTime}"/></td>
                    <th>截止日期：</th>
                    <td><input type="text" id="endTime" name="endTime" onfocus="MyCalendar.SetDate(this)" value="${queryParam.endTime}"/></td>
                </tr>
                <tr>
                    <th>页码：</th>
                    <td><input type="text" name="page" value="${page}"/></td>
                    <th>页面大小：</th>
                    <td><input type="text" name="rows" value="${rows}"/></td>
                </tr>
            </table>
        </form>
        <c:if test="${page!=null}">
            <table class="gridtable" style="width:100%;">
                <thead>
	                <tr>
	                    <th>商户ID</th>
	                    <th>商户名称</th>
	                    <th>积分数量</th>
	                    <th>已承兑积分</th>
	                    <th>授信日期</th>
	                </tr>
                </thead>
                <tbody>
                <c:forEach items="${pageInfo.list}" var="vo">
                    <tr>
                        <td>${vo.rollInAccount}</td>
                        <td>${vo.rollInAccountName}</td>
                        <td>${vo.transAmount}</td>
                        <td>
                        	<c:if test="${vo.acceptedPointsNum == 0}">
                        		${vo.acceptedPointsNum}
                        	</c:if>
                        	<c:if test="${vo.acceptedPointsNum != 0}">
                        		<a href="${pageContext.request.contextPath}/creditParty/goAcceptQueryPage?rollInAccount=${vo.rollOutAccount}&merchant=${vo.merchant}&creditParty=${vo.creditParty}&creditCreateTime=${vo.creditCreateTime}">${vo.acceptedPointsNum}</a>
                        	</c:if>
                        </td>
                        <td><fmt:formatDate value="${vo.createTime}" pattern="yyyy年MM月dd日HH点mm分ss秒"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <table class="gridtable" style="width:100%;text-align: center;">
                <tr>
                    <c:if test="${pageInfo.hasPreviousPage}">
                        <td>
                            <a href="${pageContext.request.contextPath}/creditParty/goCreditQueryPage?page=${pageInfo.prePage}&rows=${pageInfo.pageSize}&rollInAccount=${queryParam.rollInAccount}&rollInAccountName=${queryParam.rollInAccountName}&startTime=${queryParam.startTime}&endTime=${queryParam.endTime}">前一页</a>
                        </td>
                    </c:if>
                    <c:forEach items="${pageInfo.navigatepageNums}" var="nav">
                        <c:if test="${nav == pageInfo.pageNum}">
                            <td style="font-weight: bold;">${nav}</td>
                        </c:if>
                        <c:if test="${nav != pageInfo.pageNum}">
                            <td>
                                <a href="${pageContext.request.contextPath}/creditParty/goCreditQueryPage?page=${nav}&rows=${pageInfo.pageSize}&rollInAccount=${queryParam.rollInAccount}&rollInAccountName=${queryParam.rollInAccountName}&startTime=${queryParam.startTime}&endTime=${queryParam.endTime}">${nav}</a>
                            </td>
                        </c:if>
                    </c:forEach>
                    <c:if test="${pageInfo.hasNextPage}">
                        <td>
                            <a href="${pageContext.request.contextPath}/creditParty/goCreditQueryPage?page=${pageInfo.nextPage}&rows=${pageInfo.pageSize}&rollInAccount=${queryParam.rollInAccount}&rollInAccountName=${queryParam.rollInAccountName}&startTime=${queryParam.startTime}&endTime=${queryParam.endTime}">下一页</a>
                        </td>
                    </c:if>
                </tr>
            </table>
        </c:if>
    </div>
    <div class="push"></div>
</div>
</body>
</html>