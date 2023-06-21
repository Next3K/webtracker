Website Tracker
======================

This is a web application that allows users to track changes on websites of their choice.
The app has been designed to work with any imaginable online resource that is publicly available.
Users are informed about changes that are interesting to them via different communication methods such as 
Twitter, SMS, mail.
This version of the app represents an MVP (minimal viable product). Currently, only one method of notifying users is implemented: mail notifications. 
Also, only one type of online resource can be tracked, namely GitHub.     
It provides a user-friendly interface where users can enter the URLs of the websites they want to monitor, and the application will notify them whenever any changes occur on those websites.

![image](https://github.com/Next3K/webtracker/assets/52383281/fe594c03-4e78-4905-8be3-e585c168dfd7)


Implemented Features
--------

-   GitHub tracking
    - choosing a GitHub user to track
    - choosing what kind of events are of interest
    - choosing what languages are of interest 
    - sending mail notifications 

Future Features
--------
-   Twitter tracking
-   YouTube tracking
-   Twitch tracking
-   Reddit tracking

Technologies Used
-----------------

The application is built using the following technologies:

-   Backend: Java with Spring Boot framework, Maven, Hibernate
-   Frontend: not implemented
-   Database: PostgreSQL

Installation
------------

To install and run the application, follow these steps:

1.  Clone the repository: `git clone https://github.com/Next3K/webtracker`
2.  Navigate to the project directory: `cd webtracker`
3.  Set up the database by running the database setup script (_docker-compose.yml_).
4.  Open the project in your favorite IDE (such as IntelliJ or Eclipse).
5.  Specify the following configuration options before running:
- -Dspring.mail.username=MAIL: Specify the email address to be used as the sender's username for the email service.
- -Dspring.mail.password=MAIL_PASSWORD: Provide the password for the email account used as the sender's credentials.
- -Dspring.mail.host=HOST_EMAIL: Set the host address for the email service provider.
- -Dspring.mail.port=PORT: Specify the port number to be used for the email service.
- -Dcredentials.github.username=GITHUB_USERNAME: Specify the username of account used for GitHub api authentication.
- -Dcredentials.github.token=GITHUB_TOKEN: Provide the authentication token for the GITHUB_USERNAME account.
6.  Build the project using Maven.
7.  Run the application.


License
-------

This project is licensed under the [MIT License](https://github.com/Next3K/webtracker).
