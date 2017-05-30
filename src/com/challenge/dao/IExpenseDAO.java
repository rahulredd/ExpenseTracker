package com.challenge.dao;

import java.util.List;

import com.challenge.pojo.Expense;

public interface IExpenseDAO {

	public boolean addExpense(String username, double amount, String description);
	public List<Expense> listExpenses(String username);
	public boolean updateExpense(String username, int txId, double updateAmount);
	public boolean deleteExpense(String username, int txId);
}
