package com.services.banking.service;

import com.services.banking.dto.AccountDTO;
import com.services.banking.dto.CustomerDTO;

public interface BankingServices {
	
	public String registerCustomer(CustomerDTO custDTO);
	
	public String createBackAccountForCustomer(AccountDTO accDTO);

}
