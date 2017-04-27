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
	BookDAO bookDao;
	BookTagDAO bookTagDao;
	
	//==||  Section I   ||  Retrieve Single Record Methods
	//==================================================\\
	
	/*------------------------------------------------------------------------------------------*/
	
//	@Override
//	public BookTagView getViewBookTagByTitle(String title) {
//		//  We need to retrieve records from BookDao and BookTagDao matching title
//		BookTagView bookView = new BookTagView();
//		
//		//  Get book from BookDaoImpl
//		Book book = bookDao.getBookByTitle(title);
//		
//		//  Get book_tag from BookTagDaoImpl using isbn_13
//		BookTag bookTag = bookTagDao.getBookTagByISBN( book.getIsbn13() );
//		
//		//  Transfer Query results to BookTagView
//		//  ## there may be "more than one" tag per title
//		BookTagView bkView = entityToBookTagViewDTO(bookTag, book);
//		
//		return bkView;
//	}
//
//	@Override
//	public BookTagView getViewBookTagByISBN(String isbn) {
//		return null;
//	}
	
	//==||  Section II   ||  Retrieve List Of Record Methods
	//==================================================\\
		
	/*------------------------------------------------------------------------------------------*/
	
	@Override
	public List<BookTagView> getAllViewBookTagByTitle(String title) {
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
	public List<BookTagView> getAllViewBookTagByISBN(String isbn) {
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
	public boolean addViewBookTag(BookTagView bookTagView) {
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
	public boolean updateViewBookTag(BookTagView bookTagView) {
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
	public boolean deleteBookTagByTagName(String tagName) {
		//  We need to single "tag" delete record from book_tag
		boolean result = false;
		
		//  Utilize BookTagDAOImpl to delete record from book_tag
		result = bookTagDao.deleteBookTagByTagName(tagName);
		
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
		bkView.setTagName( tag.getTagName() );
		bkView.setBookTagId( tag.getBookTagId() );
		
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
		bookTag.setIsbn13( bookTagView.getIsbn13() );
		bookTag.setTagName( bookTagView.getTagName() );
		return bookTag;
	}
}
