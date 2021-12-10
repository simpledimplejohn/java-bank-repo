-- SQL 
-- DDL
-- DML


--DCL


CREATE USER harrypotter WITH PASSWORD 'dumbledore';
--how to grant privilages
--has no privilages until he is granted
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA johnmb;
--can revoke privilages
REVOKE ALL PRIVILEGES ON ALL TABLES IN SCHEMA johnmb FROM harrypotter;
--defining the privilages wiht roll
CREATE ROLE super_user WITH
	CREATEDB
	CREATEROLE
	LOGIN
	INHERIT;

GRANT super_user TO harrypotter;
