package com.kishansetu.kishan_setu_backend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserModel {

    @Id
    private String id;

    private String name;
    private String email;
    private String password;
    private String phone;
    private String imageUrl;

    private String role;

    private String address;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}