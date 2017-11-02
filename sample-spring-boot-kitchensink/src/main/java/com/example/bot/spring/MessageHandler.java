package src.main.java.com.example.bot.spring;


import java.util.ArrayList;

public class MessageHandler {
		
	private StorageEngine database;
	private Customer customer;
	private TourBooking booking;
	boolean cusNulls=true, bookNulls=true;
	
	public MessageHandler() {
		database = new PSQLDatabaseEngine();
		customer = new Customer(null,null,0,0);
		booking = new TourBooking(null, customer);
	}
	
	
	public String handleTextContent(ArrayList<String> inputArray) throws Exception{
		String intent = inputArray.get(0).toLowerCase();
		
		//return default string if not found
		String answer = "Excuse me I cannot understand what you are trying to say. Could you try again?";
		if(intent.length()>=7){
			switch(intent.substring(intent.length() - 7).toLowerCase()){
				case "question":
					answer = getAsnwer(inputArray.get(0).substring(0,intent.length() - 7));
					break;
				case "booking":
					answer = handleBookingIntent(inputArray);
					break;
				case "confirmation":
					answer = completeBooking();
			}
		}
		return answer;      
   }
	
	private String handleBookingIntent(ArrayList<String> inputArray) throws Exception {
		
		String[] temp;		
		
		for(int i = 1; i< inputArray.size(); i++){
			temp = inputArray.get(i).split(":");
			if(checkCustomer(temp,customer)) continue;
			else if(checkBooking(temp,booking)) continue;
			else throw new Exception("attribute not found");
		}
		
		String answer = "Please provide details about: ";
		
		if(booking.nullValues().size()>0){
			for(String s: booking.nullValues()){
				answer += s;
			}
		}
		else cusNulls = false;
			
		if(customer.nullValues().size()>0){
			for(String s: customer.nullValues()){
				answer += s;
			}
		}
		else bookNulls = false;
		
		
		if(!cusNulls && !bookNulls) answer = "Are you sure you want to make this booking? Press Y";		
		
		
		return answer;
	}


	private String completeBooking() {
		String answer = "Sorry I could not complete the booking"; 
		
		if(!cusNulls && !bookNulls) {
			try {
				database.addBooking(booking);
				answer = "Your booking is complete";
			} catch (Exception e){
				
			}
		}
		
		return answer;
	}


	private boolean checkCustomer(String[] attributes, Customer customer) {
		boolean successful = false;
		
		//TODO: input validate everything 

		switch(attributes[0]){
			case "encyclopedia":
				customer.setName(attributes[1]);
				successful = true;
				break;
			case "number":
				customer.setAge(Integer.parseInt(attributes[1]));
				successful = true;
				break;
			case "phone":
				customer.setPhone(Integer.parseInt(attributes[1]));
				successful = true;
				break;
		}
		return successful;
	}


	private boolean checkBooking(String[] attributes, TourBooking booking) {
		boolean successful = false;
		
		//TODO: input validate everything 
		
		switch(attributes[0]){
			case "adultsNumber":
				booking.setAdultsNumber(Integer.parseInt(attributes[1]));
				successful = true;
				break;
			case "childrenNumber":
				booking.setChildrenNumber(Integer.parseInt(attributes[1]));
				successful = true;
				break;
			case "toddlersNumber":
				booking.setToddlersNumber(Integer.parseInt(attributes[1]));
				successful = true;
				break;
			/*case "specialRequests":
				booking.setSpecialRequests(attributes[1]);
				successful = true;
				break;*/
		}
		return successful;
	}


	private String getAsnwer(String question) {
		String answer = "Excuse me, I do not have an answer for your question." +
							"We have sent it to our staff. Please wait for them to respond";
		try {
			answer = database.getFAQResponse(question);
		} catch (Exception e) {
			database.logQuestion(question);
		}
		return answer;
	}

}
