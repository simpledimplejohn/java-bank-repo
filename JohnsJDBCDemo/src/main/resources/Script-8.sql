-- allow to delete the enum
DROP TYPE IF EXISTS johnmb.user_role CASCADE;
--custom enum
CREATE TYPE johnmb.user_role AS ENUM ('Admin', 'Customer', 'Employee');

-- to delete all if needed
DROP TABLE IF EXISTS johnmb.users CASCADE;

--table names must be lowercase
CREATE TABLE johnmb.users (
	id SERIAL PRIMARY KEY, 
	username VARCHAR(50) NOT NULL UNIQUE,
	pwd VARCHAR(50) NOT NULL,
	-- how to use the enum
	user_role johnmb.user_role NOT NULL
);

DROP TABLE IF EXISTS johnmb.accounts CASCADE;
--accounts table
CREATE TABLE johnmb.accounts (
	id SERIAL PRIMARY KEY,
	balance NUMERIC(50, 2) DEFAULT 0, --NUMERIC(to the right, to the left)
	acc_owner INTEGER REFERENCES johnmb.users(id),
	active BOOLEAN DEFAULT FALSE -- can be activated by someone else
);

--manually creating a join-table
--there is a way to create on automatically

DROP TABLE IF EXISTS johnmb.users_accounts_jt CASCADE;
CREATE TABLE johnmb.users_accounts_jt (
	acc_owner INTEGER NOT NULL REFERENCES johnmb.users(id),
	account INTEGER NOT NULL REFERENCES johnmb.accounts(id)
);

SELECT * FROM johnmb.users_accounts_jt


--JOIN TABLE FOR THE PROJECT
-- 1. ADD SOME USERS TO THESE TABLES


INSERT INTO johnmb.users (username, pwd, user_role)
	VALUES 	('Larry', 'pass', 'Employee'),
			('Mary', '1234', 'Customer'),
			('Barry', 'pass', 'Customer');

SELECT * FROM users;

-- 2. ADED INFO TO ACCOUNTS
-- now we are making two accounts for mary (2)
INSERT INTO johnmb.accounts (balance, acc_owner)
	VALUES (100, 1),
			(200, 2),
			(2000, 2),
			(300, 3);
			
SELECT * FROM johnmb.accounts

--MANUALLY MAKE A JOINS TABLE
--LAST TIME WE HAVE TO DO THIS
INSERT INTO johnmb.users_accounts_jt (acc_owner, account)
	VALUES (1, 1),
			(2,2),
			(2,3),
			(3,4)

SELECT * FROM johnmb.users_accounts_jt;

-- trigger fx to create a join table 
-- automatically populated the ID and the account identity 

/*
 * PL/pgsql Procedural Language for PostgreSQL
 * 
 * - Allows you to create custom types like Enums
 * - Stored fx (return a result)
 * - Stored proceedure (void method)
 * - triggers like an eventlistener for when something happens, data added=
 */	
	
/*
 * create [or replace] function [name] (parmaters)
 * return [type]
 * as '
 * 		begin
 * 			[logic]
 * 		end
 * '
 * language plpgsql
 */	

-- create a function that inserts the account owner id and acc id into the joins table
-- every time a new record is inserted into Accounts table

-- RUN THIS TO CREATE THE FIRST TRIGGER IN THE FUNCTION FOLDER
CREATE OR REPLACE FUNCTION johnmb.auto_insert_into_jt()
RETURNS TRIGGER 
AS '
	BEGIN
		INSERT INTO johnmb.users_accounts_jt(acc_owner, account)
			VALUES (NEW.acc_owner, NEW.id);

			RETURN NEW;
	END
'
language plpgsql

-- THEN RUN THIS TO CREATE THE DEPENDENT TRIGGER IN THE FUNCTIONS FOLDER
CREATE TRIGGER trig
	AFTER INSERT ON johnmb.accounts 
	FOR EACH ROW 
	EXECUTE PROCEDURE johnmb.auto_insert_into_jt(); --proceedure above

SELECT * FROM johnmb.users_accounts_jt;	
SELECT * FROM johnmb.users;
SELECT * FROM johnmb.accounts;


-- HOW TO INSERT DATA INTO THE DATABASE
INSERT INTO johnmb.accounts (balanace, acc_owner, active)
	VALUES (500000, 4, TRUE);
	
--------------RETURN USERS AND THEIR ACCOUNTS IN ONE RETURN----------------
SELECT 	users.id, users.username, users.pwd, users.user_role, accounts.id AS account_id, accounts.balance, accounts.active 
		FROM users
		LEFT JOIN users_accounts_jt ON users.id = users_accounts_jt.acc_owner
		LEFT JOIN accounts ON accounts.id = users_accounts_jt.account;
--------------needs the johnmb. added to a few things--------------------------
	
SELECT users.id, users.username, users.pwd, users.user_role, accounts.id AS account_id, accounts.balance, 
accounts.active 
	FROM johnmb.users
	LEFT JOIN johnmb.users_accounts_jt ON users.id = users_accounts_jt.acc_owner
	LEFT JOIN johnmb.accounts ON accounts.id = users_accounts_jt.account;	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	