package in.librarymanagement.persistence;

import java.io.IOException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import in.librarymanagement.dto.Book;
import in.librarymanagement.dto.Librarian;
import in.librarymanagement.jdbcutil.JdbcUtil;

public class LibrarianDaoImpl implements ILibrarianDao {

	private Connection connection;
	private PreparedStatement pstmt;

	@Override
	public Librarian librarianLogin(Librarian librarian) {
		Librarian lib = new Librarian();
		String sqlQuery = "select * from librarian where lid=? and lpassword=?";
		try {
			connection = JdbcUtil.getJdbcConnection();
			if (connection != null) {
				pstmt = connection.prepareStatement(sqlQuery);
				pstmt.setInt(1, librarian.getlId());
				pstmt.setString(2, librarian.getlPassword());
			}
			if (pstmt != null) {
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					lib.setlId(rs.getInt(1));
					lib.setlName(rs.getString(2));
					lib.setlEmail(rs.getString(3));
					lib.setlContact(rs.getLong(4));
				}
			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lib;
	}

	@Override
	public Integer librarianRegistarion(Librarian librarian) {
		Integer lId = null;
		String sqlQuery = "insert into librarian (lname,lemail,lcontact,lpassword) values (?,?,?,?)";
		try {
			connection = JdbcUtil.getJdbcConnection();
			if (connection != null) {
				pstmt = connection.prepareStatement(sqlQuery);
			}
			if (pstmt != null) {
				pstmt.setString(1, librarian.getlName());
				pstmt.setString(2, librarian.getlEmail());
				pstmt.setLong(3, librarian.getlContact());
				pstmt.setString(4, librarian.getlPassword());
				int rowAffected = pstmt.executeUpdate();
				if (rowAffected == 1) {
					lId = getLibrarianId(librarian.getlEmail(), librarian.getlPassword());
				}
			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lId;
	}

	public Integer getLibrarianId(String lEmail, String lPassword) {
		Integer lId = null;
		String sqlQuery = "select lid from librarian where lemail=? and lpassword=?";
		try {
			connection = JdbcUtil.getJdbcConnection();
			if (connection != null) {
				pstmt = connection.prepareStatement(sqlQuery);
				pstmt.setString(1, lEmail);
				pstmt.setString(2, lPassword);
			}
			if (pstmt != null) {
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					lId = rs.getInt(1);
				}
			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lId;
	}

	@Override
	public String addBook(Book book) {
		String sqlQuery = "insert into book (btitle,bauthor,bcategory,bcount) values (?,?,?,?)";
		try {
			connection = JdbcUtil.getJdbcConnection();
			if (connection != null) {
				pstmt = connection.prepareStatement(sqlQuery);
			}
			if (pstmt != null) {
				pstmt.setString(1, book.getbTitle());
				pstmt.setString(2, book.getbAuthor());
				pstmt.setString(3, book.getbCategory());
				pstmt.setInt(4, book.getbCount());
				int rowAffected = pstmt.executeUpdate();
				if (rowAffected == 1) {
					return "success";
				}
			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
	public Integer updateBook(Book book) {
		String sqlQuery = "update book set btitle=?,bauthor=?,bcategory=?,bcount=? where bid=?";
		Integer rowAffected = null;
		try {
			connection = JdbcUtil.getJdbcConnection();
			if (connection != null) {
				pstmt = connection.prepareStatement(sqlQuery);
				pstmt.setString(1, book.getbTitle());
				pstmt.setString(2, book.getbAuthor());
				pstmt.setString(3, book.getbCategory());
				pstmt.setInt(4, book.getbCount());
				pstmt.setInt(5, book.getbId());
			}
			if (pstmt != null) {
				rowAffected = pstmt.executeUpdate();
			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rowAffected;
	}

	@Override
	public Integer deleteBook(Integer bId) {
		String sqlQuery = "delete from book where bid=?";
		Integer rowAffected = null;
		try {
			connection = JdbcUtil.getJdbcConnection();
			if (connection != null) {
				pstmt = connection.prepareStatement(sqlQuery);
				pstmt.setInt(1, bId);
			}
			if (pstmt != null) {
				rowAffected = pstmt.executeUpdate();
			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rowAffected;
	}

	@Override
	public Boolean isStudent(Integer sId) {
		String sqlQuery = "select sname from student where sid=?";
		try {
			connection = JdbcUtil.getJdbcConnection();
			if (connection != null) {
				pstmt = connection.prepareStatement(sqlQuery);
				pstmt.setInt(1, sId);
			}
			if (pstmt != null) {
				ResultSet rs = pstmt.executeQuery();
				return rs.next();
			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean isBook(Integer bId) {
		String sqlQuery = "select btitle from book where bid=?";
		try {
			connection = JdbcUtil.getJdbcConnection();
			if (connection != null) {
				pstmt = connection.prepareStatement(sqlQuery);
				pstmt.setInt(1, bId);
			}
			if (pstmt != null) {
				ResultSet rs = pstmt.executeQuery();
				return rs.next();
			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean isStudentElegible(Integer sId) {
		String sqlQuery = "select booklimit from student where sid=?";
		try {
			connection = JdbcUtil.getJdbcConnection();
			if (connection != null) {
				pstmt = connection.prepareStatement(sqlQuery);
				pstmt.setInt(1, sId);
			}
			if (pstmt != null) {
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					return rs.getInt(1)<3;
				}

			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean isBookElegible(Integer bId) {
		String sqlQuery = "select bcount from book where bid=?";
		try {
			connection = JdbcUtil.getJdbcConnection();
			if (connection != null) {
				pstmt = connection.prepareStatement(sqlQuery);
				pstmt.setInt(1, bId);
			}
			if (pstmt != null) {
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					return rs.getInt(1)>0;
				}

			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Integer issueBook(Integer bId, Integer sId) {
		Integer rowAffected=null;
		String sqlQuery = "insert into booktransaction (studentid,bookid,issuedate,status) values (?,?,?,?)";
		try {
			connection = JdbcUtil.getJdbcConnection();
			if (connection != null) {
				pstmt = connection.prepareStatement(sqlQuery);
			}
			if (pstmt != null) {
				pstmt.setInt(1, sId);
				pstmt.setInt(2, bId);
				pstmt.setDate(3, new Date(new java.util.Date().getTime()));
				pstmt.setString(4, "Issued");
				rowAffected = pstmt.executeUpdate();
				
			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rowAffected;
	}

	@Override
	public Integer updateBookCount(Integer bId,UpdateDB value) {
		String sqlQuery=null;
		if (value==UpdateDB.INCREMENT) {
			sqlQuery = "update book set bcount=bcount+1 where bid=?";
		}
		if (value==UpdateDB.DECREMENT) {
			sqlQuery = "update book set bcount=bcount-1 where bid=?";
		}
		Integer rowAffected = null;
		try {
			connection = JdbcUtil.getJdbcConnection();
			if (connection != null) {
				pstmt = connection.prepareStatement(sqlQuery);
				pstmt.setInt(1, bId);
			}
			if (pstmt != null) {
				rowAffected = pstmt.executeUpdate();
			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rowAffected;
	}

	@Override
	public Integer updateStudentLimit(Integer sId,UpdateDB value) {
		String sqlQuery=null;
		if (value==UpdateDB.INCREMENT) {
			sqlQuery = "update student set booklimit=booklimit+1 where sid=?";
		}
		if (value==UpdateDB.DECREMENT) {
			sqlQuery = "update student set booklimit=booklimit-1 where sid=?";
		}
		Integer rowAffected = null;
		try {
			connection = JdbcUtil.getJdbcConnection();
			if (connection != null) {
				pstmt = connection.prepareStatement(sqlQuery);
				pstmt.setInt(1, sId);
			}
			if (pstmt != null) {
				rowAffected = pstmt.executeUpdate();
			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rowAffected;
	}

	@Override
	public ResultSet getAllStudentDetails() {
		String sqlQuery = "select sid,sname,semail,scontact,booklimit from student";
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
	public ResultSet getBookTransactions() {
		String sqlQuery = "select studentid,bookid,issuedate,returndate,status from booktransaction ORDER BY issuedate DESC";
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
	public ResultSet getStudentTransactions(Integer sId) {
		String sqlQuery = "select id,studentid,bookid,issuedate,returndate,status from booktransaction where studentid=? ORDER BY issuedate DESC";
		ResultSet resultSet = null;
		try {
			connection = JdbcUtil.getJdbcConnection();
			if (connection != null) {
				pstmt = connection.prepareStatement(sqlQuery);
				pstmt.setInt(1, sId);
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
	public Integer updateStudentTransaction(Integer id) {
		String sqlQuery = "update booktransaction set returndate=?,status=? where id=?";
		Integer rowAffected=null;
		try {
			connection = JdbcUtil.getJdbcConnection();
			if (connection != null) {
				pstmt = connection.prepareStatement(sqlQuery);
				pstmt.setDate(1,new Date(new java.util.Date().getTime()));
				pstmt.setString(2, "Returned");
				pstmt.setInt(3, id);
			}
			if (pstmt != null) {
				rowAffected = pstmt.executeUpdate();
			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rowAffected;
	}
}
