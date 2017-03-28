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

@WebServlet("/UpdateBookTag")
public class UpdateBookTagServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isSuccess = false;
		String isbn13 = request.getParameter("isbn13");
		
		BookTagsDAO dao = DAOUtilities.getBookTagsDAO();
		BookTags tag = dao.getBookTagByISBN(isbn13);
		
		if (tag!= null) {
			
			tag.setIsbn13(request.getParameter("isbn13"));
			tag.setTagName(request.getParameter("tagName"));
			
			request.setAttribute("bookTags", tag);
			isSuccess = dao.updateBookTag(tag);
		} else {
			// couldn't find book with isbn. Update failed
			isSuccess = false;
		}
		
		if (isSuccess) {
			request.getSession().setAttribute("message", "Book successfully updated");
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect("ViewBookTags?isbn13=" + isbn13);
		} else {
			request.getSession().setAttribute("message", "There was a problem updating this book");
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("ViewBookTags.jsp").forward(request, response);
		}
		
	}

}
