package examples.pubhub.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import examples.pubhub.model.BookTags;
import examples.pubhub.utilities.DAOUtilities;

public class BookTagsDAOImpl implements BookTagsDAO {
	
	Connection connection = null; // Our connection to the Database
	PreparedStatement stmt = null;  // Use prepared statements to help protect against SQL injection
	
	/*------------------------------------------------------------------------------------------*/

	@Override
	public List<BookTags> getAllBookTags() {

		List<BookTags> bookTags = new ArrayList<>();
		
		try {
			connection = DAOUtilities.getConnection(); // Get the database connection from the manager
			String sql = "SELECT * FROM book_tags";  // The SQL query
			stmt = connection.prepareStatement(sql);  // Creates the prepared statement from the query
			
			ResultSet resultSet = stmt.executeQuery();  // Queries the database
			
			while(resultSet.next()) {
				// populate a Book object with info for each row from query result
				BookTags tag = new BookTags(); 
				
				// Each variable in our Book object maps to a column in a row from result
				tag.setTagName(resultSet.getString("tag_name"));
				tag.setIsbn13(resultSet.getString("isbn13"));
				
				// add book to list
				bookTags.add(tag);
			}
			resultSet.close();			
		} catch (SQLException sex) {
			sex.printStackTrace();
		} finally {
			// Close connection to prevent memory leak
			closeResources();
		}
		
		return bookTags;
	}
	
	
	/*------------------------------------------------------------------------------------------------*/
	
	
	@Override
	public List<BookTags> getBooksByTagName(String bookTag) {

		List<BookTags> bookTags = new ArrayList<>();
		
		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT * FROM book_tags WHERE tag_name LIKE ?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1,  "%" + bookTag + "%");
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				BookTags tag = new BookTags();
				
				tag.setBookTagsId(rs.getInt("booktags_id"));
				tag.setIsbn13(rs.getString("isbn13"));
				tag.setTagName(rs.getString("tag_name"));
				
				bookTags.add(tag);
			} 
			rs.close();
		} catch (SQLException sex) {
			sex.printStackTrace();
		} finally {
			closeResources();
		}
		
		return bookTags;
	}

	
	/*------------------------------------------------------------------------------------------------*/
	
	
	@Override
	public BookTags getBookTagByISBN(String isbn) {
		
		BookTags bookTags = null;
		
		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT * FROM book_tags WHERE isbn13 = ?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, isbn);
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				bookTags = new BookTags();
				
				bookTags.setBookTagsId(rs.getInt("booktags_id"));
				bookTags.setIsbn13(rs.getString("isbn13"));
				bookTags.setTagName(rs.getString("tag_name"));
				
			}
			
			rs.close();
		} catch (SQLException sex ) {
			sex.printStackTrace();
		} finally {
			closeResources();
		}
		
		return bookTags;
	}

	
	/*------------------------------------------------------------------------------------------------*/
	
	
	@Override
	public boolean addBookTag(BookTags bookTag) {
		
		try {
			connection = DAOUtilities.getConnection();
			String sql = "INSERT INTO book_tags VALUES (?, ?)";
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

	
	/*------------------------------------------------------------------------------------------------*/
	
	
	@Override
	public boolean updateBookTag(BookTags bookTag) {

		try {
			connection = DAOUtilities.getConnection();
			String sql = "UPDATE book_tags SET tag_name=? WHERE isbn13=?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, bookTag.getTagName());
			stmt.setString(2, bookTag.getIsbn13());
			
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
	
	
	/*------------------------------------------------------------------------------------------------*/
	

	@Override
	public boolean deleteBookTagByISBN(String isbn) {
		
		try {
			connection = DAOUtilities.getConnection();
			String sql = "DELETE book_tags WHERE isbn13 = ?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, isbn);
			
			if (stmt.executeUpdate() != 0 ) {
				return true;
			} else
				return false;
		} catch (SQLException sex ) {
			sex.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
		
	}

	
	/*------------------------------------------------------------------------------------------------*/
	
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
			if (connection != null )
				connection.close();
		} catch (SQLException sex) {
			System.out.println("Could not close connection!");
			sex.printStackTrace();
		}
	}
	
	
}
