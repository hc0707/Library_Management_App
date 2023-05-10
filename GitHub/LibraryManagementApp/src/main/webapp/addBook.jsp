<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix='c'%>
<!DOCTYPE html>
<html lang="en">

<c:if test='${librarianId eq null}'>
	<% response.sendRedirect(request.getContextPath()); %>
</c:if>
<head>
    <title>Librarian Dashboard</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/nav.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
</head>

<body>
    <div class="topnav">
        <a>Librarian Dashboard</a>
        <h1><a id="homepage" href="${pageContext.request.contextPath}">LIBRARY MANAGEMENT SYSTEM</a></h1>
        <h1 style="display: inline;">Librarian Id: ${librarianId}</h1>
        <a id="logout" href="<%=request.getContextPath()%>/librarian/logout">LogOut</a>
      </div>
      
     <div class="sidenav">
        <h3 style="margin-left: 10px;">Welcome ${librarianName}</h3>
        <a href="${pageContext.request.contextPath}/librarian/addBookForm">Add Book</a>
        <a href="${pageContext.request.contextPath}/librarian/showBook">Search Book</a>
        <a href="${pageContext.request.contextPath}/librarian/issueBookForm">Issue Book</a>
        <a href="${pageContext.request.contextPath}/librarian/returnBookForm">Return Book</a>
        <a href="${pageContext.request.contextPath}/librarian/getAllStudentDetails">Student DataBase</a>
        <a href="${pageContext.request.contextPath}/librarian/getBookTransactions">Book Transaction</a>
    </div>
    
     <div class="main">
        <div style="height: 200px; width: 30%;">
            <form action="${pageContext.request.contextPath}/librarian/addBook" method="post">
                <div class="mb-3">
                    <label for="exampleInputEmail1" class="form-label">Book Title</label>
                    <input type="text" class="form-control" id="exampleInputEmail1" name="btitle">
                </div>
                <div class="mb-3">
                    <label for="exampleInputEmail1" class="form-label">Book Author</label>
                    <input type="text" class="form-control" id="exampleInputEmail1" name="bauthor">
                </div>
                <div class="mb-3">
                    <label for="exampleInputEmail1" class="form-label">Book Category</label>
                    <input type="text" class="form-control" id="exampleInputEmail1" name="bcategory">
                </div>
                <div class="mb-3">
                    <label for="exampleInputPassword1" class="form-label">Book Qty.</label>
                    <input type="text" class="form-control" id="exampleInputPassword1" name="bcount">
                </div>
                <button type="submit" class="btn btn-primary">Add Book</button>
            </form>
        </div>
    </div>
    
</body>

</html>