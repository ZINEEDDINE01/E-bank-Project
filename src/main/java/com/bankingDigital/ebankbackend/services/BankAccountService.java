package com.bankingDigital.ebankbackend.services;

import java.util.List;

import com.bankingDigital.ebankbackend.dtos.AccountOperationDTO;
import com.bankingDigital.ebankbackend.dtos.BankAccountDTO;
import com.bankingDigital.ebankbackend.dtos.CurrentBankAccountDTO;
import com.bankingDigital.ebankbackend.dtos.CustomerDTO;
import com.bankingDigital.ebankbackend.dtos.SavingBankAccountDTO;
import com.bankingDigital.ebankbackend.exceptions.BalanceNotSufficientException;
import com.bankingDigital.ebankbackend.exceptions.BankAccountNotFoundException;
import com.bankingDigital.ebankbackend.exceptions.CustomerNotFoundException;

public interface BankAccountService {
	//Customer saveCustomer(Customer customer);
	CustomerDTO saveCustomer(CustomerDTO customerDTO);
	//BankAccount saveBankAccount(double initialBalance,String type, Long customerId)throws CustomerNotFoundException;
	/*----------------- a Ajouter----------------*/
	//CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId)throws CustomerNotFoundException;
	//SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId)throws CustomerNotFoundException;
	
	CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId)throws CustomerNotFoundException;
	SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId)throws CustomerNotFoundException;
	
	//List<Customer> listCustomers();
	List<CustomerDTO> listCustomers();
	CustomerDTO getCustomer(Long customerid)throws CustomerNotFoundException;

	//BankAccount getBankAccount(String accountId)throws BankAccountNotFoundException;
	BankAccountDTO getBankAccount(String accountId)throws BankAccountNotFoundException;

	void debit(String accountId, double amount, String description)throws BankAccountNotFoundException,BalanceNotSufficientException;
	void credit(String accountId, double amount, String description)throws BankAccountNotFoundException;
	void transfer(String accountIdSource, String accountIdDestination, double amount)throws BankAccountNotFoundException,BalanceNotSufficientException;
	//List<BankAccount> bankAccountList();
	List<BankAccountDTO> bankAccountList();
	CustomerDTO updateCustomer(CustomerDTO customerDTO);
	void deleteCustomer(Long customerID);
}
