package com.jss.expensifyappbackend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jss.expensifyappbackend.entity.Expense;
import com.jss.expensifyappbackend.service.ExpenseService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
	
	@Autowired
	ExpenseService expenseService;
	
	@ApiOperation(value="Add Expense Web Service Endpoint", notes="${expenseController.addExpense.ApiOperation.notes}")
	@PostMapping(value="/create")
	public Expense addExpense(@RequestBody Expense expense) {
		return expenseService.addExpense(expense);
	}
	
	@ApiOperation(value="Edit Expense Web Service Endpoint", notes="${expenseController.editExpense.ApiOperation.notes}")
	@PutMapping(value="/edit/{expenseId}")
	public void editExpense(@PathVariable String expenseId, @RequestBody Expense updatedExpense) {
		expenseService.updateExpense(expenseId, updatedExpense);
	}
	
	@ApiOperation(value="Remove Expense Web Service Endpoint", notes="${expenseController.removeExpense.ApiOperation.notes}")
	@DeleteMapping(value="/remove/{expenseId}")
	public void removeExpense(@PathVariable String expenseId) {
		expenseService.removeExpense(expenseId);
	}
	
	@ApiOperation(value="Get Expenses Web Service Endpoint", notes="${expenseController.getExpensesByUserId.ApiOperation.notes}")
	@GetMapping(value="/getExpensesByUserId/{userId}")
	public List<Expense> getExpensesByUserId(@PathVariable long userId) {
		return expenseService.getExpensesByUserId(userId);
	}	
}
