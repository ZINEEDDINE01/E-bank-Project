package com.bankingDigital.ebankbackend.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.bankingDigital.ebankbackend.enums.AccountStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
/*
ici on ajoute le mot cle abstract pour n'est pas créer la table 
BankAccount
*/
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE",length = 4)
@Data @NoArgsConstructor @AllArgsConstructor
public abstract class BankAccount {
	@Id 
	private String id;
	private Date createdAt;
	private double balance;
	private String currency;
	@Enumerated(EnumType.STRING)
	private AccountStatus status;
	@ManyToOne
	private Customer customer;
	@OneToMany(mappedBy = "bankaccount",fetch = FetchType.LAZY)
	private List<AccountOperation>accountOperations;
}
