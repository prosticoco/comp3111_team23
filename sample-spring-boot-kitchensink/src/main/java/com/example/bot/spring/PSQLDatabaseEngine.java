package com.example.bot.spring;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PSQLDatabaseEngine implements StorageEngine{


	@Override
	public Tour getTourDetails(String identifier, Date date) throws Exception {
		int price = 0;
		String tourGuide = null;
		String tourGuideLineAcc = null;
		String hotel = null;
		int capacity = 0;
		int minCustomers = 0;
		try{
			Connection con = getConnection();
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM tour WHERE id LIKE ? AND date = ?");
			stmt.setString(1, identifier);
			stmt.setDate(2, new java.sql.Date(date.getTime()));
			ResultSet rs = stmt.executeQuery();			
			if(rs.next()){
				tourGuide = rs.getString("tour_guide");
				tourGuideLineAcc = rs.getString("guide_line");
				hotel =  rs.getString("hotel");
				price = rs.getInt("price");
				capacity = rs.getInt("capacity");
				minCustomers = rs.getInt("min_customer");
			}
			rs.close();
			con.close();
			stmt.close();		
		} catch (URISyntaxException e){
			//log.info("The wrong URI has been provided", e.toString());
		} catch (SQLException e){
			//log.info("There has been an error with the SQL statement", e.toString());
		}
		if(tourGuide != null && hotel != null && price != 0){
			return new Tour(identifier,date,price,tourGuide,tourGuideLineAcc,hotel,capacity,minCustomers);
		}
		throw new Exception("TOUR NOT FOUND");
	}

	@Override
	public Customer getCustomerDetails(String identifier) throws Exception {
		String name = null;
		int phone = 0;
		int age = 0;
		try{
			Connection con = getConnection();
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM customer WHERE id LIKE ?");
			stmt.setString(1, identifier);
			ResultSet rs = stmt.executeQuery();			
			if(rs.next()){
				name = rs.getString("name");
				phone = rs.getInt("phone_number");
				age =  rs.getInt("age");
			}
			rs.close();
			con.close();
			stmt.close();		
		} catch (URISyntaxException e){
			//log.info("The wrong URI has been provided", e.toString());
		} catch (SQLException e){
			//log.info("There has been an error with the SQL statement", e.toString());
		}
		if( name != null && age != 0){
			return new Customer(name,identifier,phone,age);
		}
		throw new Exception("CUSTOMER NOT FOUND");
	}
	
	@Override
	public GeneralTour getGeneralTourDetails(String name) throws Exception {
		String id = null;
		String description = null;
		String days = null;
		try{
			Connection con = getConnection();
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM generaltour WHERE name LIKE ?");
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();	
			if(rs.next()){
				id = rs.getString("id");
				description = rs.getString("description");
				days =  rs.getString("days_available");
			}
			rs.close();
			con.close();
			stmt.close();		
		} catch (URISyntaxException e){
			//log.info("The wrong URI has been provided", e.toString());
		} catch (SQLException e){
			//log.info("There has been an error with the SQL statement", e.toString());
		}
		if( id != null && days != null){
			return new GeneralTour(id,name,description,days);
		}
		throw new Exception("GENERAL TOUR NOT FOUND");
	}
	
	@Override
	public int getNumberBookedTours(Tour tour) {
		int availability = 0;
		try{
			Connection con = getConnection();
			PreparedStatement stmt = con.prepareStatement("SELECT customerID FROM booking WHERE tourid LIKE ?");
			stmt.setString(1, tour.getId());
			ResultSet rs = stmt.executeQuery();			
			if(rs.last()){
				availability = rs.getRow();
			}
			rs.close();
			con.close();
			stmt.close();		
		} catch (URISyntaxException e){
			//log.info("The wrong URI has been provided", e.toString());
		} catch (SQLException e){
			//log.info("There has been an error with the SQL statement", e.toString());
		}
		return availability;
	}

	@Override
	public String getFAQResponse(String text) throws Exception {
		String result = null;
		try{
			Connection con = getConnection();
			String key = text.toLowerCase();
			PreparedStatement stmt = con.prepareStatement("SELECT answer FROM frequentquestions WHERE question LIKE ?");
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
		throw new Exception("FAQ ANSWER NOT FOUND");
	}
	
	@Override
	public void logQuestion(String question){
		try{
			Connection con = getConnection();
			String query = "INSERT INTO loggedquestions VALUES(?);";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, question);
			stmt.execute();
			stmt.close();
			con.close();
		} catch(URISyntaxException e){
			//log.info("The wrong URI has been provided", e.toString());
		} catch (SQLException e){
			//log.info("There has been an error with the SQL statement", e.toString());
		}
	}
	
	@Override
	public void addBooking(TourBooking tourBooking) throws URISyntaxException{
			try {
				Connection con = getConnection();
				String query = "INSERT INTO booking VALUES(?,?,?,?,?,?,?);";
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setString(1,tourBooking.getCustomer().getId());
				stmt.setString(2,tourBooking.getTour().getId());
				stmt.setInt(3, tourBooking.getAdultsNumber());
				stmt.setInt(4, tourBooking.getChildrenNumber());
				stmt.setInt(5, tourBooking.getToddlersNumber());
				stmt.setInt(6, tourBooking.getPaid());
				stmt.setInt(7, tourBooking.calcTourFee());
				//stmt.setString(8, tourBooking.getSpecialRequests());
				stmt.execute();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void addCustomer(Customer customer) throws URISyntaxException {
		try{
			Connection con = getConnection();
			String query = "INSERT INTO customer VALUES(?,?,?,?);";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1,customer.getId());
			stmt.setString(2, customer.getName());
			stmt.setInt(3, customer.getAge());
			stmt.setInt(4, customer.getPhone());
			stmt.execute();
			stmt.close();
			con.close();
		} catch (SQLException e){
			//log.info("There has been an error with the SQL statement", e.toString());
		}
	}

	
	private Connection getConnection() throws URISyntaxException, SQLException {
		Connection connection;
		URI dbUri = new URI(System.getenv("DATABASE_URL"));

		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() +  "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";

		//log.info("Username: {} Password: {}", username, password);
		//log.info ("dbUrl: {}", dbUrl);
		
		connection = DriverManager.getConnection(dbUrl, username, password);

		return connection;
	}

	@Override
	public ArrayList<Date> getTourDates(String identifier) {
		ArrayList<Date> dates = null;
		try{
			Connection con = getConnection();
			PreparedStatement stmt = con.prepareStatement("SELECT date FROM tour WHERE id LIKE ?");
			stmt.setString(1, identifier.toLowerCase());
			ResultSet rs = stmt.executeQuery();	
			while(rs.next()){
				dates.add(rs.getDate("date"));
			}
			rs.close();
			con.close();
			stmt.close();		
		} catch (URISyntaxException e){
			//log.info("The wrong URI has been provided", e.toString());
		} catch (SQLException e){
			//log.info("There has been an error with the SQL statement", e.toString());
		}
		assert !dates.isEmpty();	
		return dates;
	}

	@Override
	public ArrayList<String> getNotPaidCustomers(Date date) throws Exception{
		ArrayList<String> users = new ArrayList<>();
		ArrayList<String> tourIds = null;
		try{
			
			tourIds = getTourIds(date);
			Connection con = getConnection();
			if(tourIds != null)
			for(String s: tourIds){
				PreparedStatement stmt = con.prepareStatement("SELECT customerID FROM booking WHERE paid = 0 and tourID like 1");
				stmt.setString(1, s.toLowerCase());
				ResultSet rs = stmt.executeQuery();	
				if(rs.next()){
					users.add(rs.getString("customerid"));
				}
				rs.close();
				stmt.close();		
			}
			
			con.close();
		} catch (URISyntaxException e){
			//log.info("The wrong URI has been provided", e.toString());
		} catch (SQLException e){
			//log.info("There has been an error with the SQL statement", e.toString());
		}
	
		return users;
	}

	private ArrayList<String> getTourIds(Date date) throws Exception{
		ArrayList<String> tourIds = new ArrayList<>();
		try{
			Connection con = getConnection();
			PreparedStatement stmt = con.prepareStatement("SELECT id FROM tour WHERE AND date = ?");
			stmt.setDate(1, new java.sql.Date(date.getTime()));
			ResultSet rs = stmt.executeQuery();			
			while(rs.next()){
				tourIds.add(rs.getString("id"));
			}
			rs.close();
			con.close();
			stmt.close();		
		} catch (URISyntaxException e){
			//log.info("The wrong URI has been provided", e.toString());
		} catch (SQLException e){
			//log.info("There has been an error with the SQL statement", e.toString());
		}
		if(tourIds.isEmpty())
			throw new Exception("There is no tours on this date");		
		
		return tourIds;
	}
	
	
	
	
	
}