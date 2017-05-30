package com.challenge.dao;

import java.util.List;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.exception.SQLGrammarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.challenge.db.DbUtils;
import com.challenge.pojo.Expense;
import com.challenge.pojo.User;

public class ExpenseDAO implements IExpenseDAO {
	public static final Logger LOG = LoggerFactory.getLogger(ExpenseDAO.class);
	private static Session session;

	private static void beginSession() {
		session = DbUtils.getSessionFactory().openSession();
		session.beginTransaction();
	}

	@Override
	public boolean addExpense(String username, double amount, String description) {
		beginSession();
		boolean isExpenseAdded = addExpense(username, amount, description, session);
		session.close();
		return isExpenseAdded;
	}

	@Override
	public List<Expense> listExpenses(String username) {
		beginSession();
		List<Expense> list = getExpenses(username);
		session.close();
		return list;
	}

	public List<Expense> getExpensesForLastWeek(String username) {
		beginSession();
		List<Expense> list = getExpensesForLastWeek(username, session);
		session.close();
		return list;
	}

	public List<Expense> getExpensesForLastWeek(String username, Session session) {
		String query = "select SUM(amount) from Expense INNER JOIN User ON Expense.user_id = User.id AND user.username = ? WHERE created >= curdate() - INTERVAL DAYOFWEEK(curdate()) DAY AND created < curdate() - INTERVAL DAYOFWEEK(curdate()) - 7 DAY";
		List result = session.createSQLQuery(query).setParameter(0, username).list();
		return result;
	}

	private String getNextDayBeginning(String endDate) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(df.parse(endDate));
		c.add(Calendar.DATE, 1);  // number of days to add
		return df.format(c.getTime());
	}

	private Date convertStringToDate(String date) throws ParseException {
		DateFormat datePickerFormat = new SimpleDateFormat("yyyy-MM-dd");
		return datePickerFormat.parse(date);
	}

	private Date convertMySQLTimeToDate(Date timeStamp) throws ParseException {
		DateFormat mysqlFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = mysqlFormat.format(timeStamp);
		return mysqlFormat.parse(time);
	}

	@SuppressWarnings("deprecation")
	public List<Expense> listExpensesInRange(String username, String startDate, String endDate) throws ParseException {
		beginSession();
		List<Expense> expensesInRange = listExpensesInRange(username, startDate, endDate, session);
		session.close();
		return expensesInRange;
	}
	
	public List<Expense> listExpensesInRange(String username, String startDate, String endDate, Session session) throws ParseException {
		List<Expense> expenses = getExpenses(username, session);
		List<Expense> expensesInRange = new ArrayList<>();
		Date start = convertStringToDate(startDate);
		Date end =  convertStringToDate(getNextDayBeginning(endDate));
		for (int i = expenses.size() - 1; i >= 0; i--) {
			Date timeStamp = expenses.get(i).getCreated();
			Date formattedTime = convertMySQLTimeToDate(timeStamp);
			if (formattedTime.compareTo(start) > 0 && formattedTime.compareTo(end) <= 0) {
				expensesInRange.add(expenses.get(i));
			}
		}
		return expensesInRange;
	}

	public List<Expense> getExpenses(String username) {
		beginSession();
		List<Expense> expenses = getExpenses(username, session);
		session.close();
		return expenses;
	}

	public List<Expense> getExpenses(String username, Session session) {
		UserDAO userDAO = new UserDAO();
		User user = userDAO.findUser(username, session);
		return user.getExpenses();
	}
	
	public boolean addExpense(String username, double amount, String description, Session session) {
		Expense expense = new Expense();
		UserDAO userDAO = new UserDAO();
		User existingUser = userDAO.findUser(username, session);
		
		expense.setAmount(amount);
		expense.setDescription(description);
		expense.setUser(existingUser);
		
		existingUser.getExpenses().add(expense);

		try {
			session.save(expense);
			session.getTransaction().commit();
		} catch (SQLGrammarException e) {
			session.getTransaction().rollback();
			LOG.error("Cannot save user", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateExpense(String username, int txId, double updateAmount) {
		beginSession();
		boolean success= updateExpense(username, txId, updateAmount, session);
		session.close();
		return success;
	}

	public boolean updateExpense(String username, int txId, double updateAmount, Session session) {
		String query = "update Expense INNER JOIN User on  Expense.user_id = (select id from user where username = ?) set amount = ? where Expense.id = ?;";
		SQLQuery sql = session.createSQLQuery(query);
		sql.setParameter(0, username);
		sql.setParameter(1, updateAmount);
		sql.setParameter(2, txId);
		int result = 0;
		try {
			result = sql.executeUpdate();
			session.getTransaction().commit();
		} catch (SQLGrammarException e) {
			session.getTransaction().rollback();
			LOG.error("Cannot Update tx", e);
			return false;
		}
		return result > 0;
	}

	@Override
	public boolean deleteExpense(String username, int txId) {
		beginSession();
		boolean success = deleteExpense(username, txId, session);
		session.close();
		return success;
	}

	public boolean deleteExpense(String username, int txId, Session session) {
		String query = "delete from Expense where id = ? AND Expense.user_id = (select id from user where username = ?);";
		SQLQuery sql = session.createSQLQuery(query);
		sql.setParameter(0, txId);
		sql.setParameter(1, username);
		int result = 0;
		try {
			result = sql.executeUpdate();
			session.getTransaction().commit();
		} catch (SQLGrammarException e) {
			session.getTransaction().rollback();
			LOG.error("Cannot delete tx", e);
			return false;
		}
		return result > 0;
	}
}
