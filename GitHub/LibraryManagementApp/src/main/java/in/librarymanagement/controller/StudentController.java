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
import in.librarymanagement.dto.Student;
import in.librarymanagement.dto.StudentBookTransaction;
import in.librarymanagement.persistence.StudentBook;
import in.librarymanagement.service.IStudentService;
import in.librarymanagement.servicefactory.StudentServiceFactory;

@WebServlet(urlPatterns = "/student/*")
public class StudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session;
	private static IStudentService studentService;
	RequestDispatcher requestDispatcher;
	private Student std = null;
	private String registrationStatus = null;

	public void doPost(HttpServletRequest request, HttpServletResponse response) {

		if (request.getRequestURI().endsWith("login")) {

			Student student = new Student();
			student.setsId(Integer.parseInt(request.getParameter("sid")));
			student.setsPassword(request.getParameter("spassword"));
			studentService = StudentServiceFactory.getStudentService();
			if (studentService != null) {
				std = studentService.studentLogin(student);
			}
			if (std != null) {
				HttpSession session = request.getSession();
				session.setAttribute("studentId", std.getsId());
				session.setAttribute("studentName", std.getsName());
				requestDispatcher = request.getRequestDispatcher("/StudentPortal.jsp");
			} else {
				request.setAttribute("status", "Invalid Login Details");
				requestDispatcher = request.getRequestDispatcher("/StudentLogin.jsp");
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
			Student student = new Student();
			student.setsName(request.getParameter("sname"));
			student.setsEmail(request.getParameter("semail"));
			student.setsContact(Long.parseLong(request.getParameter("scontact")));
			student.setsPassword(request.getParameter("spassword"));
			studentService = StudentServiceFactory.getStudentService();
			if (studentService != null) {
				registrationStatus = studentService.studentRegistarion(student);
			}
			requestDispatcher = request.getRequestDispatcher("/StudentReg.jsp");
			if (registrationStatus != null) {
				request.setAttribute("status",
						"Account Registered Successfully! Your Student Id is:: " + registrationStatus);
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

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		
		if (request.getRequestURI().endsWith("showBook")) {
			studentService= StudentServiceFactory.getStudentService();
			List<Book> list = studentService.searchBook();
			requestDispatcher = request.getRequestDispatcher("/StudentSearchBook.jsp");
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
			studentService= StudentServiceFactory.getStudentService();
			String bookDetails = request.getParameter("bookDetails");
			String searchType = request.getParameter("type");
			List<Book> list = studentService.searchBookByValue(bookDetails,searchType);
			requestDispatcher = request.getRequestDispatcher("/StudentSearchBook.jsp");
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
		
		if (request.getRequestURI().endsWith("showIssuedBook")) {
			studentService=StudentServiceFactory.getStudentService();
			Integer sId= (Integer) request.getSession().getAttribute("studentId");
			List<StudentBookTransaction> list = studentService.getBookTransactions(sId, StudentBook.ISSUED);
			requestDispatcher = request.getRequestDispatcher("/StudentBooks.jsp");
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
		
		if (request.getRequestURI().endsWith("showReturnedBook")) {
			studentService=StudentServiceFactory.getStudentService();
			Integer sId= (Integer) request.getSession().getAttribute("studentId");
			List<StudentBookTransaction> list = studentService.getBookTransactions(sId, StudentBook.RETURNED);
			requestDispatcher = request.getRequestDispatcher("/StudentBooks.jsp");
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
