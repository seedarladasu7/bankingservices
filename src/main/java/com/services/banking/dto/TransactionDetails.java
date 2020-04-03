package com.services.banking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetails {
	private String txnDate;
	private float txnAmount;
	private String fromAcc;
	private String toAcc;
}
