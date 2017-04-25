package examples.pubhub.dao;

import java.util.List;

import examples.pubhub.model.Book;

/**
 * Interface for our Data Access Object to handle database queries related to Books.
 */
public interface BookDAO {
	
	// Retrieve Single Result
	public List<Book> getAllBookWithTag();	
	public Book getBookByISBN(String isbn);

	// Retrieve List of Results
	public List<Book> getAllBooks();
	public List<Book> getBooksByTitle(String title);
	public List<Book> getBooksByAuthor(String author);
	public List<Book> getBooksLessThanPrice(double price);
	
	// Add Record
	public boolean addBook(Book book);
	
	// Update Record
	public boolean updateBook(Book book);
	
	// Delete Record
	public boolean deleteBookByISBN(String isbn);
}
