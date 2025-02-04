package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class AccountService {

    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account register(Account account){
        String username= account.getUsername();
        boolean userExists = accountRepository.existsByUsername(username);
        if(userExists){
            return null;
        }
        if(account.getUsername()==null || account.getUsername().isEmpty()){
            throw new IllegalArgumentException("Username cannot be blank");
        }
        if(account.getPassword()==null || account.getPassword().length() < 4){
            throw new IllegalArgumentException("Password must be at least 4 characters long");
        }
        
        return accountRepository.save(account);

    }

    public Optional<Account> login(Account account){
        String username = account.getUsername();
        String password = account.getPassword();

        Account authAccount = accountRepository.findByUsername(username);


        if(authAccount != null && authAccount.getPassword().equals(password)){
            return Optional.of(authAccount);
        }else{
            return Optional.empty();
        }

    }
    
}
