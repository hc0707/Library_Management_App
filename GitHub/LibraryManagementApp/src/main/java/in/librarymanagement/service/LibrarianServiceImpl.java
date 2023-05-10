package in.librarymanagement.service;

import java.sql.Date;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import in.librarymanagement.daofactory.LibrarianDaoFactory;
import in.librarymanagement.dto.Book;
import in.librarymanagement.dto.BookTransaction;
import in.librarymanagement.dto.Librarian;
import in.librarymanagement.dto.Student;
import in.librarymanagement.persistence.ILibrarianDao;
import in.librarymanagement.persistence.UpdateDB;

public class LibrarianServiceImpl implements ILibrarianService {

	private ILibrarianDao librarianDao;

	@Override
	public Librarian librarianLogin(Librarian librarian) {
		Librarian lib = null;
		librarianDao = LibrarianDaoFactory.getLibrarainDao();
		if (librarianDao != null) {
			lib = librarianDao.librarianLogin(librarian);
		}
		if (lib.getlId() != null) {
			return lib;
		} else {
			return null;
		}
	}

	@Override
	public String librarianRegistarion(Librarian librarian) {

		Integer lId = null;
		librarianDao = LibrarianDaoFactory.getLibrarainDao();
		if (librarianDao != null) {
			lId = librarianDao.librarianRegistarion(librarian);
		}
		if (lId != null) {
			return Integer.toString(lId);
		} else {
			return null;
		}
	}

	@Override
	public String addBook(Book book) {
		String msg = null;
		librarianDao = LibrarianDaoFactory.getLibrarainDao();
		if (librarianDao != null) {
			msg = librarianDao.addBook(book);
		}
		if (msg != null) {
			return "Book Added Successfully";
		}
		return "Failed To Add Book";
	}

	@Override
	public List<Book> searchBook() {
		librarianDao = LibrarianDaoFactory.getLibrarainDao();
		ResultSet resultSet = librarianDao.searchBook();
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
		librarianDao = LibrarianDaoFactory.getLibrarainDao();
		ResultSet resultSet = librarianDao.searchBookByValue(bookDetails, searchType);
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
	public String updateBook(Book book) {
		librarianDao = LibrarianDaoFactory.getLibrarainDao();
		Integer count = null;
		if (librarianDao != null) {
			count = librarianDao.updateBook(book);
		}
		if (count != null)
			return "Book(" + book.getbId() + ") Updated Successfully";

		return "Book Updation Failed";
	}

	@Override
	public String deleteBook(Integer bId) {
		librarianDao = LibrarianDaoFactory.getLibrarainDao();
		Integer count = null;
		if (librarianDao != null) {
			count = librarianDao.deleteBook(bId);
		}
		if (count != null)
			return "Book(" + bId + ") Deleted Successfully";

		return "Book Deletion Failed";
	}

	@Override
	public String issueBook(Integer bId, Integer sId) {
		librarianDao = LibrarianDaoFactory.getLibrarainDao();
		if (librarianDao.isStudent(sId)) {
			if (librarianDao.isBook(bId)) {
				if (librarianDao.isStudentElegible(sId)) {
					if (librarianDao.isBookElegible(bId)) {
						if (librarianDao.issueBook(bId, sId) == 1
								&& librarianDao.updateBookCount(bId, UpdateDB.DECREMENT) == 1
								&& librarianDao.updateStudentLimit(sId, UpdateDB.INCREMENT) == 1)
							return "Book(id:" + bId + ") succesfully issued to Student(id:" + sId + ")";
					} else
						return "Boook with id: " + bId + " is currently unavailable";
				} else
					return "Cannot Issue book! Student with id: " + sId + " has already 3 books issued";
			} else
				return "No book found with id: " + bId;
		} else
			return "Student with id: " + sId + " is not registered";
		return "Some error Occured";
	}

	@Override
	public List<Student> getAllStudentDetails() {
		librarianDao = LibrarianDaoFactory.getLibrarainDao();
		ResultSet resultSet = librarianDao.getAllStudentDetails();
		ArrayList<Student> list = new ArrayList<Student>();
		try {
			while (resultSet.next()) {
				Student student = new Student();
				student.setsId(resultSet.getInt(1));
				student.setsName(resultSet.getString(2));
				student.setsEmail(resultSet.getString(3));
				student.setsContact(resultSet.getLong(4));
				student.setBookLimit(resultSet.getInt(5));
				list.add(student);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<BookTransaction> getBookTransactions() {
		librarianDao = LibrarianDaoFactory.getLibrarainDao();
		ResultSet resultSet = librarianDao.getBookTransactions();
		ArrayList<BookTransaction> list = new ArrayList<BookTransaction>();
		try {
			LocalDateTime presentDate = LocalDateTime.now();
			while (resultSet.next()) {
				Float fine = 0f;
				BookTransaction transaction = new BookTransaction();
				transaction.setStudentId(resultSet.getInt(1));
				transaction.setBookId(resultSet.getInt(2));
				Date date = resultSet.getDate(3);
				transaction.setIssueDate(date);
				transaction.setReturnDate(resultSet.getDate(4));
				transaction.setStatus(resultSet.getString(5));

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

	@Override
	public List<BookTransaction> getStudentTransactions(Integer sId) {
		librarianDao = LibrarianDaoFactory.getLibrarainDao();
		ResultSet resultSet = librarianDao.getStudentTransactions(sId);
		ArrayList<BookTransaction> list = new ArrayList<BookTransaction>();
		try {
			LocalDateTime presentDate = LocalDateTime.now();
			while (resultSet.next()) {
				Float fine = 0f;
				BookTransaction transaction = new BookTransaction();
				transaction.setId(resultSet.getInt(1));
				transaction.setStudentId(resultSet.getInt(2));
				transaction.setBookId(resultSet.getInt(3));
				Date date = resultSet.getDate(4);
				transaction.setIssueDate(date);
				transaction.setReturnDate(resultSet.getDate(5));
				transaction.setStatus(resultSet.getString(6));

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

	@Override
	public String returnBook(Integer id, Integer bId, Integer sId) {
		librarianDao = LibrarianDaoFactory.getLibrarainDao();
		if (librarianDao != null) {
			if (librarianDao.updateStudentTransaction(id) == 1
					&& librarianDao.updateBookCount(bId, UpdateDB.INCREMENT) == 1
					&& librarianDao.updateStudentLimit(sId, UpdateDB.DECREMENT) == 1)
				return "Book(" + bId + ") Returned Successfully by Student(" + sId + ")";
		}

		return "Some error Occured";
	}
}
