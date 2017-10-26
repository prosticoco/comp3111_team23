package src.main.java.com.example.bot.spring;

public interface StorageEngine{
	
	public String getFAQResponse(String quesion) throws Exception;
	
	public Tour getTourDetails(String identifier);
	
	public Customer getCustomerDetails(String identifier);

	public void addEntry(String question);
}
