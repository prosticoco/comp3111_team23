package com.example.bot.spring;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimerTask;

/**
 * This class is responsible for reminding customers to pay for their bookings and inform them in case a tour has been cancelled 
 * @author Oliver Thompson
 *
 */
class CustomerChecker extends TimerTask{
	
	
	private StorageEngine database = new PSQLDatabaseEngine();
	private LineCommunicator lineCom = new LineCommunicator();
	/**
	 * this method is called whenever the timer triggers (timer defined in the controller class)
	 */
	@Override
	public void run() {
		Calendar newDate = setDate();
		remindCustomers(newDate);
		informCustomers(newDate);
	}
	
	/**
	 * this method informs customers that a tour has been cancelled
	 * @param date 
	 */
	private void informCustomers(Calendar date){
		ArrayList<String> customers = new ArrayList<>();
		try {
//			customers = database.getCancelledTourCustomers(date.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		String reminder = "Please be reminded that you have booked a tour which is not paid yet. Please pay as soon as possible to reserve your seat.";
		lineCom.pushCustomerNotification(customers, reminder);
	}
	
	/**
	 * This method is called to remind customers that they have to pay for a tour
	 * @param date
	 */
	private void remindCustomers(Calendar date){
		ArrayList<String> customers = new ArrayList<>();
		try {
			//get the userID of the customers that need to pay
			customers = database.getNotPaidCustomers(date.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		String reminder = "Please be reminded that you have booked a tour which is not paid yet. Please pay as soon as possible to reserve your seat.";
		lineCom.pushCustomerNotification(customers, reminder);
	}
	/**
	 * this method returns a instance of calendar with the date that is 3 days later
	 * @return an instance of calendar with the date being 3 days ahead of the current
	 */
	private Calendar setDate() {
		Calendar newDate = Calendar.getInstance();
		//get todays date
		int date = newDate.get(Calendar.DAY_OF_MONTH);
		
		
		//get the maximum number of days in the current month
		int maxDaysInMonth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
		
		
		//set the date to point to the current date + 3 more days when the deadline is
		newDate.set(Calendar.DAY_OF_MONTH, (date+3) % maxDaysInMonth);
		
		
		//change the month if it is the end of the month
		if((date+3) > maxDaysInMonth)
			newDate.set(Calendar.MONTH, newDate.get(Calendar.DAY_OF_MONTH) + 1);
		
		return newDate;
	}
}