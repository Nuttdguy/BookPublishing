package examples.pubhub.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Book {

	private String isbn13;			// International Standard Book Number, unique
	private String title;
	private String author;
	private LocalDate publishDate;	// Date of publish to the website
	private boolean hasTag;
	private List<BookTag> bookTags;
	private double price;
	private byte[] content;

	// Constructor used when no date is specified
	public Book(String isbn, String title, String author, byte[] content) {
		this.isbn13 = isbn;
		this.title = title;
		this.author = author;
		this.publishDate = LocalDate.now();
		this.content = content;
		this.bookTags = new ArrayList<>();
	}
	
	// Constructor used when a date is specified
	public Book(String isbn, String title, String author, byte[] content, LocalDate publishDate) {
		this.isbn13 = isbn;
		this.title = title;
		this.author = author;
		this.publishDate = publishDate;
		this.content = content;
		this.bookTags = new ArrayList<>();
	}
	
	// Default constructor
	public Book() {
		this.isbn13 = null;
		this.title = null;
		this.author = null;
		this.publishDate = null ;
		this.content = null;
		this.hasTag = true;
		this.bookTags = new ArrayList<>();
	}
	
	public String getIsbn13() {
		return isbn13;
	}

	public void setIsbn13(String isbn13) {
		this.isbn13 = isbn13;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public LocalDate getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(LocalDate publishDate) {
		this.publishDate = publishDate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public boolean getHasTag() {
		return hasTag;
	}

	public void setHasTag(boolean hasTag) {
		this.hasTag = hasTag;
	}

	public List<BookTag> getBookTags() {
		return bookTags;
	}

	public void setBookTags(BookTag bookTags) {
		this.bookTags.add(bookTags);
	}	
	
}
