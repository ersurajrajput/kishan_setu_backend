package com.kishansetu.kishan_setu_backend.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "chat")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatModel {
    @Id
    private String id;
    private String chat;
    private String chatQ;
    private String userId;
    private LocalDateTime createdAt;

}
