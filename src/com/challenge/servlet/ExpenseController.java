package com.challenge.servlet;

import java.util.*;
import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.json.JSONArray;
import com.challenge.dao.ExpenseDAO;
import com.challenge.pojo.Expense;

/**
 * Servlet implementation class ExpenseController
 */
@WebServlet("/ExpenseController")
public class ExpenseController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExpenseController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		List<Expense> expenses = new ArrayList<>();
		try {
			 expenses = new ExpenseDAO().listExpensesInRange(username, startDate, endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		List<Map> list = new ArrayList<>();
		if (expenses.size() > 0) {
			for (int i = 0; i < expenses.size(); i++) {
				Map map = new HashMap();
				Expense expense = expenses.get(i);
				map.put("amount", expense.getAmount());
				map.put("item", expense.getDescription());
				map.put("date", expense.getCreated());
				map.put("id", expense.getId());
				list.add(map);
			}
			JSONArray json = new JSONArray(list);
//			System.out.println(json);
			response.getWriter().write(json.toString());
			} 
//		response.getWriter().write(list.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String item = request.getParameter("item");
		String amount = request.getParameter("amount");
		double price = Double.parseDouble(amount);
		ExpenseDAO expense = new ExpenseDAO();
		expense.addExpense(username, price, item);
		response.setContentType("text");
		response.getWriter().write(response.getStatus());
	}
}
