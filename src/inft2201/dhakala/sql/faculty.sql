-- Name: Ashesh Dhakal
-- Date: 2024-02-16
-- Description: Script to create faculty tables.

CREATE TABLE faculty (
    id BIGINT PRIMARY KEY,
    schoolcode VARCHAR(10) NOT NULL,
    schooldescription VARCHAR(255) NOT NULL,
    office VARCHAR(50) NOT NULL,
    extension INT CHECK (extension > 0) NOT NULL,
    FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
);

INSERT INTO faculty (id, schoolcode, schooldescription, office, extension) VALUES
(200000001, 'ENG', 'Engineering Department', 'Eng-101', 101),
(200000002, 'SCI', 'Science Department', 'Sci-202', 202),
(200000003, 'BUS', 'Business School', 'Bus-303', 303);
