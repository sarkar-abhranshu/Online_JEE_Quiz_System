-- Insert sample users (password is 'password' encrypted with BCrypt)
-- BCrypt hash for 'password': $2a$10$r5O63vIC3BPinycirYqqVeW9XIwC3GQjSXNE0jOsSESdKeo.CRngy
INSERT INTO users (name, email, password, role) VALUES
('Admin User', 'admin@quiz.com', '$2a$10$r5O63vIC3BPinycirYqqVeW9XIwC3GQjSXNE0jOsSESdKeo.CRngy', 'ADMIN'),
('John Smith', 'john@student.com', '$2a$10$r5O63vIC3BPinycirYqqVeW9XIwC3GQjSXNE0jOsSESdKeo.CRngy', 'STUDENT'),
('Jane Doe', 'jane@student.com', '$2a$10$r5O63vIC3BPinycirYqqVeW9XIwC3GQjSXNE0jOsSESdKeo.CRngy', 'STUDENT'),
('Bob Wilson', 'bob@student.com', '$2a$10$r5O63vIC3BPinycirYqqVeW9XIwC3GQjSXNE0jOsSESdKeo.CRngy', 'STUDENT');

-- Insert sample quizzes
INSERT INTO quizzes (title, description, duration_minutes, created_by, is_active) VALUES
('Java Basics Quiz', 'Test your knowledge of Java fundamentals', 30, 1, TRUE),
('Object-Oriented Programming', 'Quiz on OOP principles and design patterns', 45, 1, TRUE),
('Database Fundamentals', 'Test your SQL and database knowledge', 20, 1, TRUE);

-- Insert sample questions for Quiz 1 (Java Basics Quiz)
INSERT INTO questions (quiz_id, question_text, marks, question_type, option_a, option_b, option_c, option_d, correct_answer) VALUES
(1, 'Which of the following is a valid Java data type?', 2, 'MCQ', 'int', 'String', 'float', 'All of the above', 'All of the above'),
(1, 'What is the default value of a boolean variable in Java?', 2, 'MCQ', 'true', 'false', 'null', '0', 'false'),
(1, 'Which keyword is used to inherit a class in Java?', 2, 'MCQ', 'implements', 'extends', 'inherits', 'super', 'extends');

INSERT INTO questions (quiz_id, question_text, marks, question_type, correct_boolean) VALUES
(1, 'Java is a platform-independent language.', 1, 'TRUE_FALSE', TRUE),
(1, 'Multiple inheritance is supported in Java through classes.', 1, 'TRUE_FALSE', FALSE);

-- Insert sample questions for Quiz 2 (OOP Quiz)
INSERT INTO questions (quiz_id, question_text, marks, question_type, option_a, option_b, option_c, option_d, correct_answer) VALUES
(2, 'Which OOP principle focuses on hiding implementation details?', 3, 'MCQ', 'Inheritance', 'Polymorphism', 'Encapsulation', 'Abstraction', 'Encapsulation'),
(2, 'What design pattern is used to create objects without specifying their exact classes?', 3, 'MCQ', 'Singleton', 'Factory', 'Observer', 'Strategy', 'Factory'),
(2, 'Which principle states that derived classes must be substitutable for base classes?', 3, 'MCQ', 'Single Responsibility', 'Open/Closed', 'Liskov Substitution', 'Dependency Inversion', 'Liskov Substitution');

INSERT INTO questions (quiz_id, question_text, marks, question_type, correct_boolean) VALUES
(2, 'Polymorphism allows objects of different classes to be treated as objects of a common superclass.', 2, 'TRUE_FALSE', TRUE),
(2, 'The Singleton pattern allows multiple instances of a class.', 2, 'TRUE_FALSE', FALSE);

-- Insert sample questions for Quiz 3 (Database Fundamentals)
INSERT INTO questions (quiz_id, question_text, marks, question_type, option_a, option_b, option_c, option_d, correct_answer) VALUES
(3, 'Which SQL statement is used to retrieve data from a database?', 2, 'MCQ', 'GET', 'SELECT', 'FETCH', 'RETRIEVE', 'SELECT'),
(3, 'What does ACID stand for in database transactions?', 3, 'MCQ', 'Atomicity Consistency Isolation Durability', 'Accuracy Completion Integration Data', 'Addition Creation Integration Deletion', 'None of the above', 'Atomicity Consistency Isolation Durability'),
(3, 'Which constraint ensures that a column cannot have NULL values?', 2, 'MCQ', 'UNIQUE', 'PRIMARY KEY', 'NOT NULL', 'FOREIGN KEY', 'NOT NULL');

INSERT INTO questions (quiz_id, question_text, marks, question_type, correct_boolean) VALUES
(3, 'A primary key can contain NULL values.', 1, 'TRUE_FALSE', FALSE),
(3, 'JOIN operations combine rows from two or more tables.', 1, 'TRUE_FALSE', TRUE);

-- Insert sample quiz attempts and results
INSERT INTO quiz_attempts (student_id, quiz_id, start_time, end_time, is_completed) VALUES
(2, 1, '2024-01-15 10:00:00', '2024-01-15 10:25:00', TRUE),
(2, 2, '2024-01-16 14:00:00', '2024-01-16 14:40:00', TRUE),
(3, 1, '2024-01-15 11:00:00', '2024-01-15 11:28:00', TRUE);

-- Insert sample results
INSERT INTO results (student_id, quiz_id, attempt_id, score, total_marks, percentage, submitted_at) VALUES
(2, 1, 1, 8, 10, 80.0, '2024-01-15 10:25:00'),
(2, 2, 2, 12, 15, 80.0, '2024-01-16 14:40:00'),
(3, 1, 3, 6, 10, 60.0, '2024-01-15 11:28:00');

-- Insert sample answers
INSERT INTO answers (attempt_id, question_id, student_answer, is_correct, marks_obtained) VALUES
(1, 1, 'All of the above', TRUE, 2),
(1, 2, 'false', TRUE, 2),
(1, 3, 'extends', TRUE, 2),
(1, 4, 'true', TRUE, 1),
(1, 5, 'false', TRUE, 1),
(1, 6, 'public', FALSE, 0);
