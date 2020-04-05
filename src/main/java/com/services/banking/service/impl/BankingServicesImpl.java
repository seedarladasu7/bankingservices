package com.services.banking.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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

		float txnAmount = ftDTO.getTxnAmount();

		if (fromAcc == null || toAcc == null) {
			message = "Invalid Bank details provided...";
		}

		if (StringUtils.isNotEmpty(message)) {
			return message;
		} else {
			if (fromAcc != null) {
				float accBalance = fromAcc.getBalance();

				if (accBalance < ftDTO.getTxnAmount()) {
					message = "Insufficient balance...";
				} else {
					fromAcc.setBalance(fromAcc.getBalance() - ftDTO.getTxnAmount());
					accRepository.saveAndFlush(fromAcc);

					Transaction dtTxn = new Transaction();
					dtTxn.setAccount(fromAcc);
					dtTxn.setTxnType("Debit");
					dtTxn.setTxnDate(java.sql.Timestamp.valueOf(simpDate.format(new Date())));
					dtTxn.setFromAccount(ftDTO.getFromAccount());
					dtTxn.setToAccount(ftDTO.getToAccount());
					dtTxn.setTxnAmount(txnAmount);
					txnRepository.saveAndFlush(dtTxn);
				}
			}

			if (toAcc != null) {
				toAcc.setBalance(toAcc.getBalance() + ftDTO.getTxnAmount());
				accRepository.saveAndFlush(toAcc);

				Transaction crTxn = new Transaction();
				crTxn.setAccount(toAcc);
				crTxn.setTxnType("Credit");
				crTxn.setTxnDate(java.sql.Timestamp.valueOf(simpDate.format(new Date())));
				crTxn.setFromAccount(ftDTO.getFromAccount());
				crTxn.setToAccount(ftDTO.getToAccount());
				crTxn.setTxnAmount(txnAmount);
				txnRepository.saveAndFlush(crTxn);
			}

			message = "Transaction has been done successfully...";

		}
		return message;
	}

	@Override
	public List<Transaction> retrieveCustomerBankStatement(int custId) {

		String message = "";

		Optional<Customer> custOptnl = custRepository.findById(custId);

		if (custOptnl.isPresent()) {
			Customer customer = custOptnl.get();
			Optional<List<Account>> accOptnl = accRepository.findByCustomer(customer);

			if (accOptnl.isPresent()) {
				List<Account> acctList = accOptnl.get();
				Account account = acctList.get(0);

				Optional<List<Transaction>> txnListOptnl = txnRepository.findByAccount(account);

				if (txnListOptnl.isPresent()) {
					List<Transaction> txnList = txnListOptnl.get();
					return txnList;
				} else {
					message = "No transactions found for the account: " + account.getAccNumber();
				}

			} else {
				message = "Invalid Account for customer: " + customer.getFirstName();
			}

		} else {
			message = "Invalid customer with customer id: " + custId;
		}

		return null;
	}

}
