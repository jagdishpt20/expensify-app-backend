package com.jss.expensifyappbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jss.expensifyappbackend.entity.Expense;

@Repository
public interface ExpenseRepository extends CrudRepository<Expense, Long>{
	Optional<Expense> findByExpenseId(String expenseId);
	void deleteByExpenseId(String expenseId);
	List<Expense> findByUserId(long userId);
}
