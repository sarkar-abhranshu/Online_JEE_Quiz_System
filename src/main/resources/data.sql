-- Insert sample users (password is 'password' encrypted with BCrypt)
-- BCrypt hash for 'password': $2a$10$r5O63vIC3BPinycirYqqVeW9XIwC3GQjSXNE0jOsSESdKeo.CRngy
INSERT INTO users (name, email, password, role) VALUES
('Admin User', 'admin@quiz.com', '$2a$10$r5O63vIC3BPinycirYqqVeW9XIwC3GQjSXNE0jOsSESdKeo.CRngy', 'ADMIN'),
('John Smith', 'john@student.com', '$2a$10$r5O63vIC3BPinycirYqqVeW9XIwC3GQjSXNE0jOsSESdKeo.CRngy', 'STUDENT'),
('Jane Doe', 'jane@student.com', '$2a$10$r5O63vIC3BPinycirYqqVeW9XIwC3GQjSXNE0jOsSESdKeo.CRngy', 'STUDENT'),
('Bob Wilson', 'bob@student.com', '$2a$10$r5O63vIC3BPinycirYqqVeW9XIwC3GQjSXNE0jOsSESdKeo.CRngy', 'STUDENT');

-- Quizzes and quiz-related seed data intentionally removed for now.
