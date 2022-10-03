package com.bankingDigital.ebankbackend;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.List;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.bankingDigital.ebankbackend.dtos.BankAccountDTO;
import com.bankingDigital.ebankbackend.dtos.CurrentBankAccountDTO;
import com.bankingDigital.ebankbackend.dtos.CustomerDTO;
import com.bankingDigital.ebankbackend.dtos.SavingBankAccountDTO;
import com.bankingDigital.ebankbackend.entities.AccountOperation;
import com.bankingDigital.ebankbackend.entities.CurrentAccount;
import com.bankingDigital.ebankbackend.entities.Customer;
import com.bankingDigital.ebankbackend.entities.SavingAccount;
import com.bankingDigital.ebankbackend.enums.AccountStatus;
import com.bankingDigital.ebankbackend.enums.OperationType;
import com.bankingDigital.ebankbackend.exceptions.CustomerNotFoundException;
import com.bankingDigital.ebankbackend.repositories.AccountOperationRepository;
import com.bankingDigital.ebankbackend.repositories.BankAccountRepository;
import com.bankingDigital.ebankbackend.repositories.CustomerRepository;
import com.bankingDigital.ebankbackend.services.BankAccountService;

@SpringBootApplication
public class EbankBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankBackendApplication.class, args);
	}
	
	@Bean
	CommandLineRunner start(BankAccountService bankAccountService) {
		return args -> {
			//bankAccountService.consulter();
				
			Stream.of("ABDE EZZALZOULI","ZINEEDDINE LOURIGA","ACHRAF EL HADY").forEach(name->{
					CustomerDTO customer = new CustomerDTO();
					customer.setName(name);
					customer.setEmail(name+"@gmail.com");
					
					bankAccountService.saveCustomer(customer);
				});
			
			bankAccountService.listCustomers().forEach(customer ->{
				try {
					bankAccountService.saveCurrentBankAccount(Math.random()*90000, 9000, customer.getId());
					bankAccountService.saveSavingBankAccount(Math.random()*120000, 5.5, customer.getId());
					} catch (CustomerNotFoundException e) {
						e.printStackTrace();
					} 
			});
			
			List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();
			for(BankAccountDTO bankAccount:bankAccounts) {
				for(int i= 0; i <10; i++) {
					String accountId;
					if(bankAccount instanceof SavingBankAccountDTO) {
						accountId = ((SavingBankAccountDTO) bankAccount).getId();
					}else {
						accountId = ((CurrentBankAccountDTO) bankAccount).getId();
					}
						bankAccountService.credit(accountId,10000+Math.random()*120000,"Credit");
						bankAccountService.debit(accountId, 1000+Math.random()*9000, "Debit");
				}
			}
				
		};
	}
	
	//@Bean
	CommandLineRunner start(CustomerRepository customerRepository,
							BankAccountRepository bankAccountRepository,
							AccountOperationRepository accountOperationRepository) {
		return args -> {
			Stream.of("Hassan","ZINEEDDINE","SANA").forEach(name->{
				Customer customer = new Customer();
				customer.setName(name);
				customer.setEmail(name+"@gmail.com");
				customerRepository.save(customer);
			});
			
			customerRepository.findAll().forEach(cust ->{
				CurrentAccount currentaccount = new CurrentAccount();
				currentaccount.setId(UUID.randomUUID().toString());
				currentaccount.setBalance(Math.random()*90000);
				currentaccount.setCreatedAt(new Date());
				currentaccount.setStatus(AccountStatus.CREATED);
				currentaccount.setOverDraft(9000);
				currentaccount.setCustomer(cust);
				//currentaccount.setCurrency("DH");
				bankAccountRepository.save(currentaccount);
				
				SavingAccount savingaccount = new SavingAccount();
				savingaccount.setId(UUID.randomUUID().toString());
				savingaccount.setBalance(Math.random()*90000);
				savingaccount.setCreatedAt(new Date());
				savingaccount.setStatus(AccountStatus.CREATED);
				savingaccount.setInterestRate(5.4);
				savingaccount.setCustomer(cust);
				//currentaccount.setCurrency("DH");
				bankAccountRepository.save(savingaccount);
			});
				bankAccountRepository.findAll().forEach(acc ->{
					for(int i=0;i<5;i++) {
						AccountOperation accountoperation = new AccountOperation();
						accountoperation.setDate(new Date());
						accountoperation.setAmount(Math.random()*12000);
						accountoperation.setType(Math.random()>0.5? OperationType.DEBIT: OperationType.CREDIT);
						accountoperation.setBankaccount(acc);
						accountOperationRepository.save(accountoperation);
					}
					
				});

		};
	}
	
		
}
