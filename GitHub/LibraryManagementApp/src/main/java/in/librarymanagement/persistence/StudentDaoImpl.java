package in.librarymanagement.persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import in.librarymanagement.dto.Student;
import in.librarymanagement.jdbcutil.JdbcUtil;

public class StudentDaoImpl implements IStudentDao {

	private Connection connection;
	private PreparedStatement pstmt;

	@Override
	public Student studentLogin(Student student) {
		Student std = new Student();
		String sqlQuery = "select * from student where sid=? and spassword=?";
		try {
			connection = JdbcUtil.getJdbcConnection();
			if (connection != null) {
				pstmt = connection.prepareStatement(sqlQuery);
				pstmt.setInt(1, student.getsId());
				pstmt.setString(2, student.getsPassword());
			}
			if (pstmt != null) {
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					std.setsId(rs.getInt(1));
					std.setsName(rs.getString(2));
					std.setsEmail(rs.getString(3));
					std.setsContact(rs.getLong(4));
				}
			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return std;
	}

	@Override
	public Integer studentRegistarion(Student student) {
		Integer sId = null;
		String sqlQuery = "insert into student (sname,semail,scontact,spassword) values (?,?,?,?)";
		try {
			connection = JdbcUtil.getJdbcConnection();
			if (connection != null) {
				pstmt = connection.prepareStatement(sqlQuery);
			}
			if (pstmt != null) {
				pstmt.setString(1, student.getsName());
				pstmt.setString(2, student.getsEmail());
				pstmt.setLong(3, student.getsContact());
				pstmt.setString(4, student.getsPassword());
				int rowAffected = pstmt.executeUpdate();
				if (rowAffected == 1) {
					sId = getStudentId(student.getsEmail(), student.getsPassword());
				}
			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sId;
	}

	public Integer getStudentId(String sEmail, String sPassword) {
		Integer sId = null;
		String sqlQuery = "select sid from student where semail=? and spassword=?";
		try {
			connection = JdbcUtil.getJdbcConnection();
			if (connection != null) {
				pstmt = connection.prepareStatement(sqlQuery);
				pstmt.setString(1, sEmail);
				pstmt.setString(2, sPassword);
			}
			if (pstmt != null) {
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					sId = rs.getInt(1);
				}
			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sId;
	}
	
	@Override
	public ResultSet searchBook() {
		String sqlQuery = "select bid,btitle,bauthor,bcategory,bcount from book ORDER BY bcount";
		ResultSet resultSet = null;
		try {
			connection = JdbcUtil.getJdbcConnection();
			if (connection != null) {
				pstmt = connection.prepareStatement(sqlQuery);
			}
			if (pstmt != null) {
				resultSet = pstmt.executeQuery();
			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultSet;
	}

	@Override
	public ResultSet searchBookByValue(String bookDetails, String searchType) {
		String sqlQuery;
		if (searchType.equals("title"))
			sqlQuery = "select bid,btitle,bauthor,bcategory,bcount from book where btitle='" + bookDetails + "'";

		else if (searchType.equals("author"))
			sqlQuery = "select bid,btitle,bauthor,bcategory,bcount from book where bauthor='" + bookDetails + "'";

		else if (searchType.equals("category"))
			sqlQuery = "select bid,btitle,bauthor,bcategory,bcount from book where bcategory='" + bookDetails + "'";

		else if (searchType.equals("id"))
			sqlQuery = "select bid,btitle,bauthor,bcategory,bcount from book where bid='" + bookDetails + "'";

		else
			return null;

		ResultSet resultSet = null;
		try {
			connection = JdbcUtil.getJdbcConnection();
			if (connection != null) {
				pstmt = connection.prepareStatement(sqlQuery);
			}
			if (pstmt != null) {
				resultSet = pstmt.executeQuery();
			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultSet;
	}

	@Override
	public ResultSet getBookTransactions(Integer sId, StudentBook value) {
		String sqlQuery = "SELECT bookid,btitle,bauthor,bcategory,issuedate,returndate,status FROM booktransaction INNER JOIN book ON booktransaction.bookid=book.bid WHERE status=? AND studentid=? ORDER BY issuedate DESC";
		ResultSet resultSet = null;
		try {
			connection = JdbcUtil.getJdbcConnection();
			if (connection != null) {
				pstmt = connection.prepareStatement(sqlQuery);
				if (value==StudentBook.ISSUED) 
					pstmt.setString(1, "Issued");
				if (value==StudentBook.RETURNED) 
					pstmt.setString(1, "Returned");
				pstmt.setInt(2, sId);
			}
			if (pstmt != null) {
				resultSet = pstmt.executeQuery();
			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultSet;
	}

}
