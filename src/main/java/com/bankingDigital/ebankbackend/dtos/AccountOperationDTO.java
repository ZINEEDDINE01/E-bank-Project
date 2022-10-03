package com.bankingDigital.ebankbackend.dtos;

import java.util.Date;
import com.bankingDigital.ebankbackend.enums.OperationType;

import lombok.Data;


@Data
public class AccountOperationDTO {
	private long id;
	private Date date;
	private double amount;
	private OperationType type;
	private String description;
}
