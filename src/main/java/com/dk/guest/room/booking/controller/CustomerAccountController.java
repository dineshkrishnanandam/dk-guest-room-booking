package com.dk.guest.room.booking.controller;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.dk.guest.room.booking.data.model.Account;
import com.dk.guest.room.booking.data.repository.AccountRepository;

/*
 * CustomerAccountController - process requests to create and view customers
 * 
 */
@RestController
public class CustomerAccountController {
	
	private static Logger logger = LoggerFactory.getLogger(CustomerAccountController.class);

	
	@Autowired
	AccountRepository accountRepository;
	
	
	/*
	 * viewAccount process GET request to view the details of a customer
	 * 
	 * id - customer id 
	 * 
	 * @return Account - details of the customer {id} 
	 */
	@GetMapping("/customers/{id}")
	public Account viewAccount(@PathVariable Long id) throws IOException{
		logger.info("Reading the Account id = " + id);
		
		Optional<Account> optional = accountRepository.findByAccountIdAndType(id, "customer");
		if(optional.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer doesn't exist");
		}
		return optional.get();
	}
	
	/*
	 * createAccount process POST request to create the customer
	 * 
	 * @return Account -  returns created customer with new customer id 
	 */
	@PostMapping("/customers")
	public Account createAccount(@RequestBody Account account) throws IOException{
		logger.info("***Creating the Account***");
		if(account.getAccountId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Account Id should be empty to create an account");
		}
		if(account.getType() == null || !account.getType().equals("customer")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid type is given");
		}
		accountRepository.save(account);
		return account;
	}
	
	/*
	 * updateAccount process PUT request to update the customer
	 * 
	 * @return Account - returns updated customer 
	 */
	@PutMapping("/customers")
	public Account updateAccount(@RequestBody Account account) throws IOException{
		logger.info("***updating the Account***");
		if(account.getAccountId() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Account Id should be empty to update an existing account");
		}
		accountRepository.save(account);
		return account;
	}

	

}
