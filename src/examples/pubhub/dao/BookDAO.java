package examples.pubhub.dao;

import java.util.List;

import examples.pubhub.model.Book;

/**
 * Interface for our Data Access Object to handle database queries related to Books.
 */
public interface BookDAO {
	
	// Retrieve Single Result
	public Book getBookByISBN(String isbn);  //  review completed 04-25
	public Book getBookByTitle(String title);  //  added 04-26

	// Retrieve List of Results
	public List<Book> getAllBooks();  //  review completed 04-25
	public List<Book> getAllBookWithTag(); //  review completed 04-25
	public List<Book> getBooksByTitle(String title);  //  review completed 04-25
	public List<Book> getBooksByAuthor(String author);  //  review completed 04-25
	public List<Book> getBooksLessThanPrice(double price); //  review completed 04-25
	
	// Add Record
	public boolean addBook(Book book);  //  review completed 04-25
	
	// Update Record
	public boolean updateBook(Book book);  //  review completed 04-25
	
	// Delete Record
	public boolean deleteBookByISBN(String isbn);  //  review completed 04-25
}
