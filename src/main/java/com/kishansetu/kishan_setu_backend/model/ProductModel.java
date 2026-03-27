package com.kishansetu.kishan_setu_backend.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document(collection = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductModel {
    @Id
    private String id;

    private String name;
    private String quantity ;
    private String price;
    private String location;
    private String sellerId;
    private String sellerName;
    private String imageUrl;
    private String type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
