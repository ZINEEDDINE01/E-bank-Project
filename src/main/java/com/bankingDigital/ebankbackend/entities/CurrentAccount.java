package com.bankingDigital.ebankbackend.entities;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue(value = "CUR")
@Data @NoArgsConstructor @AllArgsConstructor
public class CurrentAccount extends BankAccount {
	private double overDraft;
}
