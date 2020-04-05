package com.services.banking.service;

import java.util.List;

import com.services.banking.dto.AccountDTO;
import com.services.banking.dto.CustomerDTO;
import com.services.banking.dto.FundTransferDTO;
import com.services.banking.entity.Transaction;

public interface BankingServices {
	
	public String registerCustomer(CustomerDTO custDTO);
	
	public String createAccountForCustomer(AccountDTO accDTO);
	
	public String transferFunds(FundTransferDTO ftDTO);
	
	public List<Transaction> retrieveCustomerBankStatement(int custId);

}
