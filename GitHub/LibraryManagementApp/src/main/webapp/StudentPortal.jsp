<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix='c'%>
<html lang="en">
<c:if test='${studentId eq null}'>
	<% response.sendRedirect(request.getContextPath()); %>
</c:if>
<head>
    <title>Student Dashboard</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/nav.css">
</head>

<body style="margin: 0%;">
    <div class="topnav">
        <a>Student Dashboard</a>
        <h1><a id="homepage" href="${pageContext.request.contextPath}">LIBRARY MANAGEMENT SYSTEM</a></h1>
        <h1 style="display: inline;">Student Id: ${studentId}</h1>
        <a id="logout" href="${pageContext.request.contextPath}/student/logout">LogOut</a>
      </div>
      <div class="sidenav">
        <h2 style="margin-left: 10px;">Welcome ${studentName}</h2>
        <a href="${pageContext.request.contextPath}/student/showBook">Search Book</a>
        <a href="${pageContext.request.contextPath}/student/showIssuedBook">Issued Books</a>
        <a href="${pageContext.request.contextPath}/student/showReturnedBook">Returned Books</a>
    </div>
</body>

</html>