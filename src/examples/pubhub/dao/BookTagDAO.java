package examples.pubhub.dao;

import java.util.List;

import examples.pubhub.model.BookTag;
import examples.pubhub.model.view.BookTagView;


public interface BookTagDAO {
	
	// Retrieve Single Results
	public BookTag getBookTagByBookTagId(String tagId);  // added 04-28
	public BookTag getBookTagByTagName(String tagName);  // added 04-28
	public BookTag getBookTagByTitle(String title);  // rework completed 04-25
	public BookTag getBookTagByISBN(String isbn);  // rework completed 04-25 
	
	// Retrieve List of Results
	public List<BookTag> getAllBookTag();   // rework completed 04-25
	public List<BookTag> getAllBookTagByISBN(String isbn);	// rework completed 04-25
	public List<BookTag> getAllBookTagByTitle(String title);  // rework completed 04-25
	public List<BookTag> getAllBookTagByTagName(String bookTag); // rework completed 04-25
	
	// Add Record
	public boolean addBookTag(BookTag bookTag);  // rework completed 04-25
	
	// Update Record
	public boolean updateBookTag(BookTag bookTag);  // rework completed 04-25
	
	// Delete Record
	public boolean deleteBookTagByBookTagId(String tagId);  // rework completed 04-28
	public boolean deleteBookTagByTagName(String tagName); //  review completed 04-25

}
