package in.librarymanagement.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.librarymanagement.dto.Book;
import in.librarymanagement.dto.BookTransaction;
import in.librarymanagement.dto.Librarian;
import in.librarymanagement.dto.Student;
import in.librarymanagement.service.ILibrarianService;
import in.librarymanagement.servicefactory.LibrarianServiceFactory;

@WebServlet(urlPatterns = "/librarian/*")
public class LibrarianController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static ILibrarianService librarianService;
	private HttpSession session;
	RequestDispatcher requestDispatcher;
	private Librarian lib = null;
	private String registrationStatus = null;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getRequestURI().endsWith("login")) {
			Librarian librarian = new Librarian();
			librarian.setlId(Integer.parseInt(request.getParameter("lid")));
			librarian.setlPassword(request.getParameter("lpassword"));
			librarianService = LibrarianServiceFactory.getLibrarianService();
			if (librarianService != null) {
				lib = librarianService.librarianLogin(librarian);
			}
			if (lib != null) {
				HttpSession session = request.getSession();
				session.setAttribute("librarianId", lib.getlId());
				session.setAttribute("librarianName", lib.getlName());
				requestDispatcher = request.getRequestDispatcher("/LibrarianPortal.jsp");
			} else {
				request.setAttribute("status", "Invalid Login Details");
				requestDispatcher = request.getRequestDispatcher("/LibrarianLogin.jsp");
			}
			try {
				requestDispatcher.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (request.getRequestURI().endsWith("register")) {
			Librarian librarian = new Librarian();
			librarian.setlName(request.getParameter("lname"));
			librarian.setlEmail(request.getParameter("lemail"));
			librarian.setlContact(Long.parseLong(request.getParameter("lcontact")));
			librarian.setlPassword(request.getParameter("lpassword"));
			librarianService = LibrarianServiceFactory.getLibrarianService();
			if (librarianService != null) {
				registrationStatus = librarianService.librarianRegistarion(librarian);
			}
			requestDispatcher = request.getRequestDispatcher("/LibrarianReg.jsp");
			if (registrationStatus != null) {
				request.setAttribute("status",
						"Account Registered Successfully! Your Librarian Id is:: " + registrationStatus);
				request.setAttribute("color", "green");
			} else {
				request.setAttribute("color", "crimson");
				request.setAttribute("status", "Registration Unsucessfull");
			}
			try {
				requestDispatcher.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (request.getRequestURI().endsWith("addBook")) {
			Book book = new Book();
			book.setbTitle(request.getParameter("btitle"));
			book.setbAuthor(request.getParameter("bauthor"));
			book.setbCategory(request.getParameter("bcategory"));
			book.setbCount(Integer.parseInt(request.getParameter("bcount")));
			librarianService = LibrarianServiceFactory.getLibrarianService();
			String msg = librarianService.addBook(book);
			requestDispatcher = request.getRequestDispatcher("/displayMessage.jsp");
			request.setAttribute("message", msg);
			try {
				requestDispatcher.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (request.getRequestURI().endsWith("updateBook")) {
			Book book = new Book();
			book.setbId(Integer.parseInt(request.getParameter("bid")));
			book.setbTitle(request.getParameter("btitle"));
			book.setbAuthor(request.getParameter("bauthor"));
			book.setbCategory(request.getParameter("bcategory"));
			book.setbCount(Integer.parseInt(request.getParameter("bcount")));
			librarianService = LibrarianServiceFactory.getLibrarianService();
			String bookUpdateMessage = librarianService.updateBook(book);
			requestDispatcher = request.getRequestDispatcher("/displayMessage.jsp");
			request.setAttribute("message", bookUpdateMessage);
			try {
				requestDispatcher.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (request.getRequestURI().endsWith("issueBook")) {
			String bId = request.getParameter("bid");
			String sId = request.getParameter("sid");
			librarianService = LibrarianServiceFactory.getLibrarianService();
			String status = librarianService.issueBook(Integer.parseInt(bId), Integer.parseInt(sId));
			requestDispatcher = request.getRequestDispatcher("/displayMessage.jsp");
			request.setAttribute("message", status);
			try {
				requestDispatcher.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response){
		
		if (request.getRequestURI().endsWith("showBook")) {
			librarianService = LibrarianServiceFactory.getLibrarianService();
			List<Book> list = librarianService.searchBook();
			requestDispatcher = request.getRequestDispatcher("/searchBook.jsp");
			request.setAttribute("bookList", list);
			try {
				requestDispatcher.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (request.getRequestURI().endsWith("searchBook")) {
			librarianService = LibrarianServiceFactory.getLibrarianService();
			String bookDetails = request.getParameter("bookDetails");
			String searchType = request.getParameter("type");
			List<Book> list = librarianService.searchBookByValue(bookDetails,searchType);
			requestDispatcher = request.getRequestDispatcher("/searchBook.jsp");
			request.setAttribute("bookList", list);
			try {
				requestDispatcher.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (request.getRequestURI().endsWith("getAllStudentDetails")) {
			librarianService = LibrarianServiceFactory.getLibrarianService();
			List<Student> list = librarianService.getAllStudentDetails();
			requestDispatcher = request.getRequestDispatcher("/studentData.jsp");
			request.setAttribute("studentList", list);
			try {
				requestDispatcher.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (request.getRequestURI().endsWith("getBookTransactions")) {
			librarianService = LibrarianServiceFactory.getLibrarianService();
			List<BookTransaction> list = librarianService.getBookTransactions();
			requestDispatcher = request.getRequestDispatcher("/bookTransaction.jsp");
			request.setAttribute("transactionList", list);
			try {
				requestDispatcher.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (request.getRequestURI().endsWith("getStudentTransactions")) {
			int sId = Integer.parseInt(request.getParameter("sId"));
			librarianService = LibrarianServiceFactory.getLibrarianService();
			List<BookTransaction> list = librarianService.getStudentTransactions(sId);
			requestDispatcher = request.getRequestDispatcher("/returnBook.jsp");
			request.setAttribute("studentTransactions", list);
			try {
				requestDispatcher.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (request.getRequestURI().endsWith("bookUpdateForm")) {
			librarianService = LibrarianServiceFactory.getLibrarianService();
			String bookId = request.getParameter("bookId");
			List<Book> list = librarianService.searchBookByValue(bookId, "id");
			Book book = list.get(0);
			requestDispatcher = request.getRequestDispatcher("/updateBook.jsp");
			request.setAttribute("book", book);
			try {
				requestDispatcher.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (request.getRequestURI().endsWith("deleteBook")) {
			librarianService = LibrarianServiceFactory.getLibrarianService();
			String bookId = request.getParameter("bookId");
			String deleteBookMessage = librarianService.deleteBook(Integer.parseInt(bookId));
			
			requestDispatcher = request.getRequestDispatcher("/displayMessage.jsp");
			request.setAttribute("message", deleteBookMessage);
			try {
				requestDispatcher.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (request.getRequestURI().endsWith("returnBook")) {
			librarianService = LibrarianServiceFactory.getLibrarianService();
			int id = Integer.parseInt(request.getParameter("id"));
			int bId = Integer.parseInt(request.getParameter("bId"));
			int sId = Integer.parseInt(request.getParameter("sId"));
			String status = librarianService.returnBook(id, bId, sId);
			requestDispatcher = request.getRequestDispatcher("/librarian/getStudentTransactions");
			request.setAttribute("message", status);
			request.setAttribute("sId", sId);
			try {
				requestDispatcher.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (request.getRequestURI().endsWith("logout")) {
			session = request.getSession();
			session.invalidate();
			try {
				response.sendRedirect(request.getContextPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
