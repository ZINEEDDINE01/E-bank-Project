package com.bankingDigital.ebankbackend.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankingDigital.ebankbackend.dtos.AccountOperationDTO;
import com.bankingDigital.ebankbackend.dtos.BankAccountDTO;
import com.bankingDigital.ebankbackend.dtos.CurrentBankAccountDTO;
import com.bankingDigital.ebankbackend.dtos.SavingBankAccountDTO;
import com.bankingDigital.ebankbackend.entities.AccountOperation;
import com.bankingDigital.ebankbackend.entities.BankAccount;
import com.bankingDigital.ebankbackend.entities.CurrentAccount;
import com.bankingDigital.ebankbackend.exceptions.BankAccountNotFoundException;
import com.bankingDigital.ebankbackend.exceptions.CustomerNotFoundException;
import com.bankingDigital.ebankbackend.services.BankAccountService;

import lombok.AllArgsConstructor;
@RestController
@AllArgsConstructor
public class BankAccountRestAPI {
	private BankAccountService bankAccountService;
	
	@GetMapping("/accounts/{accoundId}")
	public BankAccountDTO getBankAccount(@PathVariable String accoundId) throws BankAccountNotFoundException {
		return bankAccountService.getBankAccount(accoundId);
	}
	@GetMapping("/accounts")
	public List<BankAccountDTO> bankAccountList(){
		return bankAccountService.bankAccountList();
	}
	
}
