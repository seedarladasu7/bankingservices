package com.services.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.services.banking.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
	
	Account findByAccNumber(String accNumber);

}
