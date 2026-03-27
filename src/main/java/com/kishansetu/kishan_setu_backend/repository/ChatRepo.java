package com.kishansetu.kishan_setu_backend.repository;

import com.kishansetu.kishan_setu_backend.model.ChatModel;
import com.kishansetu.kishan_setu_backend.model.ProductModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepo extends MongoRepository<ChatModel, String> {
    List<ChatModel> findByUserId(String userId);

}
