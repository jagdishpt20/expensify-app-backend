package com.jss.expensifyappbackend.service;

import java.util.List;

import com.jss.expensifyappbackend.entity.Expense;

public interface ExpenseService {
	Expense addExpense(Expense expense);
	void updateExpense(String expenseId , Expense updatedExpense);
	void removeExpense(String expenseId);
	List<Expense> getExpensesByUserId(long userId);
}
