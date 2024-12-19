-- Drop the customers table if it exists
DROP TABLE IF EXISTS customers;

-- Create the customers table
CREATE TABLE customers(
	phoneNumber VARCHAR(15) PRIMARY KEY,
	address VARCHAR(30) NOT  NULL,
	name VARCHAR(30) NOT  NULL
);

-- Set the owner of the customers table
ALTER TABLE customers OWNER TO db_user;

-- Insert sample data into the customers table
INSERT INTO customers(phoneNumber, address, name) VALUES ('123-4567','Toronto','Eleanor');
INSERT INTO customers(phoneNumber, address, name) VALUES ('765-4321','Montreal','Dave');
INSERT INTO customers(phoneNumber, address, name) VALUES ('222-2222','Halifax','Mike');
INSERT INTO customers(phoneNumber, address, name) VALUES ('333-3333','Winnipeg','Brian');
INSERT INTO customers(phoneNumber, address, name) VALUES ('444-4444','Vancouver','Dan');
INSERT INTO customers(phoneNumber, address, name) VALUES ('555-5555','Calgary','JoAnn');

-- Select all records from the customers table
SELECT * FROM customers;