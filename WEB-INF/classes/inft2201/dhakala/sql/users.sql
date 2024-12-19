-- Name: Ashesh Dhakal
-- Date: 2024-02-16
-- Description: Script to create users
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS faculty;
DROP TABLE IF EXISTS users;


CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    password CHAR(40) NOT NULL,
    firstname VARCHAR(50) NOT NULL,
    lastname VARCHAR(50) NOT NULL,
    emailaddress VARCHAR(255) UNIQUE NOT NULL,
    lastaccess DATE NOT NULL,
    enroldate DATE NOT NULL,
    type CHAR(1) CHECK (type IN ('s', 'f')) NOT NULL,
    enabled BOOLEAN NOT NULL
);
-- Modify the column lastaccess to DATETIME
ALTER TABLE users ALTER COLUMN lastaccess SET DATA TYPE TIMESTAMP;
ALTER TABLE users ALTER COLUMN lastaccess SET DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE users ALTER COLUMN lastaccess SET NOT NULL;

INSERT INTO users (id, password, firstname, lastname, emailaddress, lastaccess, enroldate, type, enabled) VALUES
(100886468, '5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8', 'Ashesh', 'Dhakal', 'ashesh.dhakal@dcmai.ca', '2022-09-07', '2022-09-07', 's', TRUE),
(100111111, '5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8', 'Mike', 'Jones', 'mike.jones@dcmail.ca', '2024-02-16', '2015-09-11', 's', TRUE),
(100888888, '5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8', 'Bob', 'Doe', 'bob.doe@dcmail.ca', '2024-02-16', '2022-09-07', 's', TRUE),
(200000001, '5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8', 'John', 'Smith', 'john.smith@dcmail.ca', '2024-02-16', '2022-09-07', 'f', TRUE),
(200000002, '5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8', 'Emily', 'Jones', 'emily.jones@dcmail.ca', '2024-02-16', '2022-09-07', 'f', TRUE),
(200000003, '5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8', 'David', 'Brown', 'david.brown@dcmail.ca', '2024-02-16', '2022-09-07', 'f', TRUE),
(100987654, '5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8', 'Dave', 'Green', 'dave.brown@dcmail.ca', '2024-02-16', '2022-09-07', 'f', TRUE);