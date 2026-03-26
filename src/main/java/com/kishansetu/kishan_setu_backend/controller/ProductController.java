package com.kishansetu.kishan_setu_backend.controller;

import com.kishansetu.kishan_setu_backend.model.ProductModel;
import com.kishansetu.kishan_setu_backend.model.UserModel;
import com.kishansetu.kishan_setu_backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id){
        return productService.getProductById(id);
    }

    @GetMapping("/seller/{id}")
    public ResponseEntity<?> getAllProductsBySellerId(@PathVariable String id){
        return productService.getAllProductsBySellerId(id);
    }

    @PostMapping
    public ResponseEntity<?> saveProduct(
                                         @RequestParam(required = false) String name,
                                         @RequestParam(required = false) String quantity,
                                         @RequestParam(required = false) String price,
                                         @RequestParam(required = false) String location,
                                         @RequestParam(required = false) String sellerId,
                                         @RequestParam(required = false) String type,
                                         @RequestParam(required = false) MultipartFile image){
        ProductModel productModel = new ProductModel();


        productModel.setName(name);
        productModel.setQuantity(quantity);
        productModel.setPrice(price);
        productModel.setLocation(location);
        productModel.setSellerId(sellerId);
        productModel.setType(type);

        return productService.saveProduct(productModel,image);
    }
    @PutMapping
    public ResponseEntity<?> editProduct(
            @RequestParam(required = false) String id,

            @RequestParam(required = false) String name,
            @RequestParam(required = false) String quantity,
            @RequestParam(required = false) String price,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String sellerId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) MultipartFile image){
        ProductModel productModel = new ProductModel();

        productModel.setId(id);
        productModel.setName(name);
        productModel.setQuantity(quantity);
        productModel.setPrice(price);
        productModel.setLocation(location);
        productModel.setSellerId(sellerId);
        productModel.setType(type);

        return productService.updateProduct(productModel,image);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable String id){
        return productService.deleteProductById(id);
    }


}
