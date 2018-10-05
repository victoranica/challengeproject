package com.victoranica.myproject.shared;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.victoranica.myproject.client.Customer;

/**
 * <p>
 * FieldVerifier validates that the name the user enters is valid.
 * </p>
 * <p>
 * This class is in the <code>shared</code> package because we use it in both
 * the client code and on the server. On the client, we verify that the name is
 * valid before sending an RPC request so the user doesn't have to wait for a
 * network round trip to get feedback. On the server, we verify that the name is
 * correct to ensure that the input is correct regardless of where the RPC
 * originates.
 * </p>
 * <p>
 * When creating a class that is used on both the client and the server, be sure
 * that all code is translatable and does not use native JavaScript. Code that
 * is not translatable (such as code that interacts with a database or the file
 * system) cannot be compiled into client-side JavaScript. Code that uses native
 * JavaScript (such as Widgets) cannot be run on the server.
 * </p>
 */
public class FieldVerifier {
	
	//since customer's emails are unique, we could identify a customer by email, 
	//therefore we can use a map whose key is the email and value is the whole customer object
	public static HashMap<String, Customer> customers = new HashMap<>();

	/**
	 * Verifies that the specified name is valid for our service.
	 * 
	 * @param name the name to validate
	 * @return true if valid, false if invalid
	 */
	public static boolean isValidName(String name) {
		if (name == null || name.equals("")) {
			return false;
		}
		return true;
	}
	
	/**
	 * Verifies that the specified email address is valid for our service.
	 * 
	 * The email address must not be empty and must be in a format of local part
	 * at domain dot com 
	 * 
	 * @param email the email address to validate
	 * @return true if valid, false if invalid
	 */
	public static boolean isValidEmail(String email) {
		if (email == null) {
			return false;
		}
		
		//using a regex to validate the email address correctness
		RegExp regExp = RegExp.compile("^[-a-z0-9~!$%^&*_=+}{\\'?]+(\\.[-a-z0-9~!$%^&*_=+}{\\'?]+)*@([a-z0-9_][-a-z0-9_]*(\\.[-a-z0-9_]+)*\\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})?$");
		MatchResult matcher = regExp.exec(email);
		boolean matchFound = matcher != null;

		if (!matchFound)
			return false;
		return true;
	}
	
	public static boolean areThereAnyClients() {
		return !customers.isEmpty();
	}
	
}
