package com.services.banking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "account")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "acc_id")
	private Integer accId;

	@Column(name = "acc_number")
	private String accNumber;

	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "balance")
	private float balance;

	@ManyToOne
	@JoinColumn(name = "cust_id")
	@JsonBackReference
	private Customer customer;

}
