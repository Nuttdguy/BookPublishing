package examples.pubhub.modelview;

import examples.pubhub.model.Book;
import examples.pubhub.model.BookTags;

public class ViewBookTags {
	
	private Book book;
	private BookTags bookTags;
	
	public ViewBookTags() {
		this.book = new Book();
		this.bookTags = new BookTags();
	}
	
	public String getIsbn13() {
		return this.book.getIsbn13();
	}
	
	public void setIsbn13(String isbn13) {
		this.bookTags.setIsbn13(isbn13);
		this.book.setIsbn13(isbn13);
	}
	
	public String getTitle() {
		return this.book.getTitle();
	}

	public void setTitle(String title) {
		this.book.setTitle(title);
	}
	
	public String getAuthor() {
		return this.book.getAuthor();
	}

	public void setAuthor(String author) {
		this.book.setAuthor(author);
	}
	
	public String getTagName() {
		return this.bookTags.getTagName();
	}

	public void setTagName(String tagName) {
		this.bookTags.setTagName(tagName);
	}
	
}
