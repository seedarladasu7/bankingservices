package com.services.banking.service;

import com.services.banking.dto.AccountDTO;
import com.services.banking.dto.CustomerDTO;
import com.services.banking.dto.FundTransferDTO;

public interface BankingServices {
	
	public String registerCustomer(CustomerDTO custDTO);
	
	public String createAccountForCustomer(AccountDTO accDTO);
	
	public String transferFunds(FundTransferDTO ftDTO);

}
