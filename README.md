# Project Tool Application

## Features

- Log in and log out.
- Safety features to prevent unauthorized access.
- Create, edit, and delete projects.
- Create, edit, and delete subprojects
- Create, edit, and delete tasks.
- Assign tasks to users.
- Different user authorities - admin, employee and project manager
  
## Introduction

This application can be run in two ways:
1. Using our Azure web app which can be accessed at http://eksamensprojekt.azurewebsites.net/
2. Downloading the program from our GitHub repository and running it on localhost. Follow the steps below:


## Prerequisites

Before setting up the program, please ensure that you have access to the following technologies or similar technologies that will work in the same way (note that we cannot guarantee proper functioning with alternative technologies).

### Technologies Used in Project Tool Application:
- **Java**: Used to develop the backend of the application, and to create an API that communicates with the frontend.
- **MySQL**: Used as a relational database to store and manage data.
- **Thymeleaf**: A Java template engine used to facilitate data exchange between the backend and frontend.
- **Spring Boot**: The Spring Boot framework is used to aid in the development of the Java application.
- **IntelliJ**: The coding program used for development

## Installation Guide

1. **Clone the repository:**
- https://github.com/ThomasFBN/Eksamensprojekt

2. **Import SQL script:**
 Import the SQL script into your preferred database server application.

3. **Run the SQL script:**
Ensure the database is set up correctly by running the SQL script provided in the repository.

4. **Modify application.properties:**
Ensure the active profile is set to local. This will ensure the application runs on localhost.
properties
spring.profiles.active=local


5. **Match the database information to your own setup.**
properties:
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_database_username
spring.datasource.password=your_database_password

6. **Run the application.**
   Head to EksamensprojektApplication and start the program

7. **Access the application:**
Open a web browser and navigate to the specified port (usually http://localhost:8080).
Now, you can use the application as intended.


