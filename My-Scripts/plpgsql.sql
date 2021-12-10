/*
 * PL/pgsal - Proceedural language extension of PostgresSQL
 * 
 * Stored Functions return a value
 * 
 * 
 * 
 */

--=============PLANE FUNCTION================
CREATE OR REPLACE FUNCTION multiply(x NUMERIC, y NUMERIC)
RETURNS NUMERIC
AS $$
	BEGIN
		RETURN x*y;	
		
	END
$$
LANGUAGE plpgsql;

SELECT multiply(3, '2') --can convert VarChar to int!! 
-- returns the result of above

--====================INSERT INTO===================

CREATE TABLE colors(
	id SERIAL PRIMARY KEY,
	color VARCHAR
);

CREATE TABLE sizes (
	id SERIAL PRIMARY KEY,
	size_description VARCHAR
);

INSERT INTO sizes (size_description)
	VALUES ('S'), ('M'), ('L');
INSERT INTO colors (color)
	VALUES ('red'), ('orange'), ('yellow');

--==========TRIGGER FUNCTION==============
--===========1. CREATE FX
--prevent blues from being inserted into the color table
--if the new color is not blue, it will be added to the database
CREATE OR REPLACE FUNCTION no_more_blues()
RETURNS TRIGGER --how you write a trigger
AS $$
	BEGIN
		IF(NEW.color = 'blue') THEN
		RETURN NULL;
		END IF; -- like the } in java
		RETURN NEW;
	END
$$
LANGUAGE plpgsql;
--==============2. CREATE Trigger=====================
--EVERY TIME WE DO AN INSERT, CHECKS COLOR VALUE
CREATE TRIGGER no_blue
	BEFORE INSERT OR UPDATE ON colors
	FOR EACH ROW
	EXECUTE FUNCTION no_more_blues();

--=============3. RUN THIS=====================
SELECT * FROM colors;

INSERT INTO colors(color) VALUES ('magenta');--WORKS!

INSERT INTO colors(color) VALUES ('blue');--WONT ADD!

--------------INDEX SPEED VS SELECT SPEED---------------------
EXPLAIN SELECT * FROM colors WHERE color = 'magenta';

CREATE INDEX fast_colors
	ON colors(color);
-------------ONCE YOU CREATE AN INDEX, IT SPEEDS THINGS UP-------








