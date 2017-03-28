package examples.pubhub.dao;

import java.util.List;

import examples.pubhub.model.BookTags;

public interface BookTagsDAO {
	
	public List<BookTags> getAllBookTags();
	public List<BookTags> getBooksByTagName(String bookTag);
	public BookTags getBookTagByISBN(String isbn);
	
	public boolean addBookTag(BookTags bookTag);
	public boolean updateBookTag(BookTags bookTag);
	public boolean deleteBookTagByISBN(String isbn);

}
