package com.bankingDigital.ebankbackend.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@DiscriminatorValue(value = "SAV")
@Data @NoArgsConstructor @AllArgsConstructor
public class SavingAccount extends BankAccount {
	private double interestRate;
}
