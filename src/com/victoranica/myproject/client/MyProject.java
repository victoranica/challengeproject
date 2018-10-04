package com.victoranica.myproject.client;

import com.victoranica.myproject.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MyProject implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network " + "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Customer service.
	 */
	private final CustomerServiceAsync customerService = GWT.create(CustomerService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final Button addButton = new Button("Add new customer");
		final Button showButton = new Button("Show existing customers");
		final TextBox nameField = new TextBox();
		final TextBox emailField = new TextBox();

		nameField.setText("GWT User");
		final Label errorLabel = new Label();

		// add style names to widgets
		addButton.addStyleName("addButton");
		showButton.addStyleName("showButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("emailFieldContainer").add(emailField);
		RootPanel.get("addButtonContainer").add(addButton);
		RootPanel.get("showButtonContainer").add(showButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				addButton.setEnabled(true);
				addButton.setFocus(true);
				showButton.setEnabled(true);
				showButton.setFocus(true);
			}
		});

		// Create a handler for the sendButton and nameField
		class SendDataHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				sendCustDataToServer();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendCustDataToServer();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a response.
			 */
			private void sendCustDataToServer() {
				// First, we validate the input.
				errorLabel.setText("");
						
				String nameToServer = nameField.getText();
				String emailToServer = emailField.getText();
				
				nameField.setText("");
				emailField.setText("");
				
				Customer customer = new Customer(nameToServer, emailToServer);

				if (!FieldVerifier.isValidName(nameToServer)) {
					errorLabel.setText("Name must not be empty!");
					return;
				}
				if (!FieldVerifier.isValidEmail(emailToServer)) {
					errorLabel.setText("Email address field must not be empty and must be in form "
							+ "of local@domain! Example: jsmith@example.com");
					return;
				}

				// Then, we send the input to the server.
				addButton.setEnabled(false);
				
				serverResponseLabel.setText("");
				customerService.addCustomer(customer, new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						dialogBox.setText("Remote Procedure Call - Failure");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(SERVER_ERROR);
						dialogBox.center();
						closeButton.setFocus(true);
					}

					public void onSuccess(String result) {
						dialogBox.setText("Remote Procedure Call");
						serverResponseLabel.removeStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(new SafeHtmlBuilder().appendEscapedLines(result).toSafeHtml());
						dialogBox.center();
						closeButton.setFocus(true);
					}
				});
			}
		}
		
		class RetrieveDataHandler implements ClickHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				getCustDataFromServer();
			}

			/**
			 * Gets the customer list from the server.
			 */
			private void getCustDataFromServer() {
				// First, we validate the input.
				errorLabel.setText("");

//				if (!FieldVerifier.areThereAnyClients()) {
//					errorLabel.setText("There are no customers yet");
//					return;
//				}
				
				serverResponseLabel.setText("");
				showButton.setEnabled(false);

				customerService.showCustomers(new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						dialogBox.setText("Remote Procedure Call - Failure");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(SERVER_ERROR);
						dialogBox.center();
						closeButton.setFocus(true);
					}

					public void onSuccess(String result) {
						dialogBox.setText("Remote Procedure Call");
						serverResponseLabel.removeStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(new SafeHtmlBuilder().appendEscapedLines(result).toSafeHtml());
						dialogBox.center();
						closeButton.setFocus(true);
					}
				});
			}
		}

		// Add a handler to send the customer data to the server
		SendDataHandler sendHandler = new SendDataHandler();
		addButton.addClickHandler(sendHandler);
		nameField.addKeyUpHandler(sendHandler);
		
		// Add a handler to retrieve the customers from the server
		RetrieveDataHandler retrieveHandler = new RetrieveDataHandler();
		showButton.addClickHandler(retrieveHandler);
		

	}
}
