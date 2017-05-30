package com.challenge.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.challenge.dao.ExpenseDAO;

/**
 * Servlet implementation class DeleteExpense
 */
@WebServlet("/DeleteExpense")
public class DeleteExpense extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteExpense() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String id = request.getParameter("id");
		
		ExpenseDAO expenseDAO = new ExpenseDAO();
		int txId = Integer.parseInt(id);
		boolean success = expenseDAO.deleteExpense(username, txId);
		if (success) {
			response.getWriter().write("1");
    } else {
			response.getWriter().write("0");
    }
	}

}
