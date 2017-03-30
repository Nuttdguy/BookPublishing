package examples.pubhub.dao;

import java.util.List;

import examples.pubhub.model.BookTags;
import examples.pubhub.modelview.ViewBookTags;

public interface BookTagsDAO {
	
	public List<ViewBookTags> getAllBookTags();
	public ViewBookTags getOneViewBookTagByTitle(String title);
	public ViewBookTags getOneViewBookTagByISBN(String isbn);	
	
	public List<ViewBookTags> getViewBookTagByISBN(String isbn);
	public List<ViewBookTags> getViewBookTagByTitle(String title);
	
	public List<BookTags> getBooksByTagName(String bookTag);
	public BookTags getBookTagByISBN(String isbn);
	
	public boolean addBookTag(BookTags viewBookTag);
	public boolean addViewBookTag(ViewBookTags viewBookTag);
	
	public boolean updateBookTag(BookTags bookTag);
	public boolean updateViewBookTag(ViewBookTags bookTag);
	
	public boolean deleteBookTagByTagName(String tagName);

}
