package examples.pubhub.servlets;

import java.io.IOException;
import java.sql.SQLException;

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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		BookTagsDAO dao = DAOUtilities.getBookTagsDAO();	
		BookTags tag = new BookTags();
		tag.setIsbn13(request.getParameter("isbn13"));
		
		request.setAttribute("bookTags", tag);	
		request.getRequestDispatcher("addTag.jsp").forward(request, response);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isSuccess;
		
		// Get book tag by ISBN
		BookTagsDAO dao = DAOUtilities.getBookTagsDAO();
		BookTags tag = new BookTags();
		
		tag.setTagName(request.getParameter("tagName"));
		tag.setIsbn13(request.getParameter("isbn13"));
		
		request.setAttribute("bookTags", tag);
		isSuccess = dao.addBookTag(tag);

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
