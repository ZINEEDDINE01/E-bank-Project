package com.bankingDigital.ebankbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankingDigital.ebankbackend.entities.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, String>{

}
