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
	// TODO review all tag functionality
	// TODO review/check that book publishing home can search for book tags by tag_name !Important

	//==| verified data returned correctly from database
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BookTagViewService bkTagService = DAOUtilities.getBookTagService();
		List<BookTagView> bookList = bkTagService.getAllBookTagViewWithTag();

		// Populate the list into a variable that will be stored in the session
		request.getSession().setAttribute("books", bookList);
		
		request.getRequestDispatcher("bookPublishingHome.jsp").forward(request, response);
	}
}
