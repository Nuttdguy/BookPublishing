package examples.pubhub.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import examples.pubhub.dao.BookDAO;
import examples.pubhub.dao.BookDAOImpl;
import examples.pubhub.dao.BookTagDAO;
import examples.pubhub.dao.BookTagDAOImpl;
import examples.pubhub.service.BookTagViewService;
import examples.pubhub.service.BookTagViewServiceImpl;

/**
 * Class used to retrieve DAO Implementations. Serves as a factory. Also manages a single instance of the database connection.
 */
public class DAOUtilities {

	private static final String CONNECTION_USERNAME = "pubhub_user";
	private static final String CONNECTION_PASSWORD = "pubhub_password";
	private static final String URL = "jdbc:postgresql://localhost:5432/pubhub";
	private static Connection connection;
	
	public static synchronized Connection getConnection() throws SQLException {
		if (connection == null) {
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("Could not register driver!");
				e.printStackTrace();
			}
			connection = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
		}
		
		//If connection was closed then retrieve a new connection
		if (connection.isClosed()){
			System.out.println("Opening new connection...");
			connection = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
		}
		return connection;
	}
	
	public static BookDAO getBookDAO() {
		return new BookDAOImpl();
	}
	
	public static BookTagDAO getBookTagsDAO() {
		return new BookTagDAOImpl();
	}
	
	public static BookTagViewService getBookTagService() {
		return new BookTagViewServiceImpl();
	}
}
