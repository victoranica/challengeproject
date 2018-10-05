package com.victoranica.myproject.server;

import com.victoranica.myproject.client.Customer;
import com.victoranica.myproject.client.CustomerService;
import com.victoranica.myproject.shared.FieldVerifier;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class CustomerServiceImpl extends RemoteServiceServlet implements CustomerService {

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}

	/**
	 * Adds a new customer to the server collection
	 * 
	 * @param customer the customer to be added 
	 * @return the string containing added customer's details
	 */
	@Override
	public String addCustomer(Customer customer) throws IllegalArgumentException {

		HashMap<String, Customer> customers = FieldVerifier.customers;
		String email = customer.getCustomerEmail();

		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(customer.getCustomerName())) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException("Name must not be empty");
		}

		if (!FieldVerifier.isValidEmail(email)) {
			throw new IllegalArgumentException("Email must not be null and must be in form of localpart@domain.com");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		//		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		if (customers.containsKey(email)) {
			Customer existingCust = customers.get(email);
			existingCust.setCustomerName(customer.getCustomerName());

			return escapeHtml(returnCustomerData("updated", existingCust));
		}
		else 
		{
			customer.generateId();
			customers.put(email, customer);

			return escapeHtml(returnCustomerData("added", customer));
		}
	}

	/**
	 * Displays the customers in the collection on the server
	 * 
	 * @return the string containing the details for every added customer so far
	 */
	public String showCustomers() throws IllegalArgumentException {

		StringBuilder output = new StringBuilder();
		
		if (FieldVerifier.customers.size() == 0)
			return output.append("There is no stored customer").toString();

		Iterator<Entry<String, Customer>> it = FieldVerifier.customers.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Customer> pair = (Map.Entry<String, Customer>)it.next();
			output.append(pair.getValue()).append("\n");
		}

		return output.toString();
	}


	private String returnCustomerData(String addedOrUpdated, Customer customer) {
		return "\nCustomer has been " + addedOrUpdated + " successfully !\n\n" + 
				customer;
	}
}
