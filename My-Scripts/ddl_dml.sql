-- This is a Single line Comment

/*
 * this is a multi-line comment
 * SQL Structure Query Language
 * Server-side scripting language
 * 
 */

-- NEVER DO THIS !!!!!!!!!!!!!!
DROP TABLE IF EXISTS johnmb.phonenumbers CASCADE;
DROP TABLE IF EXISTS johnmb.employees CASCADE;
-- completely drops the table regardless or relationships

-- DDL Data Definition Language
-- creating first table
CREATE TABLE employees (
	id SERIAL PRIMARY KEY, -- auto increase id with SERIAL
	first_name VARCHAR(30) CHECK(LENGTH(first_name) >= 2), -- adding constraints 
	last_name VARCHAR(30) NOT NULL,
	email VARCHAR(100) UNIQUE,
	employee_age INTEGER NOT NULL DEFAULT 0,
	supervisor INTEGER-- point to the other employee that manages another employee
);

--manually define forieng key
ALTER TABLE employees 
	ADD CONSTRAINT employee_supervisor_fk
	FOREIGN KEY (supervisor) REFERENCES employees(id);

-- DML data manipulation language
	-- INSERT, DELETE from table 
	-- (COLUMN1, COLUMN2, ...)
INSERT INTO johnmb.employees (first_name, last_name, email, employee_age)
	VALUES ('Big', 'Guy', 'fredbuddy69@gmail.com', 22);

--check his id

SELECT * FROM johnmb.employees

INSERT INTO johnmb.employees (first_name, last_name, email, employee_age, supervisor)
	VALUES ('Little', 'Guy', 'bredfuddy69@gmail.com', 2, 1);



SELECT * FROM johnmb.employees;

--WIPES ALL THE DATA, LEAVES THE TABLE
TRUNCATE TABLE employees CASCADE; 

TRUNCATE TABLE phonenumbers CASCADE; 

CREATE TABLE phonenumbers(
	id SERIAL PRIMARY KEY,
	-- how to declare a forien key the simple way
	-- REFERENCES is how you call a foreign key
	employee_id INTEGER NOT NULL REFERENCES employees(id),
	home VARCHAR(20),
	mobile VARCHAR(20),
	work_num VARCHAR(20)
);

-- let's insert the phone numbers
-- The forien keys are the first value
INSERT INTO phonenumbers (employee_id, home, mobile, work_num)
	VALUES (1, '111-111-1111', '222-222-2222', '333-333-3333'),
		   (2, '111-111-1111', '222-222-2222', '333-333-3333');
		   
SELECT * FROM phonenumbers; 

-- Diffrence between TRUNCATE, DROP, DELETE

--DML DELTE, INSERT, UPDATE, for editing individual records

SELECT * FROM johnmb.phonenumbers 

DELETE FROM phonenumbers WHERE employee_id = 1

UPDATE phonenumbers
	SET home = '432-890-4321'
	WHERE employee_id = 2;

SELECT * FROM employees 

SELECT * FROM employees WHERE employee_age = 22;

SELECT first_name, last_name FROM johnmb.employees WHERE employee_age = 2;

-- this concatinates the names with the || operator
SELECT first_name ||"-" || last_name AS "Full Name" FROM employees;

-- Views represent "virtual tables"
-- a way to store the results of a query that you can call for latter
CREATE VIEW view_names AS SELECT first_name, last_name FROM employees;

SELECT * FROM view_names;




