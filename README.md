# Online JEE Quiz System

Online JEE Quiz System is a Spring Boot web application for creating, managing, attempting, and evaluating quizzes. It supports role-based access for admins and students, automatic score generation, result history, and quiz analytics.

## Features

- Role-based login for Admin and Student users
- Admin dashboard for quiz management
- Quiz creation and question management
- Student quiz attempt flow with timed sessions
- Automatic evaluation and result generation
- Result history for students
- Analytics for admins, including attempt count and average scores
- Thymeleaf-based server-rendered UI
- Spring Security authentication and authorization

## Tech Stack

- Java 25
- Spring Boot 3.5.0
- Spring MVC
- Spring Data JPA
- Spring Security
- Thymeleaf
- MySQL for the main profile
- H2 for local testing profile

## Project Structure

- src/main/java/com/quiz/controller: Web controllers for admin, student, and authentication flows
- src/main/java/com/quiz/model: Domain entities such as Quiz, Question, Answer, Result, and QuizAttempt
- src/main/java/com/quiz/service: Application services for quiz, result, question, and user logic
- src/main/java/com/quiz/repository: JPA repositories
- src/main/java/com/quiz/strategy: Evaluation strategy implementations
- src/main/java/com/quiz/factory: Question creation factory
- src/main/java/com/quiz/security: Spring Security configuration and user details service
- src/main/java/com/quiz/singleton: Quiz session management
- src/main/resources/templates: Thymeleaf views
- src/main/resources/static: CSS and JavaScript assets

## Prerequisites

- JDK 25
- Maven 3.9+
- MySQL 8 if you want to run against the default database profile

## Setup and Run

### Option 1: Local H2 profile

This is the easiest way to run the project locally because it uses an in-memory H2 database and loads the schema and seed data automatically.

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

Then open:

```text
http://localhost:8080
```

### Option 2: MySQL default profile

The default configuration in src/main/resources/application.properties connects to MySQL.

1. Start a MySQL server locally.
2. Create a database named quiz_db, or allow the app to create it automatically.
3. Update the database username and password in src/main/resources/application.properties if needed.
4. Run the application:

```bash
mvn clean spring-boot:run
```

### Windows helper script

The repository includes run-local.ps1, which sets up Java and Maven from the expected Windows paths and runs the app with the local profile.

```powershell
.\run-local.ps1
```

## Default Login Accounts

The local seed data includes these users with the password 'password':

- Admin: admin@quiz.com
- Student: john@student.com
- Student: jane@student.com
- Student: bob@student.com

## How to Use the Application

### As a Student

1. Open the login page and sign in with a student account.
2. Go to the student dashboard to view recent performance.
3. Browse active quizzes from the quiz list.
4. Start a quiz attempt.
5. Answer the questions before the timer expires.
6. Submit the quiz to see the instant score and answer review page.
7. Open the history page to review past attempts.

Student routes:

- /student/dashboard
- /student/quizzes
- /student/attempt/{quizId}
- /student/submit/{attemptId}
- /student/result/{resultId}
- /student/history

### As an Admin

1. Log in with an admin account.
2. Open the admin dashboard to view the quizzes you created.
3. Create a new quiz.
4. Add questions to the quiz.
5. Activate or deactivate the quiz as needed.
6. Open analytics to review attempt count, average score, average percentage, and individual results.

Admin routes:

- /admin/dashboard
- /admin/create-quiz
- /admin/add-question/{quizId}
- /admin/analytics/{quizId}
- /admin/toggle-quiz/{quizId}
- /admin/delete-quiz/{quizId}
- /admin/delete-question/{questionId}

## Quiz Evaluation and Result Flow

When a student submits a quiz, the app performs the following steps:

1. The controller collects submitted answers from the form.
2. ResultService retrieves the quiz attempt.
3. Each question is evaluated against the student answer.
4. Marks are added for correct answers and negative marks are applied for wrong answers when configured.
5. Each Answer is persisted.
6. The attempt is marked completed.
7. A Result record is created and saved.
8. The student is redirected to the result page.

The score and percentage are stored in the Result entity, and the result page shows the final score immediately after submission.

## Design Principles and Patterns

This project intentionally demonstrates several object-oriented design principles and patterns:

### SOLID Principles

- SRP: Controllers handle HTTP flow, services handle business logic, repositories handle persistence, and models hold domain data.
- OCP: New question or evaluation behavior can be added by extending the existing abstraction instead of rewriting the core flow.
- LSP: Concrete question types can be used wherever the abstract Question type is expected.
- ISP: EvaluationStrategy exposes only the evaluation contract needed by the question types.
- DIP: Services and controllers depend on abstractions such as repositories, services, and evaluation interfaces.

### Design Patterns

- Strategy Pattern: EvaluationStrategy defines the contract, with MCQEvaluationStrategy and TrueFalseEvaluationStrategy providing different evaluation behaviors.
- Factory Pattern: QuestionFactory centralizes creation of MCQ and True/False questions.
- Singleton Pattern: QuizSessionManager manages quiz session timing and completion state.
- MVC Pattern: Controllers, services, and Thymeleaf views separate presentation from application logic.

## Database and Seed Data

- schema.sql contains the database schema.
- data.sql contains sample users.
- The local profile uses H2 and loads both schema and data automatically.
- The default profile uses MySQL and points to quiz_db.

## Notes

- The application listens on port 8080 by default.
- If you use the MySQL profile, make sure the database credentials in application.properties match your local setup.
- The app expects role-specific authorities: ADMIN and STUDENT.

## License

No license file is currently provided in the repository.