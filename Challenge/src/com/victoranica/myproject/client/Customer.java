package com.victoranica.myproject.client;

import java.io.Serializable;

public class Customer implements Serializable{
	
	private static final long serialVersionUID = 3018519084873038173L;
	private String customerName;
	private String customerEmail;
	private int customerId;
	
	public Customer() {};

	public Customer(String customerName, String customerEmail) {
		this.customerName = customerName;
		this.customerEmail = customerEmail;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	/**
	 * id generation based on email's address hashcode; since emails are unique and hashcode
	 * method is overidden in String class, we are guaranteed unicity. 
	 */
	public void generateId() {
		int hashcode = getCustomerEmail().hashCode();
		if (hashcode < 0)
		    this.customerId = Integer.MAX_VALUE + hashcode; 
		else
			this.customerId = getCustomerEmail().hashCode();
	}
	
	public String toString() {
		return "Name: " + this.getCustomerName() + "; Email: " + this.getCustomerEmail() + "; Customer ID: " + 
				this.getCustomerId();
	}
}
