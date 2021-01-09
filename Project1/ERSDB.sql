----------------ERS REIMBURSEMENT DATA TABLES
---LET'S TAKE A LOOK

SELECT * FROM ers_user eu;
SELECT * FROM reimbursement r2;
SELECT * FROM reimb_lookups_users_join rluj;
SELECT * FROM user_role_lookup;
SELECT * FROM reimb_status_lookup;
SELECT * FROM reimb_type_lookup;

SELECT * FROM reimbursement WHERE reimb_status = 1 OR reimb_status = 2;


DROP TABLE reimb_lookups_users_join;
DROP TABLE reimbursement;
DROP TABLE reimb_type_lookup;
DROP TABLE reimb_status_lookup;


----CREATE LOOKUP TABLES

CREATE TABLE user_role_lookup(
	user_role_id INTEGER PRIMARY KEY
	, user_role VARCHAR(50) NOT NULL
);

CREATE TABLE reimb_status_lookup(
	status_id INTEGER PRIMARY KEY
	, status VARCHAR(50) NOT NULL
);

CREATE TABLE reimb_type_lookup(
	type_id INTEGER PRIMARY KEY
	, reimb_type VARCHAR(50) NOT NULL
);

--INSERT VALUES INTO LOOKUP TABLES

INSERT INTO user_role_lookup
VALUES (0, 'employee')
	,(1, 'finance_manager');

INSERT INTO reimb_status_lookup
VALUES (0, 'pending')
	,(1, 'approved')
	,(2, 'denied');
	
INSERT INTO reimb_type_lookup
VALUES (0, 'other')
, (1, 'lodging')
, (2, 'travel')
, (3, 'food');

----CREATE TABLES THEMSELVES

CREATE TABLE ERS_user(
	user_id SERIAL PRIMARY KEY
	, user_username VARCHAR(50) NOT NULL UNIQUE
	, user_password VARCHAR(50) NOT NULL
	, first_name VARCHAR(100) NOT NULL
	, last_name VARCHAR(100) NOT NULL
	, user_email VARCHAR(150) NOT NULL UNIQUE
	, user_role_id INTEGER NOT NULL 
	, FOREIGN KEY(user_role_id) REFERENCES user_role_lookup(user_role_id)
);

CREATE TABLE reimbursement(
	reimb_id SERIAL PRIMARY KEY
	, reimb_amount NUMERIC(20, 2) NOT NULL
	, reimb_date_submitted DATE NOT NULL DEFAULT CURRENT_DATE
	, reimb_date_resolved DATE 
	, reimb_description VARCHAR(250)
	, reimb_author INTEGER NOT NULL
	, reimb_resolver INTEGER
	, reimb_status INTEGER NOT NULL
	, reimb_type INTEGER NOT NULL
	, FOREIGN KEY(reimb_author) REFERENCES ERS_user(user_id) ON DELETE CASCADE 
	, FOREIGN KEY(reimb_resolver) REFERENCES ERS_user(user_id)
	, FOREIGN KEY(reimb_status) REFERENCES reimb_status_lookup(status_id)
	, FOREIGN KEY(reimb_type) REFERENCES reimb_type_lookup(type_id)
);


----LET'S GET THIS JOIN UP IN THIS JOINT

CREATE VIEW reimb_lookups_users_join AS
SELECT u.user_id AS employee_user_id, u.first_name AS employee_fname, u.last_name AS employee_lname, reimb_id
, reimb_amount, reimb_description, t.reimb_type, reimb_date_submitted, s.status, reimb_date_resolved, e.user_id AS resolver_id FROM reimbursement r
	INNER JOIN ers_user u ON reimb_author = u.user_id
	LEFT JOIN ers_user e ON reimb_resolver = e.user_id
	INNER JOIN reimb_status_lookup s ON reimb_status = s.status_id
	INNER JOIN reimb_type_lookup t ON r.reimb_type = t.type_id
ORDER BY employee_user_id;

DROP VIEW reimb_lookups_users_join;

-----INSERT DUMMY DATA

INSERT INTO ers_user 
VALUES (DEFAULT, 'johnsmith', 'password1', 'John', 'Smith', 'johnsmith@gmail', 0)
	,(DEFAULT, 'johndoe', 'password2', 'John', 'Doe', 'johndoe@gmail', 0)
	,(DEFAULT, 'janedoe', 'password3', 'Jane', 'Doe', 'janedoe@gmail', 1)
	,(DEFAULT, 'williamsmith', 'password4', 'William', 'Smith', 'williamsmith@gmail', 1)
	,(DEFAULT, 'corneliusboy', 'password5', 'Cornelius', 'Boy', 'corneliusboy@gmail', 0)
	,(DEFAULT, 'charlottebronte', 'password6', 'Charlotte', 'Bronte', 'charlottebronte@gmail', 0)
	,(DEFAULT, 'emilyblunt', 'password7', 'Emily', 'Blunt', 'emilyblunt@gmail', 0)
	,(DEFAULT, 'potatoman', 'potpassword', 'Potato', 'Man', 'potatoman@gmail', 1);
	
INSERT INTO reimbursement
VALUES (DEFAULT, 50.23, DEFAULT, null, 'Bought potatoes', 2, null, 0, 3)
	, (DEFAULT, 50.23, DEFAULT, null, 'Went to Malibu', 1, null, 0, 2)
	, (DEFAULT, 50.23, DEFAULT, '2021-01-23', 'Stayed at Hilton', 7, 3, 1, 1)
	, (DEFAULT, 50.23, DEFAULT, null, 'Stayed at HolidayInn', 6, null, 0, 1)
	, (DEFAULT, 50.23, DEFAULT, '2021-08-11', 'Bought souveniers', 2, 4, 2, 0)
	, (DEFAULT, 50.23, DEFAULT, null, 'Bought diamond potatoes', 1, null, 0, 0);


INSERT INTO reimbursement VALUES (DEFAULT, ?, DEFAULT, NULL, ?, ?, NULL, 0, ?)

UPDATE reimbursement 
SET reimb_date_resolved = CURRENT_DATE
, reimb_resolver = 3
, reimb_status = 1
WHERE reimb_id = 13;






