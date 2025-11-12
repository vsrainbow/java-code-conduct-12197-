-- Create Database
CREATE DATABASE IF NOT EXISTS student_management_db;
USE student_management_db;

-- Drop tables if they exist (for clean setup)
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS courses;

-- Create Courses Table
CREATE TABLE courses (
    course_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL,
    duration INT NOT NULL,
    fee DOUBLE NOT NULL,
    UNIQUE KEY uk_course_name (course_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create Students Table
CREATE TABLE students (
    student_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    course_id BIGINT,
    balance DOUBLE DEFAULT 0.0,
    UNIQUE KEY uk_email (email),
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE SET NULL,
    INDEX idx_course_id (course_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create Payments Table
CREATE TABLE payments (
    payment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    amount DOUBLE NOT NULL,
    payment_date DATETIME NOT NULL,
    payment_type VARCHAR(20) NOT NULL,
    description VARCHAR(255),
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    INDEX idx_student_id (student_id),
    INDEX idx_payment_date (payment_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Insert Sample Courses
INSERT INTO courses (course_name, duration, fee) VALUES
('Java Full Stack Development', 6, 45000.00),
('Python Data Science', 5, 40000.00),
('Web Development', 4, 35000.00),
('Cloud Computing', 4, 38000.00),
('Machine Learning', 6, 50000.00);

-- Insert Sample Students
INSERT INTO students (name, email, course_id, balance) VALUES
('Rahul Sharma', 'rahul.sharma@email.com', 1, 0.00),
('Priya Singh', 'priya.singh@email.com', 2, 0.00),
('Amit Kumar', 'amit.kumar@email.com', 3, 0.00),
('Sneha Patel', 'sneha.patel@email.com', 1, 0.00),
('Vikram Desai', 'vikram.desai@email.com', 4, 0.00);

-- Insert Sample Payments
INSERT INTO payments (student_id, amount, payment_date, payment_type, description) VALUES
(1, 15000.00, NOW(), 'PAYMENT', 'First installment'),
(2, 20000.00, NOW(), 'PAYMENT', 'First installment'),
(3, 35000.00, NOW(), 'PAYMENT', 'Full payment');

-- Update student balances after payments
UPDATE students SET balance = -15000.00 WHERE student_id = 1;
UPDATE students SET balance = -20000.00 WHERE student_id = 2;
UPDATE students SET balance = -35000.00 WHERE student_id = 3;