package com.jss.expensifyappbackend.service;


import static org.mockito.Mockito.when;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;


import org.hibernate.type.AnyType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.jss.expensifyappbackend.entity.Expense;
import com.jss.expensifyappbackend.repository.ExpenseRepository;

@TestInstance(Lifecycle.PER_CLASS)
public class ExpenseServiceImplTest {
	
	@Mock
	ExpenseRepository expRepo;
	
	@InjectMocks
	ExpenseServiceImpl expService;
	
	Expense expense;
	
	@BeforeAll
	void init() {
		expense = new Expense(1, "hjg6687", "Rent", 500, "July rent", LocalDate.now(), 1);
	}
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	void addExpenseTest() {
		when(expRepo.save(any(Expense.class))).thenReturn(expense);
		
		Expense returnedExpense = expService.addExpense(expense);
		assertEquals(expense.getAmount(), returnedExpense.getAmount());
	}
}
