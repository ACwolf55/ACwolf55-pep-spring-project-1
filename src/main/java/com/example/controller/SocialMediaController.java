package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService,MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

        //post localhost:8080
        @PostMapping(value = "/register")
        public ResponseEntity<Account> register(@RequestBody Account requestbody){

            Account newAccount = accountService.register(requestbody);
            if(newAccount==null){
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }else{
                return ResponseEntity.ok(newAccount);
            }

        }

        //post localhost:8080/login
        @PostMapping(value = "/login")
        public ResponseEntity<Account> loginHandler(@RequestBody Account requestbody){
            Optional<Account> authenticatedAccount = accountService.login(requestbody);
            if(authenticatedAccount.isPresent()){
                return ResponseEntity.ok(authenticatedAccount.get());
            }else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

        }

        //post localhost:8080/messages
        @PostMapping("/messages")
        public ResponseEntity<Message> postMessage(@RequestBody Message requestbody){
            try{
                Message newMessage = messageService.postMessage(requestbody);
                return ResponseEntity.ok(newMessage);
            } catch (IllegalArgumentException e){
                return ResponseEntity.badRequest().build();
            }        
        }

        //get localhost:8080/mesages
        @GetMapping("/messages")
        public ResponseEntity<List<Message>> getAllMessages(){

            List<Message> allMessages = messageService.getAllMessages();
            return ResponseEntity.ok(allMessages);

        }

        //get localhost:8080/messages/{message_id}
        @GetMapping("/messages/{message_id}")
        public ResponseEntity<Message> getSingleMessage(@PathVariable("message_id") int message_id){
            Optional<Message> optionalMessage = messageService.getMessageById(message_id);
            if(optionalMessage.isPresent()){
                return ResponseEntity.ok(optionalMessage.get());
            }else{
                return ResponseEntity.ok().build();
            }
        }

        //patch localhost:8080/messages/{message_id}
        @PatchMapping("/messages/{message_id}")
        public ResponseEntity<Integer> updateMessage(@RequestBody Message requestbody,@PathVariable("message_id") int messageId){
            int rowsUpdated = messageService.patchMessage(requestbody,messageId);
            if (rowsUpdated >0){
                return ResponseEntity.ok(rowsUpdated);
            }else{
                return ResponseEntity.badRequest().build();
            }
        }

        //delete localhost:8080/message/{message_id}
        @DeleteMapping("/messages/{message_id}")
        public ResponseEntity<Integer> deleteAccountMessage(@PathVariable("message_id") int messageId){
            int rowsUpdated = messageService.deleteMessage(messageId);
            if(rowsUpdated>0){
                return ResponseEntity.ok(rowsUpdated);
            } else {
                return ResponseEntity.ok().build();
            }
        }

        //get localhost:8080/message/{message_id}
        @GetMapping("/accounts/{account_id}/messages")
        public ResponseEntity<List<Message>> getAllUserMessages(@PathVariable("account_id") int account_id){
            List<Message> messages = messageService.getAllUserMessages(account_id);
            return ResponseEntity.ok(messages);
        }

}
