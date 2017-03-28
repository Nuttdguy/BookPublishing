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
import examples.pubhub.utilities.DAOUtilities;

@WebServlet("/ViewBookTags")
public class ViewBookTagsServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		// Grab the list of Book Tags from the Database
		BookTagsDAO dao = DAOUtilities.getBookTagsDAO();
		List<BookTags> tagList = dao.getAllBookTags();		
		
		// Populate the list into a variable that will be stored in the session =
		request.getSession().setAttribute("bookTags", tagList);
		
		// Use dispatcher, get jsp view and forward request/response to client
		request.getRequestDispatcher("bookTagsHome.jsp").forward(request, response);
		// TODO create bookTagsHome.jsp template
		
	}

}
