package examples.pubhub.servlets;

import java.io.IOException;
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

@WebServlet("/ViewBookTags")
public class ViewBookTagsServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String isbn = request.getParameter("isbn13");
		
		// Grab the list of Book Tags from the Database
		BookTagsDAO dao = DAOUtilities.getBookTagsDAO();
		List<ViewBookTags> tag = dao.getAllBookTags();		
		//BookTags tag = dao.getBookTagByISBN(isbn);		
		
		// Populate the list into a variable that will be stored in the session =
		request.getSession().setAttribute("ViewBookTags", tag);
		
		// Use dispatcher, get jsp view and forward request/response to client
		request.getRequestDispatcher("viewBookTags.jsp").forward(request, response);
		
	}	

}