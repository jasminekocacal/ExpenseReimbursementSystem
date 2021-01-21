# Expense Reimbursement System

## Project Description

The Expense Reimbursement System (ERS) will manage the process of reimbursing employees for expenses incurred while on company time. All employees in the company can login and submit requests for reimbursement and view their past tickets and pending requests. Finance managers can log in and view all reimbursement requests and past history for all employees in the company. Finance managers are authorized to approve and deny requests for expense reimbursement.

## Technologies Used

* Java - Version 1.8
* PostGreSQL - Version 42.2.18
* JUnit - Version 4.13
* H2 - Version 1.4.200
* Javascript - Version 1.8.5
* HTML - Version HTML5

## Features

List of features ready and TODOs for future development
* Log in to the application as an employee or as a finance manager
* As a user, create a reimbursement and include the amount, description, and type
* As a user, view pending reimbursement tickets and past tickets that may have been approved or rejected
* As a finance manager, view all the pending reimbursement tickets created by the employees 
* As a finance manager, approve or deny employee reimbursements
* As a finance manager, view all the reimbursements that you had previously confirmed

To-do list:
* Improve the design of the forms and make the website more aesthetically pleasing
* Rather than forwarding from page to page to access functionality, use ajax to allow everything to be performed on the same page

## Getting Started
   
* Clone project with `https://github.com/jasminekocacal/ExpenseReimbursementSystem.git`
* Set up back-end environment variables:
```
TRAINING_DB_URL=YOUR_DB_URL_HERE
TRAINING_DBNAME=YOUR_DB_NAME_HERE
TRAINING_DB_USERNAME=YOUR_DB_USERNAME_HERE
TRAINING_DB_PASSWORD=YOUR_PASSWORD_HERE
```
* Compile the project with Maven and deploy to Tomcat

## Usage

* Employees and finance managers can log in on the landing page
* On the Employee page, employees can view all of the pending reimbursements. They can also click buttons to create a reimbursement, view their approved reimbursements, or view their denied reimbursements
* On the finance manager page, they can view all of the pending reimbursements of the users. They can click buttons to update one of the pending reimbursements and choose to approve or deny it. They can also view the reimbursements that they have updated in the past. 
