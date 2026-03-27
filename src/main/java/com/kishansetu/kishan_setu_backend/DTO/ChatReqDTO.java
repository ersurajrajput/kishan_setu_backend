package com.kishansetu.kishan_setu_backend.DTO;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chat")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString



public class ChatReqDTO {
    @Id
    private String id;
    private String chat;

}
