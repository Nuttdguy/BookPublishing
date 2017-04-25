package examples.pubhub.model;

public class BookTag {

	private int bookTagId;
	private String isbn13;
	private String tagName;
	
	public BookTag() {
		this.bookTagId = 0;
		this.tagName = null;
		this.isbn13 = null;
	}
	
	public BookTag(int bookTagId, String tagName, String isbn) {
		this.bookTagId = bookTagId;
		this.tagName = tagName;
		this.isbn13 = isbn;
	}

	public int getBookTagId() {
		return bookTagId;
	}

	public void setBookTagId(int bookTagId) {
		this.bookTagId = bookTagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getIsbn13() {
		return isbn13;
	}

	public void setIsbn13(String isbn13) {
		this.isbn13 = isbn13;
	}
	
}
