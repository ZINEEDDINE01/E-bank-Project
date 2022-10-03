package com.bankingDigital.ebankbackend.dtos;

import java.util.Date;

import com.bankingDigital.ebankbackend.enums.AccountStatus;

import lombok.Data;

@Data
public class CurrentBankAccountDTO extends BankAccountDTO{ 
	private String id;
	private Date createdAt;
	private double balance;
	private AccountStatus status;
	private CustomerDTO customerDTO;
	private double overDraft;
}
