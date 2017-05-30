package com.challenge.dao;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.junit.Rule;
import org.junit.Test;
import com.challenge.pojo.Expense;
import junit.framework.Assert;

public class ExpenseDAOTest {

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

	@Test
	public void testListExpenseInRange() throws ParseException {
		Session session = sessionFactoryRule.getSession();
		String startDate = "2017-05-23";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String endDate = dateFormat.format(date);
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
		Assert.assertEquals(2, expensesInRange.size());
	}
	@Test
	public void testExpensesInLastWeek() {
		double amount = 5;
		double amount1 = 25;
		String description = "movie";
		String description1 = "travel";

		String username = USERNAME + new Timestamp(System.currentTimeMillis()).toString();

		userDAO.createUser(username, PASSWORD, EMAIL);
		expenseDAO.addExpense(username, amount, description);
		expenseDAO.addExpense(username, amount1, description1);

		List<Expense> lastWeekExpenses = expenseDAO.getExpensesForLastWeek(username);
		Assert.assertEquals(30.0, lastWeekExpenses.get(0));
	}

	@Test
	public void testAddAndListExpense() {
		Session session = sessionFactoryRule.getSession();
		session.beginTransaction();
		double amount = 5.75;
		String description = "movie";
		addUser(USERNAME, PASSWORD, EMAIL, session);
		session.beginTransaction();
		expenseDAO.addExpense(USERNAME, amount, description, session);
		List<Expense> list = expenseDAO.getExpenses(USERNAME, session);
		Assert.assertEquals(1, list.size());
	}
}
