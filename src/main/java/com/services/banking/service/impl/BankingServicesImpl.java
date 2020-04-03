package com.services.banking.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.banking.dto.CustomerDTO;
import com.services.banking.entity.Customer;
import com.services.banking.repository.AccountRepository;
import com.services.banking.repository.CustomerRepository;
import com.services.banking.repository.TransactionRepository;
import com.services.banking.service.BankingServices;

@Service
public class BankingServicesImpl implements BankingServices {

	@Autowired
	CustomerRepository custRepository;

	@Autowired
	AccountRepository accRepository;

	@Autowired
	TransactionRepository txnRepository;

	@Override
	public String registerCustomer(CustomerDTO customer) {

		ObjectMapper objectMapper = new ObjectMapper();

		Customer cust = objectMapper.convertValue(customer, Customer.class);

		custRepository.saveAndFlush(cust);

		return "Customer regiistration done successfully...";
	}

}
