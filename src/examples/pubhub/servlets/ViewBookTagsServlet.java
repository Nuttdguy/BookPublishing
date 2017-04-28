package examples.pubhub.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.pubhub.dao.BookTagDAO;
import examples.pubhub.model.view.BookTagView;
import examples.pubhub.service.BookTagViewService;
import examples.pubhub.utilities.DAOUtilities;

@WebServlet("/BookTagView")
public class ViewBookTagsServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String isbn = request.getParameter("isbn13");
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String tag = request.getParameter("tagName");
		
		// Grab the list of Book Tags from the Database
		BookTagViewService tagViewService = DAOUtilities.getBookTagService();
		
		//  Pending "tag_name"; not being shown on viewing all book tags;
		List<BookTagView> tagList = tagViewService.getAllBookTagViewByTitle(title);
		BookTagView singleTag = new BookTagView();
		
		if (tagList.size() > 0) {
			singleTag = tagList.get(0);
		} else {
			singleTag.setAuthor(author);
			singleTag.setTitle(title);
			singleTag.setTagName(tag);
			singleTag.setIsbn13(isbn);
			tagList.add(singleTag);
		}
		
		// Populate the list into a variable that will be stored in the session =
		request.setAttribute("singleTag", singleTag);
		request.getSession().setAttribute("BookTagView", tagList);
		
		// Use dispatcher, get jsp view and forward request/response to client
		request.getRequestDispatcher("viewBookTags.jsp").forward(request, response);
		
	}	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Get the book tag ID
		String tagId = request.getParameter("tagId");
		String title = request.getParameter("title");
		
		// Create an instance of book tag DAO
		BookTagDAO dao = DAOUtilities.getBookTagsDAO();
		
		// Delete record
		boolean isSuccess = dao.deleteBookTagByBookTagId(tagId);
		
		if (isSuccess) {
			request.getSession().setAttribute("message", "Tag successfully deleted");
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect("BookPublishing");
		} else {
			request.getSession().setAttribute("message", "Tag has been deleted to book");
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("viewBookTags.jsp").forward(request, response);
		}
		
	}

}
