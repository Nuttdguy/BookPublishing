package examples.pubhub.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import examples.pubhub.model.BookTag;
import examples.pubhub.model.view.BookTagView;
import examples.pubhub.utilities.DAOUtilities;

public class BookTagDAOImpl implements BookTagDAO {

	Connection connection = null; // Our connection to the Database
	PreparedStatement stmt = null; // Use prepared statements to help protect against SQL injection

	//==||  Section I   ||  Retrieve Single Record Methods
	//==================================================\\
	
	/*------------------------------------------------------------------------------------------*/
	
	@Override
	public BookTag getBookTagByBookTagId(String tagId) {
		BookTag bookTag = null;
		
		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT * FROM book_tag WHERE book_tag_id = ?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setInt(1, Integer.valueOf(tagId) );
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next() ) {
				bookTag = new BookTag();
				bookTag.setTagName(rs.getString("tag_name"));
				bookTag.setIsbn13(rs.getString("isbn_13"));
				bookTag.setBookTagId(rs.getInt("book_tag_id"));
			}
			rs.close();
		} catch (SQLException sex) {
			sex.printStackTrace();
		} finally {
			closeResources();
		}
		return bookTag;
	}
	
	@Override
	public BookTag getBookTagByTagName(String tagName) {
		BookTag bookTag =  null;
		
		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT * FROM book_tag WHERE tag_name = ?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, tagName);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next() ) {
				bookTag =  new BookTag();
				
				bookTag.setTagName(rs.getString("tag_name"));
				bookTag.setIsbn13(rs.getString("isbn_13"));
				bookTag.setBookTagId(rs.getInt("book_tag_id"));
			}
			
			rs.close();
		} catch (SQLException sex) {
			sex.printStackTrace();
		} finally {
			closeResources();
		}
		
		return bookTag;
	}
	
	@Override
	public BookTag getBookTagByTitle(String title) {
		
		BookTag tag = null;
		
		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT a.isbn_13,"
					+ "a.title,"
					+ "a.author,"
					+ "b.* FROM books a INNER JOIN book_tag b ON a.isbn_13 = b.isbn_13 WHERE a.title = ?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, title);		
			ResultSet rs  = stmt.executeQuery();
			
			while (rs.next()) {
				tag = new BookTag();
				
				tag.setTagName(rs.getString("tag_name"));
				tag.setIsbn13(rs.getString("isbn_13"));
				tag.setBookTagId(rs.getInt("book_tag_id"));
				
			}
			rs.close();
			
		} catch (SQLException sex) {
			sex.printStackTrace();
		} finally {
			closeResources();
		}
		return tag;
	}

	/*------------------------------------------------------------------------------------------*/

	@Override
	public BookTag getBookTagByISBN(String isbn) {
		BookTag tag = null;
		
		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT * FROM book_tag WHERE isbn_13 = ?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, isbn);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				tag = new BookTag();
				
				tag.setBookTagId(rs.getInt("book_tag_id"));
				tag.setIsbn13(rs.getString("isbn_13"));
				tag.setTagName(rs.getString("tag_name"));
			}
			rs.close();
			
		} catch (SQLException sex) {
			sex.printStackTrace();
		} finally {
			closeResources();
		}
		
		return tag;
	}

	//==||  Section II   ||  Retrieve List Of Record Methods
	//==================================================\\
		
	/*------------------------------------------------------------------------------------------*/

	@Override
	public List<BookTag> getAllBookTag() {

		List<BookTag> bookTags = new ArrayList<>();

		try {
			connection = DAOUtilities.getConnection(); // Get the database connection from the manager
			String sql = "SELECT * FROM book_tag"; // The SQL query
			stmt = connection.prepareStatement(sql); // Creates the prepared statement from the query

			ResultSet rs = stmt.executeQuery(); // Queries the database

			while (rs.next()) {
				// populate a Book object with info for each row from query result
				BookTag tag = new BookTag();

				// Each variable in our Book object maps to a column in a row from result
				tag.setTagName(rs.getString("tag_name"));
				tag.setIsbn13(rs.getString("isbn_13"));
				tag.setBookTagId(rs.getInt("book_tag_id"));

				// add book to list
				bookTags.add(tag);
			}
			
			rs.close();
		} catch (SQLException sex) {
			sex.printStackTrace();
		} finally {
			// Close connection to prevent memory leak
			closeResources();
		}

		return bookTags;
	}
	

	/*------------------------------------------------------------------------------------------*/

	@Override
	public List<BookTag> getAllBookTagByISBN(String isbn) {
		
		List<BookTag> tagList = new ArrayList<>();
		
		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT "
					+ "isbn_13,"
					+ "tag_name,"
					+ "FROM book_tag WHERE isbn_13 = ?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, isbn);		
			ResultSet rs  = stmt.executeQuery();
			
			while (rs.next()) {
				BookTag tag = new BookTag();
				
				tag.setTagName(rs.getString("tag_name"));
				tag.setIsbn13(rs.getString("isbn_13"));
				tag.setBookTagId(rs.getInt("book_tag_id"));				
				tagList.add(tag);
			}
			rs.close();
			
		} catch (SQLException sex) {
			sex.printStackTrace();
		} finally {
			closeResources();
		}
		return tagList;
	}

	/*------------------------------------------------------------------------------------------------*/
	
	public List<BookTag> getAllBookTagByTitle(String title) {
		List<BookTag> tagList = new ArrayList<>();
		
		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT a.isbn_13,"
					+ " a.title,"
					+ " a.author,"
					+ " b.tag_name, "
					+ " b.book_tag_id "
					+ " FROM books a "
					+ " INNER JOIN book_tag b ON b.isbn_13 = a.isbn_13 "
					+ " WHERE a.title = ?;";
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, title );
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				BookTag tag = new BookTag();
				
				tag.setTagName(rs.getString("tag_name"));
				tag.setIsbn13(rs.getString("isbn_13"));
				tag.setBookTagId(rs.getInt("book_tag_id"));				
				tagList.add(tag);
			}
			rs.close();
		} catch (SQLException sex) {
			sex.printStackTrace();	
		} finally {
			closeResources();
		}
		
		return tagList;
	}
	
	/*------------------------------------------------------------------------------------------------*/

	@Override
	public List<BookTag> getAllBookTagByTagName(String tagName) {

		List<BookTag> tagList = new ArrayList<>();
		
		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT * FROM book_tag WHERE tag_name LIKE ?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, "%" + tagName + "%");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				BookTag tag = new BookTag();

				tag.setTagName(rs.getString("tag_name"));
				tag.setIsbn13(rs.getString("isbn_13"));
				tag.setBookTagId(rs.getInt("book_tag_id"));				
				tagList.add(tag);
			}
			rs.close();
		} catch (SQLException sex) {
			sex.printStackTrace();
		} finally {
			closeResources();
		}
		
		return tagList;
	}

	//==||  Section III   ||  Add New Record Methods
	//==================================================\\
		
	/*------------------------------------------------------------------------------------------*/

	@Override
	public boolean addBookTag(BookTag bookTag) {

		try {
			connection = DAOUtilities.getConnection();
			String sql = "INSERT INTO book_tag (isbn_13, tag_name) VALUES (?, ?)";
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, bookTag.getIsbn13());
			stmt.setString(2, bookTag.getTagName());
			
			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
			
		} catch (SQLException sex) {
			sex.printStackTrace();
			return false;
		} finally {
			closeResources();
		}

	}

	//==||  Section IV   ||  Update Record Methods
	//==================================================\\
		
	/*------------------------------------------------------------------------------------------*/

	@Override
	public boolean updateBookTag(BookTag bookTag) {

		try {
			connection = DAOUtilities.getConnection();
			String sql = "UPDATE book_tag SET tag_name=? WHERE book_tag_id =?";
			stmt = connection.prepareStatement(sql);

			stmt.setString(1, bookTag.getTagName());
			stmt.setInt(2, bookTag.getBookTagId());

			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;

		} catch (SQLException sex) {
			sex.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}

	//==||  Section V   ||  Delete Record Methods
	//==================================================\\
		
	/*------------------------------------------------------------------------------------------*/

	@Override
	public boolean deleteBookTagByBookTagId(String tagId) {

		try {
			connection = DAOUtilities.getConnection();
			String sql = "DELETE FROM book_tag WHERE book_tag_id = ?";
			stmt = connection.prepareStatement(sql);

			stmt.setInt(1,Integer.valueOf( tagId) );

			if (stmt.executeUpdate() != 0) {
				return true;
			} else
				return false;
		} catch (SQLException sex) {
			sex.printStackTrace();
			return false;
		} finally {
			closeResources();
		}

	}
	
	@Override
	public boolean deleteBookTagByTagName(String tagName) {

		try {
			connection = DAOUtilities.getConnection();
			String sql = "DELETE FROM book_tag WHERE tag_name = ?";
			stmt = connection.prepareStatement(sql);

			stmt.setString(1, tagName);

			if (stmt.executeUpdate() != 0) {
				return true;
			} else
				return false;
		} catch (SQLException sex) {
			sex.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}

	//==||  Section VI   ||  Close Resource Methods
	//==================================================\\
		
	/*------------------------------------------------------------------------------------------*/

	// Close all resources in order to prevent memory leaks.
	// Ideally, you want to close them in the reverse order opened
	private void closeResources() {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException sex) {
			System.out.println("Could not close statement!");
			sex.printStackTrace();
		}

		try {
			if (connection != null)
				connection.close();
		} catch (SQLException sex) {
			System.out.println("Could not close connection!");
			sex.printStackTrace();
		}
	}

}


