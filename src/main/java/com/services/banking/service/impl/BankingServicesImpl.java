package com.services.banking.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.banking.dto.AccountDTO;
import com.services.banking.dto.CustomerDTO;
import com.services.banking.entity.Account;
import com.services.banking.entity.Customer;
import com.services.banking.repository.AccountRepository;
import com.services.banking.repository.CustomerRepository;
import com.services.banking.repository.TransactionRepository;
import com.services.banking.service.BankingServices;

@Service
public class BankingServicesImpl implements BankingServices {

	private static ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	CustomerRepository custRepository;

	@Autowired
	AccountRepository accRepository;

	@Autowired
	TransactionRepository txnRepository;

	@Override
	public String registerCustomer(CustomerDTO custDTO) {
		Customer customer = objectMapper.convertValue(custDTO, Customer.class);
		custRepository.saveAndFlush(customer);
		return "Customer regiistration done successfully...";
	}

	@Override
	public String createBackAccountForCustomer(AccountDTO accDTO) {		
		Optional<Customer> custOpt = custRepository.findById(accDTO.getCustId());		
		if(custOpt.isPresent()) {
			Customer cust = custOpt.get();
			Account account = new Account();
			account.setAccNumber(accDTO.getAccNumber());
			account.setBankName(accDTO.getBankName());
			account.setBalance(accDTO.getBalance());
			account.setCustomer(cust);
			accRepository.saveAndFlush(account);
			return "Account has been created successfully...";
		}
		return "Account creation failed.";
	}

}
