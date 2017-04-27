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
	
	@Override
	public BookTagView getViewBookTagByTitle(String title) {
		BookTagView bookView = new BookTagView();
		
		// Get book from BookDaoImpl
		
		// Get book_tag from BookTagDaoImpl
		
		return null;
	}

	@Override
	public BookTagView getViewBookTagByISBN(String isbn) {
		// TODO Auto-generated method stub
		return null;
	}
	
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
			bookTagViewList.add( EntityDTO( tag, book) );
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
			bookTagViewList.add( EntityDTO(tag, book) );
		}
		
		return bookTagViewList;
	}

	//==||  Section III   ||  Add New Record Methods
	//==================================================\\
		
	/*------------------------------------------------------------------------------------------*/
	
	@Override
	public boolean addViewBookTag(BookTagView viewBookTag) {
		// TODO Auto-generated method stub
		return false;
	}

	//==||  Section IV   ||  Update Record Methods
	//==================================================\\
		
	/*------------------------------------------------------------------------------------------*/
	
	@Override
	public boolean updateViewBookTag(BookTagView viewBookTag) {
		// TODO Auto-generated method stub
		return false;
	}

	//==||  Section V   ||  Delete Record Methods
	//==================================================\\
		
	/*------------------------------------------------------------------------------------------*/
	
	@Override
	public boolean deleteBookTagByTagName(String tagName) {
		// TODO Auto-generated method stub
		return false;
	}
	
	//==||  Section VI   ||  Misc. Methods
	//==================================================\\
		
	/*------------------------------------------------------------------------------------------*/
	
	private static BookTagView EntityDTO(BookTag tag, Book book) {
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
	
}
