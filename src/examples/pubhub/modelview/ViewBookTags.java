package examples.pubhub.modelview;

import java.sql.Date;
import java.time.LocalDate;

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
	
	public int getBookTagId() {
		return this.bookTags.getBookTagsId();
	}
	
	public void setBookTagId(int id) {
		this.bookTags.setBookTagsId(id);
	}
	
	public LocalDate getPublishDate() {
		return this.book.getPublishDate();
	}

	public void setPublishDate(LocalDate publishDate) {
		this.book.setPublishDate(publishDate);
	}

	public double getPrice() {
		return this.book.getPrice();
	}

	public void setPrice(double price) {
		this.book.setPrice(price);
	}
	
	public byte[] getContent() {
		return book.getContent();
	}

	public void setContent(byte[] content) {
		this.book.setContent(content);
	}

	public void setPublishDate(Date date) {
		this.book.setPublishDate( new java.sql.Date( date.getTime() ).toLocalDate() );
		
	}
	
}
