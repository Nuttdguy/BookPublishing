package examples.pubhub.service;

import java.util.List;

import examples.pubhub.model.view.BookTagView;

public interface BookTagViewService {

	// Retrieve Single Results, << should always return as List
	public BookTagView getBookTagViewByTitle(String title);
	public BookTagView getBookTagViewByISBN(String isbn);
	
	// Retrieve List of Results
	public List<BookTagView> getAllBookTagViewWithTag(); // books have "has_tag" flag enabled by default
	public List<BookTagView> getAllBookTagViewByISBN(String isbn);
	public List<BookTagView> getAllBookTagViewByTitle(String title);
	
	// Add Record
	public boolean addBookTagView(BookTagView bookTagView);
	
	// Update Record
	public boolean updateBookTagView(BookTagView bookTagView);
	
	// Delete Record
	public boolean deleteBookTagByTagName(String tagName);														
	
}
