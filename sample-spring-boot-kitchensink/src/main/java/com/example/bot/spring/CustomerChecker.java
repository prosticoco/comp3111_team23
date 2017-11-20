package com.example.bot.spring;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimerTask;


class CustomerChecker extends TimerTask{
	
	
	private StorageEngine database = new PSQLDatabaseEngine();
	private LineCommunicator lineCom = new LineCommunicator();

	@Override
	public void run() {
		Calendar newDate = setDate();
		remindCustomers(newDate);
		informCustomers(newDate);
	}
	
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