package com.bankingDigital.ebankbackend.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.bankingDigital.ebankbackend.dtos.AccountOperationDTO;
import com.bankingDigital.ebankbackend.dtos.CurrentBankAccountDTO;
import com.bankingDigital.ebankbackend.dtos.CustomerDTO;
import com.bankingDigital.ebankbackend.dtos.SavingBankAccountDTO;
import com.bankingDigital.ebankbackend.entities.AccountOperation;
import com.bankingDigital.ebankbackend.entities.CurrentAccount;
import com.bankingDigital.ebankbackend.entities.Customer;
import com.bankingDigital.ebankbackend.entities.SavingAccount;
@Service
public class BankAccountMapperImpl {
	
	public CustomerDTO fromCustomer(Customer customer) {
		CustomerDTO customerDTO = new CustomerDTO();
		//customerDTO.setId(customer.getId());
		//customerDTO.setName(customer.getName());
		//customerDTO.setEmail(customer.getEmail());
		//au lieu d'utiliser bcp de set et get on utilise l'objet de spring beanUtils
		BeanUtils.copyProperties(customer, customerDTO);
		return customerDTO;
	}
	
	public Customer fromCustomerDTO(CustomerDTO customerDTO) {
		Customer customer = new Customer();
		BeanUtils.copyProperties(customerDTO, customer);
		return customer;
	}
	
	public SavingBankAccountDTO fromSavingAccount(SavingAccount savingaccount) {
		SavingBankAccountDTO SavingbankaccountDTO = new SavingBankAccountDTO();
		BeanUtils.copyProperties(savingaccount, SavingbankaccountDTO);
		SavingbankaccountDTO.setCustomerDTO(fromCustomer(savingaccount.getCustomer()));
		SavingbankaccountDTO.setType(savingaccount.getClass().getSimpleName());
		return SavingbankaccountDTO;
	}
	
	public SavingAccount fromSavingAccountDTO(SavingBankAccountDTO savingbankaccountDTO) {
		SavingAccount savingaccount = new SavingAccount();
		BeanUtils.copyProperties(savingbankaccountDTO,savingaccount);
		savingaccount.setCustomer(fromCustomerDTO(savingbankaccountDTO.getCustomerDTO()));
		return savingaccount;
	}
	
	public CurrentBankAccountDTO fromCurrentAccount(CurrentAccount currentaccount) {
		CurrentBankAccountDTO currentaccountDTO = new CurrentBankAccountDTO();
		BeanUtils.copyProperties(currentaccount, currentaccountDTO);
		currentaccountDTO.setCustomerDTO(fromCustomer(currentaccount.getCustomer()));
		currentaccountDTO.setType(currentaccount.getClass().getSimpleName());
		return currentaccountDTO;
	}
	
	public CurrentAccount fromCurrentAccountDTO(CurrentBankAccountDTO currentbankaccountDTO) {
		CurrentAccount currentaccount = new CurrentAccount();
		BeanUtils.copyProperties(currentbankaccountDTO, currentaccount);
		currentaccount.setCustomer(fromCustomerDTO(currentbankaccountDTO.getCustomerDTO()));
		return currentaccount;
	}
	
	public AccountOperation fromAccountOperationDTO(AccountOperationDTO accountoperationDTO) {
		AccountOperation accountoperation = new AccountOperation();
		BeanUtils.copyProperties(accountoperationDTO, accountoperation);
		return accountoperation;
	}

	public AccountOperationDTO fromAccountOperation(AccountOperation accountoperation) {
		AccountOperationDTO accountoperationDTO = new AccountOperationDTO();
		BeanUtils.copyProperties(accountoperation, accountoperationDTO);
		return accountoperationDTO;
	}
	
}
