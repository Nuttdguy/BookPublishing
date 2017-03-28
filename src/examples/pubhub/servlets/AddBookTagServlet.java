package examples.pubhub.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.pubhub.dao.BookTagsDAO;
import examples.pubhub.model.BookTags;
import examples.pubhub.utilities.DAOUtilities;

@WebServlet("/AddBookTag")
public class AddBookTagServlet extends HttpServlet  {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isSuccess;
		String isbn13 = request.getParameter("isbn13");
		
		// Get book tag by ISBN
		BookTagsDAO dao = DAOUtilities.getBookTagsDAO();
		BookTags tag = dao.getBookTagByISBN(isbn13);
		
		if (tag != null) {
			// if tag exists, get parameters
			tag.setTagName(request.getParameter("tag_name"));
			tag.setIsbn13(request.getParameter("isbn13"));
			
			request.setAttribute("bookTags", tag);
			isSuccess = dao.addBookTag(tag);
			
			// TODO return to complete Logic, feature does not make sense
		} else {
			isSuccess = false;
		}
		
		if(isSuccess) {
			request.getSession().setAttribute("message", "Book tag successfully added");
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect("BookPublishing");
		} else {
			request.getSession().setAttribute("message", "There was a problem adding tag to book");
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("bookTags.jsp").forward(request, response);
		}
		
		
	}

}
