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
    
    <div style="margin-left: 260px;
  				padding: 0px 10px;
  				margin-top:-490px;">
  				
  		  <form action='${pageContext.request.contextPath}/librarian/searchBook' method="GET">
      		<input type="text" placeholder="Search Book.." name="bookDetails" style="width:400px;" >
      		<button type="submit" name='type' value='title'>Search By Title</button>
      		<button type="submit" name='type' value='author'>Search By Author</button>
      		<button type="submit" name='type' value='category'>Search By Category</button>
    	  </form>
    	  <br/>
	      <table id="customers">
	      <tr>
	        <th>Book Id</th>
	        <th>Book Title</th>
	        <th>Book Author</th>
	        <th>Book Category</th>
	        <th>Book Qty.</th>
	        <th>Update/Delete</th>
	      </tr>
	      <c:forEach var='book' items='${bookList}'>
	      <tr>
		    <c:url var="updateLink" value="/librarian/bookUpdateForm">
				<c:param name="bookId" value="${book.bId}"/>
			</c:url>
					
		    <c:url var="deleteLink" value="/librarian/deleteBook">
				<c:param name="bookId" value="${book.bId}"/>
		    </c:url>
	        <td>${book.bId}</td>
	        <td>${book.bTitle}</td>
	        <td>${book.bAuthor}</td>
	        <td>${book.bCategory}</td>
	        <td>${book.bCount}</td>
	        <td><a href='${updateLink}'>UPDATE</a>
										|
				<a href="${deleteLink}"
				onclick="if(!(confirm('Are you sure u want to delete this book?')))return false;">
				DELETE</a></td>
	      </tr>
	      </c:forEach>
	    </table>
    </div>
    
</body>

</html>