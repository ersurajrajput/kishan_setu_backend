package com.kishansetu.kishan_setu_backend.repository;

import com.kishansetu.kishan_setu_backend.model.ProductModel;
import com.kishansetu.kishan_setu_backend.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends MongoRepository<ProductModel, String> {
    List<ProductModel> findBySellerId(String sellerId);

}
