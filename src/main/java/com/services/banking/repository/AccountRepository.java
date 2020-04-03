package com.services.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.services.banking.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer>{

}
