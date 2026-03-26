package com.kishansetu.kishan_setu_backend.repository;

import com.kishansetu.kishan_setu_backend.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<UserModel, String> {

    Optional<UserModel> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<UserModel> findByPhone(String phone);
}