package examples.pubhub.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import examples.pubhub.dao.BookDAO;
import examples.pubhub.dao.BookDAOImpl;
import examples.pubhub.dao.BookTagDAO;
import examples.pubhub.dao.BookTagDAOImpl;
import examples.pubhub.model.Book;
import examples.pubhub.model.BookTag;
import examples.pubhub.model.view.BookTagView;
import examples.pubhub.utilities.DAOUtilities;

public class BookTagViewServiceImpl implements BookTagViewService {
	
	//==||  Define DAO instance variables
	BookDAO bookDao = new BookDAOImpl();
	BookTagDAO bookTagDao = new BookTagDAOImpl();
	
	//==||  Section I   ||  Retrieve Single Record Methods
	//==================================================\\
	
	/*------------------------------------------------------------------------------------------*/
	
	@Override
	public BookTagView getBookTagViewByBookTagId(String tagId) {
		BookTag bookTag = bookTagDao.getBookTagByBookTagId(tagId);
		Book book = bookDao.getBookByISBN( bookTag.getIsbn13() );
		return entityToBookTagViewDTO(bookTag, book);
	}
	
	@Override
	public BookTagView getBookTagViewByTagName(String tagName) {
		
		BookTag bookTag = bookTagDao.getBookTagByTagName(tagName);		
		Book book = bookDao.getBookByISBN( bookTag.getIsbn13() );
		
		return entityToBookTagViewDTO(bookTag, book);
	}
	
	@Override
	public BookTagView getBookTagViewByTitle(String title) {
		//  We need to retrieve records from BookDao and BookTagDao matching title
		BookTagView bookView = new BookTagView();
		
		//  Get book from BookDaoImpl
		Book book = bookDao.getBookByTitle(title);
		
		//  Get book_tag from BookTagDaoImpl using isbn_13
		BookTag bookTag = bookTagDao.getBookTagByISBN( book.getIsbn13() );
		
		//  Transfer Query results to BookTagView
		//  ## there may be "more than one" tag per title
		bookView = entityToBookTagViewDTO(bookTag, book);
		
		return bookView;
	}

	@Override
	public BookTagView getBookTagViewByISBN(String isbn) {
		//  We need to retrieve records from BookDao and BookTagDao matching title
		BookTagView bookView = new BookTagView();
		
		//  Get book from BookDaoImpl
		Book book = bookDao.getBookByISBN(isbn);
		
		//  Get book_tag from BookTagDaoImpl using isbn_13
		BookTag bookTag = bookTagDao.getBookTagByISBN( book.getIsbn13() );
		
		//  Transfer Query results to BookTagView
		//  ## there may be "more than one" tag per title
		bookView = entityToBookTagViewDTO(bookTag, book);
		
		return bookView;
	}
	
	//==||  Section II   ||  Retrieve List Of Record Methods
	//==================================================\\
		
	/*------------------------------------------------------------------------------------------*/
	
	@Override
	public List<BookTagView> getAllBookTagViewWithTag() {
		//  We need to retrieve all books that have "has_tag == true"
		
		//  Get all books with "has_tag" enabled
		//  # important columns are "isbn_13, title, and author"
		List<Book> bookList = bookDao.getAllBookWithTag();
		
		//  Get all tags from "book_tag"
		//  # there may be "more than" one tag per "isbn_13"
		//  # we want to create a new "BookTagView" object for each tag
		//  # AND then add object to bookTagView list
		List<BookTag> tagList = bookTagDao.getAllBookTag();
		
		//  Initialize List of BookTagView to transfer Query results
		List<BookTagView> tagViewList = new ArrayList<>();
		
		//  Sort book tags, match with "isbn_13"
		//  Firstly, get the number of "unique isbn_13" size for loop
		//  AND then the number of "tag_names" for that isbn_13.
		for (Book book : bookList) {
			String isbn = book.getIsbn13();
			for (BookTag tag : tagList) {
				//  if isbn_13 == current tag isbn_13, transfer data 
				if (isbn.equals( tag.getIsbn13() ) ) {
					// if true, transfer current data and add to "bookTagView" list
					tagViewList.add( entityToBookTagViewDTO(tag, book) );
				}
			}
		}
		return tagViewList;
	}
	
	@Override
	public List<BookTagView> getAllBookTagViewByTitle(String title) {
		// We are getting number of records according to title;
		// ##  may have "more than" one tag per title
		// ##  will have "one" author and "one" title per "isbn_13"
		
		//  Retrieve all book tag records matching title 
		List<BookTag> tagList = bookTagDao.getAllBookTagByTitle(title);
		
		//  Retrieve "one" author for the matching title
		Book book = bookDao.getBookByTitle(title);
		
		//  Initialize List for BookTagView
		List<BookTagView> bookTagViewList = new ArrayList<>();
		
		//  Set Query Results to BookTagView
		for (BookTag tag : tagList) {
			// For each book tag, add result to bookTagView list
			bookTagViewList.add( entityToBookTagViewDTO( tag, book) );
		}
		
		return bookTagViewList;
	}
	
