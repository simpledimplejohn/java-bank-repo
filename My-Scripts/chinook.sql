

-- INNER JOIN: Selects all rows from both tables that have a matching common key
-- LEFT JOIN: Selects all rows of table on the left side  (of the statment) and finds matching rows 
   --form the table on the right side
-- RIGHT JOIN: oposite, Right side joined to matching rows LEFT side
-- FULL JOIN: combines all rows all rows from both tables


SELECT * FROM chinook."Artist";
SELECT * FROM chinook."Album";

-- joins 
-- RETURN THE ALBUM ID, TITLE, NAME(of artist) 
	--by joining ALBUM and ARTIST tables 


-- the foirgein key is in the Album table
-- select album ID 
-- What table is it from 
CREATE VIEW simple_album_artist_view AS

SELECT alb."AlbumId", alb."Title", art."Name" AS artist --where the table is returned
	FROM chinook."Album" alb --because it has the fk
	JOIN chinook."Artist" art --because its being joined too
	ON alb."ArtistId" = art."ArtistId"; --where ArtistId is the same 
-- to view the view
SELECT * FROM simple_album_artist_view;
	
--- DOES NOT WORK ================================
-- natural join example
SELECT abl."AlbumId", alb."Title", art."Name" AS Artist
	FROM "Album" alb
	JOIN "Artist" art
	USING("ArtistId"); --ArtistId IS a column present in both tables
	
SELECT* FROM chinook."Employee";

SELECT e1."FirstName", e1."Title", e2."FirstName" AS boss_firstname, e2."Title" AS boss_title
	FROM "Employee" e1
	JOIN "Employee" e2
	ON e1."ReportsTo = e2."EmployeeId";

SELECT * FROM chinook."Track";
SELECT * FROM chinook."InvoiceLine";

SELECT * FROM chinook."Album"
WHERE "Album"."Title" = 'Balls to the Wall';

SELECT * FROM chinook."Artist"

--THIS WORKED!!

SELECT *
FROM chinook."Album"
INNER JOIN chinook."Artist" ON "Album"."AlbumId"="Artist"."ArtistId"












