package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.List;
import java.util.ArrayList;

@Service
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired 
    public MessageService(MessageRepository messageRepository,AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }


    public Message postMessage(Message message){
        int account_id = message.getPostedBy();
        boolean userExists = accountRepository.existById(account_id);
        if (message.getMessageText()==null || message.getMessageText().trim().isEmpty() || message.getMessageText.length() >225 || !userExists){
            throw new IllegalArgumentException("INvalid message or user");
        }
        return messageRepository.save(message);

    }

    
}
