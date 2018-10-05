package com.victoranica.myproject.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("customer")
public interface CustomerService extends RemoteService {
	String addCustomer(Customer customer) throws IllegalArgumentException;
	String showCustomers() throws IllegalArgumentException;
}
