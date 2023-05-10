package in.librarymanagement.service;

import java.util.List;

import in.librarymanagement.dto.Book;
import in.librarymanagement.dto.BookTransaction;
import in.librarymanagement.dto.Librarian;
import in.librarymanagement.dto.Student;

public interface ILibrarianService {
	
	public Librarian librarianLogin(Librarian librarian);
	public String librarianRegistarion(Librarian librarian);
	public String addBook(Book book);
	public List<Book> searchBook();
	public List<Book> searchBookByValue(String bookDetails, String searchType);	
	public String updateBook(Book book);
	public String deleteBook(Integer bId);
	public String issueBook(Integer bId, Integer sId);
	public String returnBook(Integer id, Integer bId, Integer sId);
	public List<Student> getAllStudentDetails();
	public List<BookTransaction> getBookTransactions();
	public List<BookTransaction> getStudentTransactions(Integer sId);
}
