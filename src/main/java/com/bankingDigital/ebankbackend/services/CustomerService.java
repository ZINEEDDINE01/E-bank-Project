package com.bankingDigital.ebankbackend.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankingDigital.ebankbackend.entities.BankAccount;
import com.bankingDigital.ebankbackend.entities.CurrentAccount;
import com.bankingDigital.ebankbackend.entities.SavingAccount;
import com.bankingDigital.ebankbackend.repositories.BankAccountRepository;
import com.bankingDigital.ebankbackend.repositories.CustomerRepository;

@Service
@Transactional
public class CustomerService {
	BankAccountRepository bankAccountRepository;
	public void consulter() {
		BankAccount bankaccount = 
				bankAccountRepository.findById("00373ce2-f456-45f8-8d64-65e06dad1d3a").orElse(null);
		System.out.println("*************************");
		System.out.println(bankaccount.getId());
		System.out.println(bankaccount.getBalance());
		System.out.println(bankaccount.getCreatedAt());
		System.out.println(bankaccount.getStatus());
		System.out.println(bankaccount.getCustomer().getName());
		System.out.println(bankaccount.getClass().getSimpleName());
		if(bankaccount instanceof CurrentAccount) {
			System.out.println("overDraft :"+((CurrentAccount)bankaccount).getOverDraft());
		} else if(bankaccount instanceof SavingAccount) {
			System.out.println("interestRate :"+((SavingAccount)bankaccount).getInterestRate());
		}
		
		 	bankaccount.getAccountOperations().forEach(op ->{
			System.out.println("==========Operation==========");
			System.out.println(op.getType());
			System.out.println(op.getAmount());
			System.out.println(op.getDate());
			System.out.println(op.getBankaccount());
		});
	}
}
