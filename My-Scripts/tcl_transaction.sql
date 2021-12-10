-- TCL transaction control language
-- A tx is a unit of work exicuted at one time

CREATE TABLE testbank_accounts (
	cust_name VARCHAR(50),
	balance NUMERIC(50, 2)
);

INSERT INTO testbank_accounts (cust_name, balance)
	VALUES ('Alice', 100),
			('Bob', 50),
			('Sam', 0);
			
SELECT * FROM testbank_accounts;

--alice sends $50 to bob

UPDATE testbank_accounts SET balance = balance - 50 WHERE cust_name = 'Alice';
--what if this second statement never exicuted???????
UPDATE testbank_accounts SET balance = balance + 50 WHERE cust_name = 'Bob';
-- need transactions

--==============TRANSACTIONS==============
--do the above in 1 transaction
--one unit of work, but performs two tasks
--and it happens or it doesnt

BEGIN;
	UPDATE testbank_accounts SET balance = balance - 50 WHERE cust_name = 'Alice';
-- can set up save points
-- SAVEPOINT 
	UPDATE testbank_accounts SET balance = balance + 50 WHERE cust_name = 'Bob';
-- if this is wrong you can roll back
-- ROLLBACK
COMMIT;


