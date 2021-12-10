-- Create a table in 1NF


CREATE TABLE employees_1nf (
	emp_id SERIAL PRIMARY KEY,
	emp_Name VARCHAR(50) NOT NULL,
	ssn INTEGER,
	dept VARCHAR(50) --don't want this in there for 2nf
);

-- 2nd normal form ======================================
CREATE TABLE employees_2nf (
	emp_id SERIAL PRIMARY KEY,		
	emp_Name VARCHAR(50) NOT NULL,
	ssn INTEGER	
);

CREATE TABLE employees_dept_2nf (
	emp_id REFERENCES employees_2nf(emp_id),
	dept VARCHAR(50) NOT NULL --what if this dept needs to change, make a dept table

);

-- 3 normal form=============================
-- RUN THESE THREE ALL AT ONCE

-- table connecting dept id to employee
CREATE TABLE dept_3nf (
	dept_id SERIAL PRIMARY KEY,
	dept_name VARCHAR(50)
);

-- 3rd normal form
-- table connecting employee to the departments
CREATE TABLE employees_3nf (
	emp_id SERIAL PRIMARY KEY,		
	emp_Name VARCHAR(50) NOT NULL,
	ssn INTEGER	
);

CREATE TABLE emp_dept_3nf (
	-- try with this next line john boyle added
	emp_dept_relation SERIAL PRIMARY KEY, -- uniquley defines EACH relationship

	emp_id INTEGER REFERENCES employees_3nf(emp_id),
	dept_id INTEGER REFERENCES dept_3nf
);