/*------------------------------------------------------------------------------------------*/

//@Override
//public BookTag getBookTagByISBN(String isbn) {
//	
//	BookTag tag = null;;
//	
//	try {
//		connection = DAOUtilities.getConnection();
//		String sql = "SELECT a.isbn_13,"
//				+ "a.title,"
//				+ "a.author,"
//				+ "b.* FROM books a INNER JOIN book_tags b ON a.isbn_13 = b.isbn_13 "
//				+ "WHERE a.isbn_13 = ?";
//		stmt = connection.prepareStatement(sql);
//		
//		stmt.setString(1, isbn);		
//		ResultSet rs  = stmt.executeQuery();
//		
//		while (rs.next()) {
//			
//			tag = new BookTag();
//			
//			tag.setTagName(rs.getString("tag_name"));
//			tag.setIsbn13(rs.getString("isbn_13"));
//			tag.setBookTagId(rs.getInt("book_tag_id"));
//
//		}
//		rs.close();
//	} catch (SQLException sex) {
//		sex.printStackTrace();
//	} finally {
//		closeResources();
//	}
//	return tag;
//}


/*------------------------------------------------------------------------------------------------*/

//@Override
//public boolean addBookTag(BookTag bookTag) {
//
//	try {
//		connection = DAOUtilities.getConnection();
//		String sql = "INSERT INTO book_tags (isbn_13, tag_name) VALUES (?, ?)";
//		stmt = connection.prepareStatement(sql);
//
//		stmt.setString(1, bookTag.getIsbn13());
//		stmt.setString(2, bookTag.getTagName());
//		
//		if (stmt.executeUpdate() != 0)
//			return true;
//		else
//			return false;
//		
//	} catch (SQLException sex) {
//		sex.printStackTrace();
//		return false;
//	} finally {
//		closeResources();
//	}
//
//}

/*------------------------------------------------------------------------------------------------*/

//@Override
//public boolean updateViewBookTag(BookTagView bookTag) {
//	
//	try {
//		connection = DAOUtilities.getConnection();
//		String sql = "UPDATE books SET author=? WHERE isbn_13 =?;"
//				+ "UPDATE books SET title=? WHERE isbn_13=?;"
//				+ "UPDATE book_tags SET tag_name=? WHERE booktags_id=?;";
//		stmt = connection.prepareStatement(sql);
//		
//		stmt.setString(1, bookTag.getAuthor());
//		stmt.setString(2, bookTag.getIsbn13());
//		stmt.setString(3, bookTag.getTitle());
//		stmt.setString(4, bookTag.getIsbn13());
//		stmt.setString(5, bookTag.getTagName());
//		stmt.setInt(6, bookTag.getBookTagId());
//		
//		if (stmt.executeUpdate() != 0)
//			return true;
//		else
//			return false;	
//	} catch (SQLException sex) {
//		sex.printStackTrace();
//		return false;
//	} finally {
//		closeResources();
//	}
//}





