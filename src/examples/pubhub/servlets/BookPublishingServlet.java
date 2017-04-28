package examples.pubhub.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.pubhub.dao.BookDAO;
import examples.pubhub.model.view.BookTagView;
import examples.pubhub.service.BookTagViewService;
import examples.pubhub.utilities.DAOUtilities;

/*
 * This servlet will take you to the homepage for the Book Publishing module (level 100)
 */
@WebServlet("/BookPublishing")
public class BookPublishingServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	// TODO (Corrected 04-28) tag is not being shown on tag list view; possibly the data is not being set once retrieved from database
	// TODO (Corrected 04-28) BUG -- Book Publishing Home View is showing many of the Same Records
	// TODO (Corrected 04-28) BUG -- Update view displaying incorrect tag, not getting back correct data
	// TODO (Corrected 04-28) Update is returning error, does not work -- need to capture by book_tag_id
	// TODO review all tag functionality;
	// 1. Displays all books records
	// 2. Displays all book tags
	// 3. Search for books by tag/keyword
	// 4. Publishing not allowed without completed form; allow to publish without content
	// 5. Can download content when available
	// 6. Can click through book details view
	// 7. Can update book, author, title and/or price in book details
	// 8. Can click through to view "tags" on publishing home
	// 9. Can update/edit/add tag from "UpdateBookTag" 
	// 10. Can add new tag from "BookTagView"
	// 11. Can delete "tag" from "BookTagView"
	// TODO (Reviewed 04-28) Review/check that book publishing home can search for book tags by tag_name !Important
	// TODO (Corrected 04-28) Correct delete functions for tags -- tag deletes successfully

	//==| verified data returned correctly from database
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BookTagViewService bkTagService = DAOUtilities.getBookTagService();
		List<BookTagView> bookList = bkTagService.getAllBookTagViewWithTag();

		// Populate the list into a variable that will be stored in the session
		request.getSession().setAttribute("books", bookList);
		
		request.getRequestDispatcher("bookPublishingHome.jsp").forward(request, response);
	}
}
