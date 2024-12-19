-- Name: students.sql
-- Author: Ashesh Dhakal
-- Date: 02/15/2024
-- Description: Script to create the students table, extending the users table for inft2201_db database.

CREATE TABLE students (
    id BIGINT PRIMARY KEY,
    programcode VARCHAR(10) NOT NULL,
    programdescription VARCHAR(255) NOT NULL,
    year INT CHECK (year > 0) NOT NULL,
    FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
);

-- Insert students
INSERT INTO students (id, programcode, programdescription, year) VALUES
(100886468, 'CPA', 'Computer Programming Analysis', 3),
(100111111, 'CSTY', 'Computer System Technology', 3),
(100888888, 'SE', 'Software Engineering', 2);