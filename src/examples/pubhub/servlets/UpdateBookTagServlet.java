package examples.pubhub.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.pubhub.dao.BookTagsDAO;
import examples.pubhub.model.BookTags;
import examples.pubhub.modelview.ViewBookTags;
import examples.pubhub.utilities.DAOUtilities;

@WebServlet("/EditBookTag")
public class UpdateBookTagServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get isbn from request dispatcher
		// String isbn = request.getParameter("isbn13");
		String title = request.getParameter("title");
		
		// create connection dao instance
		BookTagsDAO dao = DAOUtilities.getBookTagsDAO();
		
		// call dao, get data for isbn
		ViewBookTags tag = dao.getOneViewBookTagByTitle(title);
		
		// set request attribute of dispatcher
		request.setAttribute("viewBookTags", tag);
		
		// get .jsp object from request dispatcher, forward request/response
		request.getRequestDispatcher("editBookTag.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isSuccess = false;
		String isbn13 = request.getParameter("isbn13");
		String title = request.getParameter("title");
		
		BookTagsDAO dao = DAOUtilities.getBookTagsDAO();
		// ViewBookTags tag = dao.getOneViewBookTagByTitle(title);
		ViewBookTags tag = dao.getOneViewBookTagByISBN(isbn13);
		
		if (tag!= null) {
			
			tag.setAuthor(request.getParameter("author"));
			tag.setTitle(request.getParameter("title"));
			tag.setIsbn13(request.getParameter("isbn13"));
			tag.setTagName(request.getParameter("tagName"));
			
			request.setAttribute("bookTags", tag);
			isSuccess = dao.updateViewBookTag(tag);
		} else {
			// couldn't find book with isbn. Update failed
			isSuccess = false;
		}
		
		if (isSuccess) {
			request.getSession().setAttribute("message", "Book successfully updated");
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect("ViewBookTags?title=" + title);
		} else {
			request.getSession().setAttribute("message", "There was a problem updating this book");
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("ViewBookTags.jsp").forward(request, response);
		}
		
	}

}
