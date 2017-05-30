package com.challenge.dao;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;
import org.hibernate.Session;
import org.junit.Rule;
import com.challenge.pojo.Expense;
import junit.framework.Assert;

public class Test {

	@Rule
	public final SessionFactoryRule sessionFactoryRule = new SessionFactoryRule();
	ExpenseDAO expenseDAO  = new ExpenseDAO();
	UserDAO userDAO = new UserDAO();
	private static final String USERNAME = "test";
	private static final String PASSWORD = "abcd";
	private static final String EMAIL = "test@gmail.com";

	private void addUser(String username, String password, String email, Session session) {
		session.beginTransaction();
		userDAO.createUser(username, password, email, session);
	}

	@org.junit.Test
	public void testListExpenseInRange() throws ParseException {
		Session session = sessionFactoryRule.getSession();
		String startDate = "2017-05-23";
		String endDate = "2017-05-31";
		double amount = 5.75;
		double amount1 = 25;
		String description = "movie";
		String description1 = "travel";
		addUser(USERNAME, PASSWORD, EMAIL, session);

		session.beginTransaction();
		expenseDAO.addExpense(USERNAME, amount, description, session);

		session.beginTransaction();
		expenseDAO.addExpense(USERNAME, amount1, description1, session);

		List<Expense> expensesInRange = expenseDAO.listExpensesInRange(USERNAME, startDate, endDate, session);
		System.out.println(expensesInRange);
		Assert.assertEquals(2, expensesInRange.size());
	}

	
}
