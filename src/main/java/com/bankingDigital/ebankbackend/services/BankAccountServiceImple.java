package com.bankingDigital.ebankbackend.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankingDigital.ebankbackend.dtos.BankAccountDTO;
import com.bankingDigital.ebankbackend.dtos.CurrentBankAccountDTO;
import com.bankingDigital.ebankbackend.dtos.CustomerDTO;
import com.bankingDigital.ebankbackend.dtos.SavingBankAccountDTO;
import com.bankingDigital.ebankbackend.entities.AccountOperation;
import com.bankingDigital.ebankbackend.entities.BankAccount;
import com.bankingDigital.ebankbackend.entities.CurrentAccount;
import com.bankingDigital.ebankbackend.entities.Customer;
import com.bankingDigital.ebankbackend.entities.SavingAccount;
import com.bankingDigital.ebankbackend.enums.OperationType;
import com.bankingDigital.ebankbackend.exceptions.BalanceNotSufficientException;
import com.bankingDigital.ebankbackend.exceptions.BankAccountNotFoundException;
import com.bankingDigital.ebankbackend.exceptions.CustomerNotFoundException;
import com.bankingDigital.ebankbackend.mappers.BankAccountMapperImpl;
import com.bankingDigital.ebankbackend.repositories.AccountOperationRepository;
import com.bankingDigital.ebankbackend.repositories.BankAccountRepository;
import com.bankingDigital.ebankbackend.repositories.CustomerRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImple implements BankAccountService {
	
	private BankAccountRepository bankAccountRepository;
	private AccountOperationRepository accountOperationRepository;
	private CustomerRepository customerRepository;
	private BankAccountMapperImpl dtoMapper;   
	//Logger log = LoggerFactory.getLogger(this.getClass().getName());
	  
	@Override
	public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
		log.info("Saving new Customer");
		Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
		Customer savedCustomer = customerRepository.save(customer);
		return dtoMapper.fromCustomer(savedCustomer);
	}
	@Override
	public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
		Customer customer = customerRepository.findById(customerId).orElse(null);
		if(customer == null) throw new CustomerNotFoundException("Customer Not Found");
		CurrentAccount currentAccount = new CurrentAccount();
		currentAccount.setId(UUID.randomUUID().toString());
		currentAccount.setCreatedAt(new Date());
		currentAccount.setBalance(initialBalance);
		currentAccount.setCustomer(customer);
		currentAccount.setOverDraft(overDraft);
		CurrentAccount savedCurrentAccount = bankAccountRepository.save(currentAccount);
		return dtoMapper.fromCurrentAccount(savedCurrentAccount);
	}
	
	@Override
	public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId)
			throws CustomerNotFoundException {
		Customer customer = customerRepository.findById(customerId).orElse(null);
		if(customer == null) throw new CustomerNotFoundException("Customer Not Found");
		SavingAccount savingAccount = new SavingAccount();
		savingAccount.setId(UUID.randomUUID().toString());
		savingAccount.setCreatedAt(new Date());
		savingAccount.setBalance(initialBalance);
		savingAccount.setCustomer(customer);
		savingAccount.setInterestRate(interestRate);
		SavingAccount savedSavingAccount = bankAccountRepository.save(savingAccount);
		return dtoMapper.fromSavingAccount(savedSavingAccount);
	}
	@Override
	public List<CustomerDTO> listCustomers() {
		List<Customer> customers = customerRepository.findAll();
		 //programmation fonctionnel
		List<CustomerDTO> customerDTOS = customers.stream()
				.map(customer ->dtoMapper.fromCustomer(customer))
				.collect(Collectors.toList());
		 //Programmation impérative
		List<CustomerDTO> customerDTOsS = new ArrayList<>();
		for(Customer customer:customers) {
			CustomerDTO customerDTO = dtoMapper.fromCustomer(customer);
			customerDTOsS.add(customerDTO);
		}
		return customerDTOS;
		
	}
	
	@Override
	public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException{
		BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(()-> new BankAccountNotFoundException("Bank Account not found"));
		if(bankAccount instanceof CurrentAccount) {
			CurrentAccount currentAccount = (CurrentAccount) bankAccount;
			return dtoMapper.fromCurrentAccount(currentAccount);
		}else {
			SavingAccount savingAccount = (SavingAccount) bankAccount;
			return dtoMapper.fromSavingAccount(savingAccount);
		}
	}

	@Override
	public void debit(String accountId, double amount, String description)throws BankAccountNotFoundException,BalanceNotSufficientException {
		BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(()-> new BankAccountNotFoundException("Bank Account not found"));
		if(bankAccount.getBalance()<amount) throw new BalanceNotSufficientException("balance not sufficient");
		//pour debiter de l'argent:
		AccountOperation accountOperation = new AccountOperation();
		accountOperation.setDate(new Date());
		accountOperation.setAmount(amount);
		accountOperation.setDescription(description);
		accountOperation.setType(OperationType.DEBIT);
		accountOperation.setBankaccount(bankAccount);
		accountOperationRepository.save(accountOperation);
		bankAccount.setBalance(bankAccount.getBalance()-amount);
		bankAccountRepository.save(bankAccount);
	}

	@Override
	public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException{
		BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(()-> new BankAccountNotFoundException("Bank Account not found"));
		//pour crediter de l'argent:
		AccountOperation accountOperation = new AccountOperation();
		accountOperation.setDate(new Date());
		accountOperation.setAmount(amount);
		accountOperation.setDescription(description);
		accountOperation.setType(OperationType.CREDIT);
		accountOperation.setBankaccount(bankAccount);
		accountOperationRepository.save(accountOperation);
		bankAccount.setBalance(bankAccount.getBalance()+ amount);
		bankAccountRepository.save(bankAccount);
	}

	@Override
	public void transfer(String accountIdSource, String accountIdDestination, double amount)throws BankAccountNotFoundException,BalanceNotSufficientException {
		credit(accountIdSource, amount,"Transfer to "+ accountIdDestination);
		debit(accountIdDestination, amount,"Transfer from "+ accountIdSource);
	}
	@Override
	public List<BankAccountDTO> bankAccountList(){
		List<BankAccount> bankaccounts = bankAccountRepository.findAll();
		List<BankAccountDTO> bankAccountDTOS = new ArrayList<>();
		for(BankAccount bankaccount : bankaccounts) {
			if(bankaccount instanceof CurrentAccount) {
				CurrentAccount currentaccount = (CurrentAccount) bankaccount;
				bankAccountDTOS.add(dtoMapper.fromCurrentAccount(currentaccount));
			}else {
				SavingAccount savingaccount = (SavingAccount) bankaccount;
				bankAccountDTOS.add(dtoMapper.fromSavingAccount(savingaccount));
			}
		}
		 return bankAccountDTOS;
	}

	@Override
	public CustomerDTO getCustomer(Long customerid) throws CustomerNotFoundException {
		Customer customer = customerRepository.findById(customerid)
		.orElseThrow(()-> new CustomerNotFoundException("Customer not found"));
		return dtoMapper.fromCustomer(customer);
	}
	
	@Override
	public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
		log.info("Saving new Customer");
		Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
		Customer savedCustomer = customerRepository.save(customer);
		return dtoMapper.fromCustomer(savedCustomer);
	}
	
	@Override
	public void deleteCustomer(Long customerID) {
		customerRepository.deleteById(customerID);
	}

}
