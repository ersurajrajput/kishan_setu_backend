package com.kishansetu.kishan_setu_backend.service;


import com.kishansetu.kishan_setu_backend.repository.ChatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    @Autowired
    ChatRepo chatRepo;
    public ResponseEntity<?> getChat(String id){
        if (chatRepo.findById(id).isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(chatRepo.findById(id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CHAT  NOT FOUND");
    }
    public ResponseEntity<?> getChatByUserId(String id){
        if (!chatRepo.findByUserId(id).isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(chatRepo.findByUserId(id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CHAT  NOT FOUND");
    }
    public ResponseEntity<?> deleteChat(String id){
        if (chatRepo.findById(id).isPresent()){
            chatRepo.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Chat Deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CHAT  NOT FOUND");
    }
}
