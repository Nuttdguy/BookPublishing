package examples.pubhub.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import examples.pubhub.dao.BookTagDAO;
import examples.pubhub.model.BookTag;
import examples.pubhub.utilities.DAOUtilities;

@WebServlet("/AddBookTag")
public class AddBookTagServlet extends HttpServlet  {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		BookTag tag = new BookTag();
		tag.setIsbn13(request.getParameter("isbn13"));
		
		request.setAttribute("bookTags", tag);	
		request.getRequestDispatcher("addTag.jsp").forward(request, response);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isSuccess;
		
		// Get book tag by ISBN
		BookTagDAO dao = DAOUtilities.getBookTagsDAO();
		BookTag tag = new BookTag();
		
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
