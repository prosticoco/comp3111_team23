package src.main.java.com.example.bot.spring;

public class TourBooking {
	
	private int adultsNumber=0;
	private int childrenNumber=0;
	private int toddlersNumber=0;
	private int tourFee=0;
	private int paid=0;
	private String specialRequests=null;
	private Tour tour;
	private Customer customer;
	
	public TourBooking(Tour tour, Customer customer){
		this.tour = tour;
		this.customer = customer;
	}

	public int getAdultsNumber() {
		return adultsNumber;
	}

	public void setAdultsNumber(int adultsNumber) {
		this.adultsNumber = adultsNumber;
	}

	public int getChildrenNumber() {
		return childrenNumber;
	}

	public void setChildrenNumber(int childrenNumber) {
		this.childrenNumber = childrenNumber;
	}

	public int getToddlersNumber() {
		return toddlersNumber;
	}

	public void setToddlersNumber(int toddlersNumber) {
		this.toddlersNumber = toddlersNumber;
	}

	public int getTourFee() {
		return tourFee;
	}

	public void setTourFee(int tourFee) {
		this.tourFee = tourFee;
	}

	public int getPaid() {
		return paid;
	}

	public void setPaid(int paid) {
		this.paid = paid;
	}

	public String getSpecialRequests() {
		return specialRequests;
	}

	public void setSpecialRequests(String specialRequests) {
		this.specialRequests = specialRequests;
	}

	public Tour getTour() {
		return tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	

}
