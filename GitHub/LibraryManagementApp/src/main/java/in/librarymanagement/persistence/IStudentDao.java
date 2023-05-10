package in.librarymanagement.persistence;

import java.sql.ResultSet;

import in.librarymanagement.dto.Student;

public interface IStudentDao {
	
	public Student studentLogin(Student student);
	public Integer studentRegistarion(Student student);
	public ResultSet searchBook();	
	public ResultSet searchBookByValue(String bookDetails, String searchType);	
	public ResultSet getBookTransactions(Integer sId, StudentBook value);	
}

