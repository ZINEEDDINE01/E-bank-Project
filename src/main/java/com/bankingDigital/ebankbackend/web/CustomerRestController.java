package com.bankingDigital.ebankbackend.web;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bankingDigital.ebankbackend.dtos.CustomerDTO;
import com.bankingDigital.ebankbackend.entities.Customer;
import com.bankingDigital.ebankbackend.exceptions.CustomerNotFoundException;
import com.bankingDigital.ebankbackend.services.BankAccountService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@AllArgsConstructor
@Slf4j
@RestController
public class CustomerRestController {
	private BankAccountService bankAccountService;
	@GetMapping("/customers")
	public List<CustomerDTO> customers(){
		return bankAccountService.listCustomers();
	}
	@GetMapping("/customers/{id}")
	public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerid) throws CustomerNotFoundException {
		return bankAccountService.getCustomer(customerid);
	}
	@PostMapping("/customers")
	public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) { 
		return bankAccountService.saveCustomer(customerDTO);
	}
	@PutMapping("/customers/{customerId}")
	public CustomerDTO updateCustomer(@PathVariable Long customerId , @RequestBody CustomerDTO customerDTO) { 
		customerDTO.setId(customerId);
		return bankAccountService.updateCustomer(customerDTO);
	}
	@DeleteMapping("/customers/{id}")
	public void deleteCustomer(@PathVariable(name = "id") Long customerid)throws CustomerNotFoundException {
		bankAccountService.deleteCustomer(customerid);
	}
	
}