	@Override
	public List<BookTagView> getAllBookTagViewByISBN(String isbn) {
		// We are getting number of records according to "isbn_13"
		// ##  may have "more than" one tag per isbn_13
		// ##  may have only "one title" per isbn_13
		// ##  may have only "one author" per isbn_13
		
		//  Retrieve a list of "book tags" matching isbn_13
		List<BookTag> tagList = bookTagDao.getAllBookTagByISBN(isbn);
		
		//  Retrieve "one" author and title matching isbn_13
		Book book = bookDao.getBookByISBN(isbn);
		
		// Initialize list of bookTagView to transfer to data to view object
		List<BookTagView> bookTagViewList = new ArrayList<>();
		
		for (BookTag tag : tagList) {
			//  For each book tag, add result to bookTagViewList
			bookTagViewList.add( entityToBookTagViewDTO(tag, book) );
		}
		
		return bookTagViewList;
	}

	//==||  Section III   ||  Add New Record Methods
	//==================================================\\
		
	/*------------------------------------------------------------------------------------------*/
	
	@Override
	public boolean addBookTagView(BookTagView bookTagView) {
		//  We need to add a new record to book and book tag
		//  ##  firstly, the "isbn_13" is the primary key; therefore "book" is created first
		//  ##  secondly, the record for "book_tag" can be created with the "isbn_13"
		
		//  Set boolean flag to get success or failure result
		boolean result = false;
		
		//  Transfer bookTagView to book object, then add book
		result = bookDao.addBook( bookTagViewToBookDTO(bookTagView) );
		
		// Transfer bookTagView to bookTag object, then add book_tag
		result = bookTagDao.addBookTag( bookTagViewToBookTagDTO(bookTagView) );
		
		return result;
	}

	//==||  Section IV   ||  Update Record Methods
	//==================================================\\
		
	/*------------------------------------------------------------------------------------------*/
	
	@Override
	public boolean updateBookTagView(BookTagView bookTagView) {
		//  We need to update "one" book record && "one" book_tag record
		boolean result = false;
		
		//  Transfer "bookTagView" data to "book", update record that matches isbn_13
		result = bookDao.updateBook( bookTagViewToBookDTO(bookTagView) );
		
		//  Transfer "bookTagView" data to "book_tag", update record that matches isbn_13
		result = bookTagDao.updateBookTag( bookTagViewToBookTagDTO(bookTagView) );
		
		return result;
	}

	//==||  Section V   ||  Delete Record Methods
	//==================================================\\
		
	/*------------------------------------------------------------------------------------------*/
	
	@Override
	public boolean deleteBookTagByBookTagId(String tagId) {
		//  We need to single "tag" delete record from book_tag
		boolean result = false;
		
		//  Utilize BookTagDAOImpl to delete record from book_tag
		result = bookTagDao.deleteBookTagByBookTagId(tagId);
		
		return result;
	}
	
	//==||  Section VI   ||  Misc. Methods
	//==================================================\\
		
	/*------------------------------------------------------------------------------------------*/
	
	private static BookTagView entityToBookTagViewDTO(BookTag tag, Book book) {
		//  Initialize new BookTagView
		BookTagView bkView = new BookTagView();
		
		// Set SQL results i.e. tag to bkView
		bkView.setAuthor( book.getAuthor() );
		bkView.setTitle( book.getTitle() );
		bkView.setIsbn13( book.getIsbn13() );
		bkView.setPrice( book.getPrice() );
		bkView.setTagName( tag.getTagName() );
		bkView.setBookTagId( tag.getBookTagId() );
		bkView.setPublishDate( book.getPublishDate() );
		
		return bkView;	
	}
	
	private static Book bookTagViewToBookDTO(BookTagView bookTagView) {
		
		Book book = new Book();
		book.setIsbn13( bookTagView.getIsbn13() );
		book.setTitle( bookTagView.getTitle() );
		book.setAuthor( bookTagView.getAuthor() );
		book.setPublishDate( bookTagView.getPublishDate() );
		book.setPrice( bookTagView.getPrice() );
		book.setContent( bookTagView.getContent() );
		book.setHasTag( true );
		return book;
	}
	
	private static BookTag bookTagViewToBookTagDTO(BookTagView bookTagView) {
		
		BookTag bookTag = new BookTag();
		bookTag.setBookTagId( bookTagView.getBookTagId() );
		bookTag.setIsbn13( bookTagView.getIsbn13() );
		bookTag.setTagName( bookTagView.getTagName() );
		return bookTag;
	}
}
