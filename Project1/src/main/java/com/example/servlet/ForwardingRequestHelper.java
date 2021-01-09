package com.example.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.example.controller.EmployeeController;
import com.example.controller.LoginController;
import com.example.model.ERSUser;

public class ForwardingRequestHelper extends HttpServlet {

	public static String process(HttpServletRequest req, HttpServletResponse resp) {
		
		switch (req.getRequestURI()) {
		case "/Project1/forwarding/login":
			return LoginController.login(req);
			
		case "/Project1/forwarding/empapproved":
			return "/html/empApproved.html";
		
		case "/Project1/forwarding/empdenied":
			return "/html/empDenied.html";
			
		case "/Project1/forwarding/addreimb":
			return "/html/addReimb.html";
			
		case "/Project1/forwarding/goemphome":
			return "/html/employeePage.html";
			
		case "/Project1/forwarding/fmresolved":
			return "/html/fmPreviousResolved.html";
			
		case "/Project1/forwarding/fmresolvereimb":
			return "/html/updateReimb.html";
			
		case "/Project1/forwarding/gofmhome":
			return "/html/financeManagerPage.html";
			
		default:
			System.out.println("In forwarding request helper default");
			return "/html/badlogin.html";

		}
	}
}
