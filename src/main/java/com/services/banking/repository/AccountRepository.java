package com.services.banking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.services.banking.entity.Account;
import com.services.banking.entity.Customer;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
	
	Account findByAccNumber(String accNumber);
	
	Optional<List<Account>> findByCustomer(Customer customer);

}
