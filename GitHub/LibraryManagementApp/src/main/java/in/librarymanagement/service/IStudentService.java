package in.librarymanagement.service;

import java.util.List;

import in.librarymanagement.dto.Book;
import in.librarymanagement.dto.Student;
import in.librarymanagement.dto.StudentBookTransaction;
import in.librarymanagement.persistence.StudentBook;

public interface IStudentService {
	

	public Student studentLogin(Student student);
	public String studentRegistarion(Student student);
	public List<Book> searchBook();
	public List<Book> searchBookByValue(String bookDetails, String searchType);	
	public List<StudentBookTransaction> getBookTransactions(Integer sId, StudentBook value);
}
