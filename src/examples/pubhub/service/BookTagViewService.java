package examples.pubhub.service;

import java.util.List;

import examples.pubhub.model.view.BookTagView;

public interface BookTagViewService {

	// Retrieve Single Results, << should always return as List
//	public BookTagView getViewBookTagByTitle(String title);
//	public BookTagView getViewBookTagByISBN(String isbn);
	
	// Retrieve List of Results
	public List<BookTagView> getAllViewBookTagByISBN(String isbn);
	public List<BookTagView> getAllViewBookTagByTitle(String title);
	
	// Add Record
	public boolean addViewBookTag(BookTagView bookTagView);
	
	// Update Record
	public boolean updateViewBookTag(BookTagView bookTagView);
	
	// Delete Record
	public boolean deleteBookTagByTagName(String tagName);														
	
}
