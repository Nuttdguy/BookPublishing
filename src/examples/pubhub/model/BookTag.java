package examples.pubhub.model;

public class BookTag {

	private int bookTagsId;
	private String isbn13;
	private String tagName;
	
	public BookTag() {
		this.bookTagsId = 0;
		this.tagName = null;
		this.isbn13 = null;
	}
	
	public BookTag(int bookTagsId, String tagName, String isbn) {
		this.bookTagsId = bookTagsId;
		this.tagName = tagName;
		this.isbn13 = isbn;
	}

	public int getBookTagsId() {
		return bookTagsId;
	}

	public void setBookTagsId(int bookTagsId) {
		this.bookTagsId = bookTagsId;
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
