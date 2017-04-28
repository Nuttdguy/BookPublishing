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

	//==| verified data returned correctly from database
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BookTagViewService bkTagService = DAOUtilities.getBookTagService();
		List<BookTagView> bookList = bkTagService.getAllBookTagViewWithTag();

		// Populate the list into a variable that will be stored in the session
		request.getSession().setAttribute("books", bookList);
		
		request.getRequestDispatcher("bookPublishingHome.jsp").forward(request, response);
	}
}
