package examples.pubhub.dao;

import java.util.List;

import examples.pubhub.model.BookTag;


public interface BookTagDAO {
	
	// Retrieve Single Results
	public BookTag getBookTagByTitle(String title);
	public BookTag getBookTagByISBN(String isbn);
	
	// Retrieve List of Results
	public List<BookTag> getAllBookTag();
	public List<BookTag> getAllViewBookTagByISBN(String isbn);	
	public List<BookTag> getAllBookTagByTagName(String bookTag);
	
	// Add Record
	public boolean addBookTag(BookTag bookTag);
	
	// Update Record
	public boolean updateBookTag(BookTag bookTag);
	
	// Delete Record
	public boolean deleteBookTagByTagName(String tagName);

}
