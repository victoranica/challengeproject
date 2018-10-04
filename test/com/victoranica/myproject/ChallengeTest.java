package com.victoranica.myproject;

import org.apache.tools.ant.taskdefs.Javadoc.Html;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwtmockito.GwtMock;
import com.google.gwtmockito.GwtMockitoTestRunner;
import com.victoranica.myproject.client.Customer;
import com.victoranica.myproject.client.CustomerServiceAsync;
import com.victoranica.myproject.client.MyProject;

@RunWith(GwtMockitoTestRunner.class)
public class ChallengeTest {

	private RootPanel rootPanel;

//	private SampleWidget sampleWidget;

	
	private Button button;
	
	@Mock
	private TextBox textField;
	@Mock
	private TextBox emailField;

//	@GwtMock
//	private Label label;

//	@GwtMock
//	private Element element;

//	@GwtMock
//	private HTML html;

	@GwtMock
	private CustomerServiceAsync sampleApplicationService;
	
	@GwtMock
	private HTML serverResponseLabel;

	private ClickHandler clickHandler;
	
	  @Mock
	  private CustomerServiceAsync customerServiceAsync;
	  
	  @Mock
	  Customer customer;

	@Before
	public void setUp() {
		Mockito.doAnswer(new Answer() {
			public Object answer(InvocationOnMock invocation) throws Throwable {
				String msg = (String)invocation.getArguments()[0];
				AsyncCallback<String>  callback = (AsyncCallback<String>) invocation.getArguments()[1];
				callback.onSuccess("Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"");
				return null;
			}
		}).when(sampleApplicationService)
		.addCustomer(Mockito.any(Customer.class), (AsyncCallback<String>) Mockito.any());

		Mockito.when(button.addClickHandler(Mockito.any(ClickHandler.class))).thenAnswer(new Answer() {
			public Object answer(InvocationOnMock aInvocation) throws Throwable {
				clickHandler = (ClickHandler) aInvocation.getArguments()[0];
				return null;
			}
		});

//		verify(customerServiceAsync).someMethod(contains("foo"))
		Mockito.when(textField.getText()).thenReturn("Smith");
		Mockito.when(emailField.getText()).thenReturn("smith@yahoo.com");

	}

	@Test
	public void test() {
	    Mockito.verify(customerServiceAsync).addCustomer(customer, Mockito.any(AsyncCallback.class));

	}
	@Test
	public void clickOnButton() {
		clickHandler.onClick(new ClickEvent(){});
		Mockito.verify(serverResponseLabel).setHTML("Name: Smith; Email: smith@yahoo.com; Customer ID: ");
	}
}


