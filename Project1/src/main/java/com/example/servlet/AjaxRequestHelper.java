package com.example.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.controller.EmployeeController;
import com.example.controller.FinanceManagerController;
import com.example.controller.LoginController;

public class AjaxRequestHelper {

	public static void process(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		System.out.println(req.getRequestURI());

		switch (req.getRequestURI()) {
		case "/Project1/ajax/fmpending":

			FinanceManagerController.allPendingFinder(req, resp);
			break;

		case "/Project1/ajax/fmresolved":

			FinanceManagerController.allResolvedFinder(req, resp);
			break;
			
		case "/Project1/ajax/fmresolvereimb":

			FinanceManagerController.updateReimbursement(req, resp);
			break;

		case "/Project1/ajax/emppending":

			EmployeeController.allPendingReimbursementsFinder(req, resp);
			break;

		case "/Project1/ajax/empapproved":

			EmployeeController.allApprovedReimbursementsFinder(req, resp);
			break;

		case "/Project1/ajax/empdenied":

			EmployeeController.allDeniedReimbursementsFinder(req, resp);
			break;

		case "/Project1/ajax/empaddreimb":

			EmployeeController.addReimbursement(req, resp);
			break;

		case "/Project1/ajax/getSession":

			LoginController.getSession(req, resp);
			break;
			
		case "/Project1/ajax/endsession":
			
			LoginController.endSession(req, resp);
			break;
			
		default:

			System.out.println("Accidentally hit default");
			resp.getWriter().println("null");
		}
	}
}
