package examples.pubhub.model.view;

import java.sql.Date;
import java.time.LocalDate;

import examples.pubhub.model.Book;
import examples.pubhub.model.BookTag;

public class BookTagView {
	
	private Book book;
	private BookTag bookTag;
	
	public BookTagView() {
		this.book = new Book();
		this.bookTag = new BookTag();
	}
	
	public String getIsbn13() {
		return this.book.getIsbn13();
	}
	
	public void setIsbn13(String isbn13) {
		this.bookTag.setIsbn13(isbn13);
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
		return this.bookTag.getTagName();
	}

	public void setTagName(String tagName) {
		this.bookTag.setTagName(tagName);
	}
	
	public int getBookTagId() {
		return this.bookTag.getBookTagId();
	}
	
	public void setBookTagId(int id) {
		this.bookTag.setBookTagId(id);
	}
	
	public LocalDate getPublishDate() {
		return this.book.getPublishDate();
	}
	
	public void setPublishDate(Date date) {
		this.book.setPublishDate( date.toLocalDate() );	
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

}
