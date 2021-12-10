CREATE TABLE one (
	col_one INTEGER PRIMARY KEY,
	col_two INTEGER
);

CREATE TABLE two (
	col_one INTEGER PRIMARY KEY,
	col_two INTEGER
);

--SET OPERATIONS must have same number and type of colums

INSERT INTO one (col_one, col_two)
	VALUES (1,1), (2,2), (3,3), (4,4);

INSERT INTO two (col_one, col_two)
	VALUES (1,100), (2,200), (3,300), (4,400);

-- SET operations are a type of DQL in which we can combined data from 2 tables
-- in one result

-- UNION combine results
-- does not include duplicates
SELECT * FROM one UNION SELECT * FROM two;


SELECT * FROM one INTERSECT SELECT * FROM two;