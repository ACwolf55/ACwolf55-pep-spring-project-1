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
        boolean userExists = accountRepository.existsById(account_id);
        if (message.getMessageText()==null || message.getMessageText().trim().isEmpty() || message.getMessageText().length() >225 || !userExists){
            throw new IllegalArgumentException("Invalid message or user");
        }
        return messageRepository.save(message);

    }

    public List<Message> getAllMessages(){
        List<Message> messages = messageRepository.findAll();
        return messages;
    }

    public Optional<Message> getMessageById(int message_id){
        Optional<Message> optionalMessage = messageRepository.findById(message_id);
        return optionalMessage;
    }

    public int deleteMessage(int message_id){
        boolean exists = messageRepository.existsById(message_id);
        if(exists){
            messageRepository.deleteById(message_id);
            return 1;
        }else{
            return 0;
        }
    }

    public int patchMessage(Message message, int messageId){
        Optional<Message> optionalExistingMessage = messageRepository.findById(messageId);
        if(optionalExistingMessage.isPresent()){
            if(message.getMessageText() != null && !message.getMessageText().trim().isEmpty() && message.getMessageText().length() <= 225){
                messageRepository.save(message);
                return 1;
            }
        }
        return 0;
    }

    public List<Message> getAllUserMessages(int account_id){
        return messageRepository.findByPostedBy(account_id);
    }

    

    
}
