
# Expense Tracker Application

A web-based Expense Tracker built using Java and Spring Boot. This application helps users record, organise, and monitor their daily expenses.

## Features

- User login and logout
- Add new expenses
- Edit existing expenses
- Delete expenses
- Categorise transactions
- View total spending
- View current month's spending
- View spending grouped by category
- Store expense records in an H2 database
- Separate expense records for each demo account
- Responsive web interface

## Technologies Used

- Java 21
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data JPA and Hibernate
- Thymeleaf
- HTML and CSS
- H2 Database
- Maven

## Demo Login Credentials

| Username | Password |
| demo | demo123 |
| student | student123 |

These accounts are provided for local demonstration purposes only.

## Requirements

- JDK 21 or compatible supported Java version
- Apache Maven
- A web browser
- Internet access on the first run to download Maven dependencies

## How to Run the Project

1. Clone or download this repository.
2. Open a terminal in the project root directory, where `pom.xml` is located.
3. Run the application using Maven:

   ```bash
   mvn spring-boot:run
   ```

4. Wait for Spring Boot to finish starting.
5. Open the following URL in your browser:

   http://localhost:8082

6. Log in using one of the demo accounts listed above.

## Database

The application uses an H2 file-based database. Expense records are stored locally, and the application creates the required database tables automatically.

## Project Structure

```text
ExpenseTrackerProject/
├── pom.xml
├── run-windows.bat
├── .gitignore
└── src/
    └── main/
        ├── java/com/example/expensetracker/
        │   ├── ExpenseTrackerApplication.java
        │   ├── config/
        │   ├── controller/
        │   ├── model/
        │   └── repository/
        └── resources/
            ├── application.properties
            ├── static/css/style.css
            └── templates/
                ├── login.html
                └── expenses/
                    ├── form.html
                    └── list.html
```

## Author

Developed as a Java project demonstrating expense management, database operations, and web application development.
