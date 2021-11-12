package com.dk.guest.room.booking.controller;

import java.io.IOException;
import java.util.Optional;

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


@RestController
public class OwnerAccountController {
	
	@Autowired
	AccountRepository accountRepository;
	
	@GetMapping("/owners/{id}")
	public Account viewAccount(@PathVariable Long id) throws IOException{
		System.out.println("Reading the Account id = " + id);
		
		Optional<Account> optional = accountRepository.findByAccountIdAndType(id, "owner");
		if(optional.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Owner doesn't exist");
		}
		return optional.get();
	}
	
	@PostMapping("/owners")
	public Account createAccount(@RequestBody Account account) throws IOException{
		System.out.println("***Creating the Account***");
		if(account.getAccountId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Account Id should be empty to create an account");
		}
		
		if(account.getType() == null || !account.getType().equals("owner")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid type is given");
		}
		accountRepository.save(account);
		return account;
	}
	
	@PutMapping("/owners")
	public Account updateAccount(@RequestBody Account account) throws IOException{
		System.out.println("***updating the Account***");
		if(account.getAccountId() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Account Id should be empty to update an existing account");
		}
		accountRepository.save(account);
		return account;
	}

	

}