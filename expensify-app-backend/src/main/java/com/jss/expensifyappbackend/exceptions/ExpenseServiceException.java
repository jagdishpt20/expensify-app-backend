package com.jss.expensifyappbackend.exceptions;

public class ExpenseServiceException extends RuntimeException {

	private static final long serialVersionUID = 2504358420363254503L;

	public ExpenseServiceException(String message) {
		super(message);
	}
}
