package examples.pubhub.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.pubhub.dao.BookTagDAO;
import examples.pubhub.model.view.BookTagView;
import examples.pubhub.service.BookTagViewService;
import examples.pubhub.utilities.DAOUtilities;

@WebServlet("/UpdateBookTag")
public class UpdateBookTagServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get isbn from request dispatcher
		String isbn = request.getParameter("isbn13");
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String tagName = request.getParameter("tagName");
		
		// create connection dao instance
		BookTagViewService tagViewService = DAOUtilities.getBookTagService();
		
		// call dao, get data for isbn
		BookTagView tag = tagViewService.getBookTagViewByTagName(tagName);
		// BookTagView tag = tagViewService.getBookTagViewByTitle(title);
		
		if (tag != null) {
			tag.setAuthor(author);
			tag.setIsbn13(isbn);
			tag.setTitle(title);
		}
		
		// set request attribute of dispatcher
		request.setAttribute("bookTagView", tag);
		
		// get .jsp object from request dispatcher, forward request/response
		request.getRequestDispatcher("updateBookTag.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isSuccess = false;
		String isbn13 = request.getParameter("isbn13");
		String title = request.getParameter("title");
		String tagId = request.getParameter("bookTagId");
		//String tagName = request.getParameter("tagName");
		
		BookTagViewService tagViewService = DAOUtilities.getBookTagService();
		BookTagView tag = tagViewService.getBookTagViewByBookTagId(tagId);
		// BookTagView tag = tagViewService.getBookTagViewByTitle(title);
		
		if (tag!= null) {
			
			tag.setAuthor(request.getParameter("author"));
			tag.setTitle(request.getParameter("title"));
			tag.setIsbn13(request.getParameter("isbn13"));
			tag.setTagName(request.getParameter("tagName"));
			
			request.setAttribute("bookTags", tag);
			isSuccess = tagViewService.updateBookTagView(tag);
		} else {
			// couldn't find book with isbn. Update failed
			tag = new BookTagView();
			tag.setAuthor(request.getParameter("author"));
			tag.setTitle(request.getParameter("title"));
			tag.setIsbn13(request.getParameter("isbn13"));
			tag.setTagName(request.getParameter("tagName"));
			
			request.setAttribute("bookTags", tag);
			isSuccess = tagViewService.addBookTagView(tag);
		}
		
		if (isSuccess) {
			request.getSession().setAttribute("message", "Book successfully updated");
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect("BookTagView?title=" + title);
		} else {
			request.getSession().setAttribute("message", "Tag has been added to book");
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("viewBookTags.jsp").forward(request, response);
		}
		
	}

}
