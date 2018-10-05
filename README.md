# challengeproject


I. LOCAL DEPLOYMENT: 

Prerequisites: You must have a local servlet container (Apache Tomcat / Jetty / Wildfly etc.) In this example we will stick to 
Apache Tomcat

1. Check out the project from the repository

2. Inside the war folder of the app, copy everything and archive everything (except the war folder itself) into a Challenge.zip file

3. Go to your local servlet container home folder, locate the webapps folder and create another folder in it. Name this one "Challenge".

4. Extract the file generated at the step 2 into the Challenge folder. Make sure that gwt-servlet.jar is found under WEB-INF\lib, 
otherwise server-side code will not be called

5. Start the local server. For Tomcat, this would mean running the startUp.bat file from the cmd (on Windows), or the startUp.sh file
on Unix/Linux platforms. The server will start immediately

6. Access the following link: http://localhost:8080/Challenge . The application will load. 

II. SAMPLE RUN 

Now that the application is up and running, we can add some customers. The application asks for a username and an email address. 
Both username and email address cannot be empty. Furthermore, the email address should respect the localpart@domain format. 

We will add our first customer.

Name: John Smith
Email: jsmith@yahoo.com

The response from the server after clicking "add" is :

Customer has been added successfully !

Name: John Smith; Email: jsmith@yahoo.com; Customer ID: 11928875

We will add another customer: 

Name: Andrew Pegg
Email: an_pg83@hotmail.com

And the server output: 

Customer has been added successfully !

Name: Andrew Pegg; Email: an_pg83@hotmail.com; Customer ID: 444389850

Now, we would like to update the owner for our jsmith@yahoo.com email address: 

Name: John Lennon
Email: jsmith@yahoo.com

The server now says: 

Customer has been updated successfully !

Name: John Lennon; Email: jsmith@yahoo.com; Customer ID: 11928875

Finally, we would like to see all of our inserted customer. Therefore, we hit "Show customers" and we get: 

Name: Andrew Pegg; Email: an_pg83@hotmail.com; Customer ID: 444389850
Name: John Lennon; Email: jsmith@yahoo.com; Customer ID: 11928875

