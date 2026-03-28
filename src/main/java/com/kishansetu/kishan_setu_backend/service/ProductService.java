package com.kishansetu.kishan_setu_backend.service;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.kishansetu.kishan_setu_backend.model.ProductModel;
import com.kishansetu.kishan_setu_backend.model.UserModel;
import com.kishansetu.kishan_setu_backend.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepo productRepo;

    @Autowired
    private Cloudinary cloudinary;

    public ResponseEntity<?> getAllProducts(){
        return ResponseEntity.status(HttpStatus.OK).body(productRepo.findAll());
    }

    public ResponseEntity<?> getProductById(String productId){
        if (productRepo.findById(productId).isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(productRepo.findById(productId));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Dose Not Exists");
    }

    public ResponseEntity<?> saveProduct(ProductModel productModel, MultipartFile image){
        if (image != null && !image.isEmpty()) {
            try {

                Map uploadResult = cloudinary.uploader().upload(
                        image.getBytes(),
                        ObjectUtils.emptyMap()
                );

                String imageUrl = uploadResult.get("secure_url").toString();
                productModel.setImageUrl(imageUrl);

            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Image upload failed");
            }
        }
        if ( productModel.getSellerId() == null||productModel.getSellerId().isBlank()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Seller Id Required");
        }
        productModel.setUpdatedAt(LocalDateTime.now());
        productModel.setCreatedAt(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepo.save(productModel));
    }

    public ResponseEntity<?> getAllProductsBySellerId(String sellerId){
        return ResponseEntity.status(HttpStatus.OK).body(productRepo.findBySellerId(sellerId));
    }

    public ResponseEntity<?> deleteProductById(String productId){
        if (productRepo.findById(productId).isPresent()){
            productRepo.deleteById(productId);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Dose Not Exists");
    }

    public ResponseEntity<?> updateProduct(ProductModel productModel, MultipartFile image) {

        // 1. Validate ID
        if (productModel.getId() == null || productModel.getId().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Product id is required");
        }

        // 2. Find user
        Optional<ProductModel> optionalProductModel = productRepo.findById(productModel.getId());

        if (optionalProductModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product Not Found / Invalid ID");
        }

        ProductModel existingProduct = optionalProductModel.get();

        // 3. Update fields safely
        if (productModel.getName() != null && !productModel.getName().isBlank()) {
            existingProduct.setName(productModel.getName());
        }

        if (productModel.getQuantity() != null && !productModel.getQuantity().isBlank()) {
            existingProduct.setQuantity(productModel.getQuantity());
        }

        if (productModel.getLocation() != null && !productModel.getLocation().isBlank()) {
            existingProduct.setLocation(productModel.getLocation());
        }

        if (productModel.getPrice() != null && !productModel.getPrice().isBlank()) {
            existingProduct.setPrice(productModel.getPrice());
        }

        // ⚠️ password (better to hash)
        if (productModel.getType() != null && !productModel.getType().isBlank()) {
            existingProduct.setType(productModel.getType());
        }

        // 4. CLOUDINARY IMAGE UPDATE 🚀
        if (image != null && !image.isEmpty()) {
            try {

                Map uploadResult = cloudinary.uploader().upload(
                        image.getBytes(),
                        ObjectUtils.emptyMap()
                );

                String imageUrl = uploadResult.get("secure_url").toString();
                existingProduct.setImageUrl(imageUrl);

            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Image upload failed");
            }
        }

        // 5. Timestamp
        existingProduct.setUpdatedAt(LocalDateTime.now());

        // 6. Save
        ProductModel updatedProduct = productRepo.save(existingProduct);

        return ResponseEntity.ok(updatedProduct);
    }




}
