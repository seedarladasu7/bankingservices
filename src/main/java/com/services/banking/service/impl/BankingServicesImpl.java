package com.services.banking.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.banking.dto.AccountDTO;
import com.services.banking.dto.CustomerDTO;
import com.services.banking.dto.FundTransferDTO;
import com.services.banking.entity.Account;
import com.services.banking.entity.Customer;
import com.services.banking.entity.Transaction;
import com.services.banking.repository.AccountRepository;
import com.services.banking.repository.CustomerRepository;
import com.services.banking.repository.TransactionRepository;
import com.services.banking.service.BankingServices;

@Service
public class BankingServicesImpl implements BankingServices {

	SimpleDateFormat simpDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
	public String createAccountForCustomer(AccountDTO accDTO) {
		Optional<Customer> custOpt = custRepository.findById(accDTO.getCustId());
		if (custOpt.isPresent()) {
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

	@Override
	public String transferFunds(FundTransferDTO ftDTO) {

		String message = "";

		Account fromAcc = accRepository.findByAccNumber(ftDTO.getFromAccount());

		Account toAcc = accRepository.findByAccNumber(ftDTO.getToAccount());

		float accBalance = fromAcc.getBalance();
		float txnAmount = ftDTO.getTxnAmount();

		if (fromAcc == null || toAcc == null) {
			message = "Invalid Bank details provided...";
		} else if (accBalance < ftDTO.getTxnAmount()) {
			message = "Insufficient balance...";
		}

		if (StringUtils.isNotEmpty(message)) {
			return message;
		} else {
			java.sql.Timestamp txnDateTS = java.sql.Timestamp.valueOf(simpDate.format(new Date()));
			
			if(fromAcc != null) {
				fromAcc.setBalance(fromAcc.getBalance() - ftDTO.getTxnAmount());
				accRepository.saveAndFlush(fromAcc);
	
				Transaction dtTxn = new Transaction();
				dtTxn.setAccount(fromAcc);
				dtTxn.setTxnType("Debit");
				dtTxn.setTxnDate(txnDateTS);
				dtTxn.setFromAccount(ftDTO.getFromAccount());
				dtTxn.setToAccount(ftDTO.getToAccount());
				dtTxn.setTxnAmount(txnAmount);
				txnRepository.saveAndFlush(dtTxn);
			}
			
			if (toAcc != null) {
				toAcc.setBalance(toAcc.getBalance() + ftDTO.getTxnAmount());
				accRepository.saveAndFlush(toAcc);

				Transaction crTxn = new Transaction();
				crTxn.setAccount(toAcc);
				crTxn.setTxnType("Credit");
				crTxn.setTxnDate(txnDateTS);
				crTxn.setFromAccount(ftDTO.getFromAccount());
				crTxn.setToAccount(ftDTO.getToAccount());
				crTxn.setTxnAmount(txnAmount);
				txnRepository.saveAndFlush(crTxn);
			}

			message = "Transaction has been done successfully...";

		}
		return message;
	}

}
