package examples.pubhub.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import examples.pubhub.model.Book;
import examples.pubhub.model.view.BookTagView;
import examples.pubhub.utilities.DAOUtilities;

/**
 * Implementation for the BookDAO, responsible for querying the database for Book objects.
 */
public class BookDAOImpl implements BookDAO {

	Connection connection = null;	// Our connection to the database
	PreparedStatement stmt = null;	// We use prepared statements to help protect against SQL injection
	
	//==||  Section I   ||  Retrieve Single Record Methods
	//==================================================\\
	
	/*------------------------------------------------------------------------------------------*/
	
	@Override
	public Book getBookByISBN(String isbn) {
		Book book = null;

		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT * FROM Books WHERE isbn_13 = ?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, isbn);		
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				book = new Book();
				book.setIsbn13(rs.getString("isbn_13"));
				book.setAuthor(rs.getString("author"));
				book.setTitle(rs.getString("title"));
				book.setPublishDate(rs.getDate("publish_date").toLocalDate());
				book.setPrice(rs.getDouble("price"));
				book.setContent(rs.getBytes("content"));			
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		
		return book;
	}

	/*------------------------------------------------------------------------------------------*/
	
	@Override
	public Book getBookByTitle(String title) {
		Book book = null;

		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT * FROM Books WHERE title = ?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, title);		
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				book = new Book();
				book.setIsbn13(rs.getString("isbn_13"));
				book.setAuthor(rs.getString("author"));
				book.setTitle(rs.getString("title"));
				book.setPublishDate(rs.getDate("publish_date").toLocalDate());
				book.setPrice(rs.getDouble("price"));
				book.setContent(rs.getBytes("content"));			
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		
		return book;
	}

	//==||  Section II   ||  Retrieve List Of Record Methods
	//==================================================\\
		
	/*------------------------------------------------------------------------------------------*/
	
	@Override
	public List<Book> getAllBooks() {
		
		List<Book> books = new ArrayList<>();
		
		try {
			connection = DAOUtilities.getConnection();	// Get our database connection from the manager
			String sql = "SELECT * FROM Books";			// Our SQL query
			stmt = connection.prepareStatement(sql);	// Creates the prepared statement from the query
			
			ResultSet rs = stmt.executeQuery();			// Queries the database

			// So long as the ResultSet actually contains results...
			while (rs.next()) {
				// We need to populate a Book object with info for each row from our query result
				Book book = new Book();

				// Each variable in our Book object maps to a column in a row from our results.
				book.setIsbn13(rs.getString("isbn_13"));
				book.setAuthor(rs.getString("author"));
				book.setTitle(rs.getString("title"));
				
				// The SQL DATE datatype maps to java.sql.Date... which isn't well supported anymore. 
				// We use a LocalDate instead, because this is Java 8.
				book.setPublishDate(rs.getDate("publish_date").toLocalDate() );
				book.setPrice(rs.getDouble("price"));
				
				// The PDF file is tricky; file data is stored in the DB as a BLOB - Binary Large Object. It's
				// literally stored as 1's and 0's, so this one data type can hold any type of file.
				book.setContent(rs.getBytes("content"));
				
				// Finally we add it to the list of Book objects returned by this query.
				books.add(book);
				
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// We need to make sure our statements and connections are closed, 
			// or else we could wind up with a memory leak
			closeResources();
		}
		
		// return the list of Book objects populated by the DB.
		return books;
	}

	
	/*------------------------------------------------------------------------------------------------*/

	
	@Override 
	public List<Book> getAllBookWithTag() {
		
		List<Book> bookList = new ArrayList<>();
		
		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT a.*,"
					+ " b.* "
					+ " FROM books a "
					+ " INNER JOIN book_tag b"
					+ " ON a.isbn_13 = b.isbn_13"
					+ " WHERE a.has_tag = true"; 
			stmt = connection.prepareStatement(sql); 
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Book book = new Book();
				
				// Handle Date to LocalDate conversion
				Date date = rs.getDate("publish_date");
				book.setPublishDate(  date.toLocalDate() );
				
				book.setAuthor(rs.getString("author"));
				book.setTitle(rs.getString("title"));
				book.setPrice(rs.getDouble("price"));
				book.setContent(rs.getBytes("content"));
				book.setIsbn13(rs.getString("isbn_13"));
				
				bookList.add(book);
			}
			rs.close();
			
		} catch (SQLException sex ) {
			sex.printStackTrace();
		} finally {
			closeResources();
		}
		
		return bookList;
	}
	
	/*------------------------------------------------------------------------------------------------*/
	
	
	@Override
	public List<Book> getBooksByTitle(String title) {
		
		List<Book> books = new ArrayList<>();

		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT * FROM Books WHERE title LIKE ?";	// Note the ? in the query
			stmt = connection.prepareStatement(sql);
			
			// This command populate the 1st '?' with the title and wildcards, between ' '
			stmt.setString(1, "%" + title + "%");	
			
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Book book = new Book();

				book.setIsbn13(rs.getString("isbn_13"));
				book.setAuthor(rs.getString("author"));
				book.setTitle(rs.getString("title"));
				book.setPublishDate(rs.getDate("publish_date").toLocalDate());
				book.setPrice(rs.getDouble("price"));
				book.setContent(rs.getBytes("content"));
				
				books.add(book);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		
		return books;
	}

	
	/*------------------------------------------------------------------------------------------------*/

	
	@Override
	public List<Book> getBooksByAuthor(String author) {
		List<Book> books = new ArrayList<>();

		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT * FROM Books WHERE author LIKE ?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, "%" + author + "%");
			
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Book book = new Book();

				book.setIsbn13(rs.getString("isbn_13"));
				book.setAuthor(rs.getString("author"));
				book.setTitle(rs.getString("title"));
				book.setPublishDate(rs.getDate("publish_date").toLocalDate());
				book.setPrice(rs.getDouble("price"));
				book.setContent(rs.getBytes("content"));
				
				books.add(book);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		
		return books;
	}

	
	/*------------------------------------------------------------------------------------------------*/

	
	@Override
	public List<Book> getBooksLessThanPrice(double price) {
		List<Book> books = new ArrayList<>();

		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT * FROM Books WHERE price < ?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setDouble(1, price);;
			
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Book book = new Book();

				book.setIsbn13(rs.getString("isbn_13"));
				book.setAuthor(rs.getString("author"));
				book.setTitle(rs.getString("title"));
				book.setPublishDate(rs.getDate("publish_date").toLocalDate());
				book.setPrice(rs.getDouble("price"));
				book.setContent(rs.getBytes("content"));
				
				books.add(book);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		
		return books;
	}
	
	//==||  Section III   ||  Add New Record Methods
	//==================================================\\
		
	/*------------------------------------------------------------------------------------------*/
	
	@Override
	public boolean addBook(Book book) {
		
		try {
			connection = DAOUtilities.getConnection();
			String sql = "INSERT INTO books (isbn_13, title, author, publish_date, price, has_tag, content) VALUES (?, ?, ?, ?, ?, ?, ?);"
					+ "INSERT INTO book_tag (isbn_13, tag_name) VALUES (?, ?);"; // Were using a lot of ?'s here...
			stmt = connection.prepareStatement(sql);
			
			// But that's okay, we can set them all before we execute
			stmt.setString(1, book.getIsbn13());
			stmt.setString(2, book.getTitle());
			stmt.setString(3, book.getAuthor());
			stmt.setDate(4, Date.valueOf( book.getPublishDate() ) );
		
			stmt.setDouble( 5, book.getPrice() );		
			stmt.setBoolean(6, book.getHasTag() );
			stmt.setBytes(7, book.getContent());
			
			stmt.setString(8, book.getIsbn13());
			stmt.setString(9, " ");
			
			// If we were able to add our book to the DB, we want to return true. 
			// This if statement both executes our query, and looks at the return 
			// value to determine how many rows were changed
			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}
	
	//==||  Section IV   ||  Update Record Methods
	//==================================================\\
		
	/*------------------------------------------------------------------------------------------*/
	
	@Override
	public boolean updateBook(Book book) {
		try {
			connection = DAOUtilities.getConnection();
			String sql = "UPDATE Books SET title=?, author=?, price=? WHERE isbn_13=?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, book.getTitle());
			stmt.setString(2, book.getAuthor());
			stmt.setDouble(3, book.getPrice());
			stmt.setString(4, book.getIsbn13());
			
			System.out.println(stmt);
			
			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
		
	}
	
	//==||  Section V   ||  Delete Record Methods
	//==================================================\\
		
	/*------------------------------------------------------------------------------------------*/
	
	@Override
	public boolean deleteBookByISBN(String isbn) {
		try {
			connection = DAOUtilities.getConnection();
			String sql = "DELETE Books WHERE isbn_13=?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, isbn);

			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}

	//==||  Section VI   ||  Close Resource Methods
	//==================================================\\
		
	/*------------------------------------------------------------------------------------------*/
	
	// Closing all resources is important, to prevent memory leaks. 
	// Ideally, you really want to close them in the reverse-order you open them
	private void closeResources() {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			System.out.println("Could not close statement!");
			e.printStackTrace();
		}
		
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			System.out.println("Could not close connection!");
			e.printStackTrace();
		}
	}
	
}
