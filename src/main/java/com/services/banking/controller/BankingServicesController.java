package com.services.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.services.banking.dto.AccountDTO;
import com.services.banking.dto.CustomerDTO;
import com.services.banking.dto.FundTransferDTO;
import com.services.banking.service.BankingServices;

@RestController
@RequestMapping("/customer")
public class BankingServicesController {

	@Autowired
	BankingServices services;
	
	@PostMapping("/register")
	public ResponseEntity<String> registerCustomer(@RequestBody CustomerDTO customer) {
		return new ResponseEntity<>("Regiistration done successfully...", HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/account")
	public ResponseEntity<String> createAccount(@RequestBody AccountDTO account) {
		return new ResponseEntity<>("Account has been created successfully...", HttpStatus.ACCEPTED);		
	}
	
	@PostMapping("/fundTransfer")
	public ResponseEntity<String> transferFunds(@RequestBody FundTransferDTO fundTransfer) {
		return new ResponseEntity<>("Fund has been created successfully...", HttpStatus.ACCEPTED);		
	}
	
	@GetMapping("/{custId}/statement")
	public ResponseEntity<String> getStatement(@PathVariable("custId") Integer custId) {
		return new ResponseEntity<>("Fund has been created successfully...", HttpStatus.ACCEPTED);		
	}
	
	
}
