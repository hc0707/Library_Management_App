package in.librarymanagement.persistence;

import java.sql.ResultSet;

import in.librarymanagement.dto.Book;
import in.librarymanagement.dto.Librarian;

public interface ILibrarianDao {
	
	public Librarian librarianLogin(Librarian librarian);
	public Integer librarianRegistarion(Librarian librarian);
	public String addBook(Book book);
	public ResultSet searchBook();	
	public ResultSet searchBookByValue(String bookDetails, String searchType);	
	public Integer updateBook(Book book);
	public Integer deleteBook(Integer bId);
	public Integer issueBook(Integer bId, Integer sId);
	public Integer updateBookCount(Integer bId,  UpdateDB value);
	public Integer updateStudentLimit(Integer sId, UpdateDB value);
	public ResultSet getAllStudentDetails();	
	public ResultSet getBookTransactions();	
	public ResultSet getStudentTransactions(Integer sId);	
	public Integer updateStudentTransaction(Integer id);
	public Boolean isStudent(Integer sId);	
	public Boolean isBook(Integer bId);	
	public Boolean isStudentElegible(Integer sId);	
	public Boolean isBookElegible(Integer bId);	
}

