package com.jss.expensifyappbackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jss.expensifyappbackend.entity.Expense;
import com.jss.expensifyappbackend.repository.ExpenseRepository;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	ExpenseRepository expenseRepo;
	
	@Override
	@Transactional
	public Expense addExpense(Expense expense) {
		Expense returnedExpense = expenseRepo.save(expense);
		return returnedExpense;
	}
	
	@Transactional	
	@Override
	public void updateExpense(String expenseId, Expense updatedExpense) {
		Optional<Expense> expense = expenseRepo.findByExpenseId(expenseId);
		if(expense.isPresent()) {
			Expense exp = expense.get();
			exp.setExpenseId(updatedExpense.getExpenseId());
			exp.setDescription(updatedExpense.getDescription());
			exp.setAmount(updatedExpense.getAmount());
			exp.setNote(updatedExpense.getNote());
			exp.setCreatedAt(updatedExpense.getCreatedAt());
		}
	}

	@Transactional
	@Override
	public void removeExpense(String expenseId) {
		expenseRepo.deleteByExpenseId(expenseId);
	}

	@Transactional
	@Override
	public List<Expense> getExpensesByUserId(long userId) {
		return expenseRepo.findByUserId(userId);
	}
}
