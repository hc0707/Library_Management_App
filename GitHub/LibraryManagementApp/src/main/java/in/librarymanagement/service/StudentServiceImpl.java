package in.librarymanagement.service;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import in.librarymanagement.daofactory.StudentDaoFactory;
import in.librarymanagement.dto.Book;
import in.librarymanagement.dto.Student;
import in.librarymanagement.dto.StudentBookTransaction;
import in.librarymanagement.persistence.IStudentDao;
import in.librarymanagement.persistence.StudentBook;

public class StudentServiceImpl implements IStudentService {

	private IStudentDao studentDao;

	@Override
	public Student studentLogin(Student student) {
		Student std=null;
		studentDao=StudentDaoFactory.getStudentDao();
		if (studentDao != null) {
			std=studentDao.studentLogin(student);
		}
		if (std.getsId()!=null) {
			return std;
		} else {
			return null;
		}
	}
	
	@Override
	public String studentRegistarion(Student student) {
		Integer sId=null;
		studentDao=StudentDaoFactory.getStudentDao();
		if (studentDao != null) {
			sId=studentDao.studentRegistarion(student);
		}
		if (sId!=null) {
			return Integer.toString(sId);
		} else {
			return null;
		}
	}
	
	@Override
	public List<Book> searchBook() {
		studentDao = StudentDaoFactory.getStudentDao();
		ResultSet resultSet = studentDao.searchBook();
		ArrayList<Book> list = new ArrayList<Book>();
		try {
			while (resultSet.next()) {
				Book book = new Book();
				book.setbId(resultSet.getInt(1));
				book.setbTitle(resultSet.getString(2));
				book.setbAuthor(resultSet.getString(3));
				book.setbCategory(resultSet.getString(4));
				book.setbCount(resultSet.getInt(5));
				list.add(book);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Book> searchBookByValue(String bookDetails, String searchType) {
		studentDao = StudentDaoFactory.getStudentDao();
		ResultSet resultSet = studentDao.searchBookByValue(bookDetails, searchType);
		ArrayList<Book> list = new ArrayList<Book>();
		if (resultSet != null) {
			try {
				while (resultSet.next()) {
					Book book = new Book();
					book.setbId(resultSet.getInt(1));
					book.setbTitle(resultSet.getString(2));
					book.setbAuthor(resultSet.getString(3));
					book.setbCategory(resultSet.getString(4));
					book.setbCount(resultSet.getInt(5));
					list.add(book);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	@Override
	public List<StudentBookTransaction> getBookTransactions(Integer sId, StudentBook value) {
		studentDao=StudentDaoFactory.getStudentDao();
		ResultSet resultSet = studentDao.getBookTransactions(sId,value);
		ArrayList<StudentBookTransaction> list = new ArrayList<StudentBookTransaction>();
		try {
			LocalDateTime presentDate = LocalDateTime.now();
			while (resultSet.next()) {
				Float fine = 0f;
				StudentBookTransaction transaction = new StudentBookTransaction();
				transaction.setBookId(resultSet.getInt(1));
				transaction.setbTitle(resultSet.getString(2));
				transaction.setbAuthor(resultSet.getString(3));
				transaction.setbCategory(resultSet.getString(4));
				Date date = resultSet.getDate(5);
				transaction.setIssueDate(date);
				transaction.setReturnDate(resultSet.getDate(6));
				transaction.setStatus(resultSet.getString(7));

				LocalDateTime issueDate = date.toLocalDate().atStartOfDay();
				long days = Duration.between(issueDate, presentDate).toDays();
				if (days > 15) {
					fine = (days - 15) * 10f;
				}
				transaction.setFine(fine);
				list.add(transaction);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
