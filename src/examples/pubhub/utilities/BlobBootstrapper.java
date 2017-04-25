package examples.pubhub.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * This class is used to bootstrap the application. The included script will insert the first row, but the book file
 * will be empty. Running this class will update that empty file to a stock PDF, so all features of the site can be
 * reliably used without having to publish new books.
 * 
 * This class only needs to be run once, and should be run AFTER running the DB setup script.
 */
public class BlobBootstrapper {

	public static void main(String[] args) {
		
		Connection conn = null;
		String sql = "UPDATE Books SET content = ? WHERE isbn_13='1111111111111'";
		PreparedStatement stmt = null;
		InputStream is = null; 
		
		String createDbFile = "/database_files/pubhub-setup.sql";
		String sqlDb = null;
		BufferedReader br = null;
		
		try {
			br = new BufferedReader( new FileReader(createDbFile) );
			String currentLine;
			
			while ( (currentLine = br.readLine()) != null ) {
				currentLine += currentLine;
			}
			
			sqlDb = currentLine;
		} catch (IOException io) {
			io.printStackTrace();
		}
		
		File tempFile = new File("ws.pdf");
		System.out.println(tempFile.exists()?"The sample file exists":"The sample file does not exist! Did you delete \"ws.pdf\"?");
		
		try {
			is = new FileInputStream(tempFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			conn = DAOUtilities.getConnection();
			stmt = conn.prepareStatement(sql);
			
			stmt.setBinaryStream(1, is, tempFile.length() );
			
			System.out.println("Executing SQL Statement: " + sql);
			System.out.println("Connection is valid: " + conn.isValid(5));
			System.out.println("Rows updated: " + stmt.executeUpdate());
			System.out.println("Success!");
		} catch (SQLException e) {
			System.out.println("Failure!");
			e.printStackTrace();
		}
		
	}

}
