Website Tracker
======================

This is a web application that allows users to track changes on websites of their choice.
The app has been designed to work with any imaginable online resource that is publicly available.
Users are informed about changes that are interesting to them via different communication methods such as 
Twitter, SMS, mail.
This version of the app represents an MVP (minimal viable product). Currently, only one method of notifying users is implemented: mail notifications. 
Also, only one type of online resource can be tracked, namely GitHub.     
It provides a user-friendly interface where users can enter the URLs of the websites they want to monitor, and the application will notify them whenever any changes occur on those websites.

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
-   Observer Pattern: Used to track changes on websites

Installation
------------

To install and run the application, follow these steps:

1.  Clone the repository: `git clone https://github.com/Next3K/webtracker`
2.  Navigate to the project directory: `cd webtracker`
3.  Set up the database by running the database setup script.
4.  Open the project in your favorite IDE (such as IntelliJ or Eclipse).
5.  Configure the database connection settings in the `application.properties` file.
6.  Build the project using Maven.
7.  Run the application.


License
-------

This project is licensed under the [MIT License](https://github.com/Next3K/webtracker).