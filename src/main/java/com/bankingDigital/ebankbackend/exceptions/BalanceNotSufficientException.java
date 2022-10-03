package com.bankingDigital.ebankbackend.exceptions;

public class BalanceNotSufficientException extends Exception{
	public BalanceNotSufficientException(String msg) {
		super(msg);
	}
}
