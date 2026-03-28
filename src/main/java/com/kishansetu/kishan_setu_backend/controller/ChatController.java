package com.kishansetu.kishan_setu_backend.controller;

import com.kishansetu.kishan_setu_backend.service.ChatService;
import jakarta.servlet.ServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(
        origins = {
                "http://localhost:5173",
                "https://kishan-setu-delta.vercel.app"
        },
        methods = {
                RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.OPTIONS
        }
)
@RestController
@RequestMapping("/api/chat")
public class ChatController {
    @Autowired
    ChatService chatService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getChat(@PathVariable String id){
        return chatService.getChat(id);
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getChatByUserId(@PathVariable String  id){
        return chatService.getChatByUserId(id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChat(@PathVariable String id){
       return chatService.deleteChat(id);
    }
}
