package com.victoranica.myproject.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>CustomerService</code>.
 */
public interface CustomerServiceAsync {
	void addCustomer(Customer customer, AsyncCallback<String> callback);
	void showCustomers(AsyncCallback<String> callback) throws IllegalArgumentException;
}
