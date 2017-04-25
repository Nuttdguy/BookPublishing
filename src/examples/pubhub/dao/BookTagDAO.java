package examples.pubhub.dao;

import java.util.List;

import examples.pubhub.model.BookTags;
import examples.pubhub.model.view.BookTagView;

public interface BookTagDAO {
	
	public List<BookTagView> getAllBookTags();
	public BookTagView getOneViewBookTagByTitle(String title);
	public BookTagView getOneViewBookTagByISBN(String isbn);	
	
	public List<BookTagView> getViewBookTagByISBN(String isbn);
	public List<BookTagView> getViewBookTagByTitle(String title);
	
	public List<BookTags> getBooksByTagName(String bookTag);
	public BookTags getBookTagByISBN(String isbn);
	
	public boolean addBookTag(BookTags viewBookTag);
	public boolean addViewBookTag(BookTagView viewBookTag);
	
	public boolean updateBookTag(BookTags bookTag);
	public boolean updateViewBookTag(BookTagView bookTag);
	
	public boolean deleteBookTagByTagName(String tagName);

}
