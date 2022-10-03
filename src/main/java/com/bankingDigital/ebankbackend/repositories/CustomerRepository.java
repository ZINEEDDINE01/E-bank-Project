package com.bankingDigital.ebankbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankingDigital.ebankbackend.dtos.CustomerDTO;
import com.bankingDigital.ebankbackend.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
