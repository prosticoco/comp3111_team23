package src.main.java.com.example.bot.spring;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PSQLDatabaseEngine implements StorageEngine{


	@Override
	public String getFAQResponse(String text) throws Exception {
		String result = null;
		try{
			Connection con = getConnection();
			String key = text.toLowerCase();
			PreparedStatement stmt = con.prepareStatement("SELECT response FROM simplemap WHERE keyword like ?");
			stmt.setString(1, key);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				result = rs.getString(1);
			}
			rs.close();
			con.close();
			stmt.close();		
		} catch (URISyntaxException e){
			//log.info("The wrong URI has been provided", e.toString());
		} catch (SQLException e){
			//log.info("There has been an error with the SQL statement", e.toString());
		}
		if (result != null)
			return result;
		throw new Exception("NOT FOUND");
	}
	
	@Override
	public Tour getTourDetails(String identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer getCustomerDetails(String identifier) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void addEntry(String question) {
		// TODO Auto-generated method stub
		
	}
	
	
	private Connection getConnection() throws URISyntaxException, SQLException {
		Connection connection;
		URI dbUri = new URI(System.getenv("DATABASE_URL"));

		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() +  "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";

		log.info("Username: {} Password: {}", username, password);
		log.info ("dbUrl: {}", dbUrl);
		
		connection = DriverManager.getConnection(dbUrl, username, password);

		return connection;
	}
}
