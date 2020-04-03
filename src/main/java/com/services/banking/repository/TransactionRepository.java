package com.services.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.services.banking.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}
