package com.cafe.java.cafebackend.dto;

import com.cafe.java.cafebackend.models.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
@NoArgsConstructor
public class ProductDTO {
    private UUID productId;
    private String productName;
    private String productDescription;
    private String productPic;
    private String productPrice;
    private boolean productAvailability;
    private String productQuantity;
    private String status;
    private Category category;

    public ProductDTO(UUID productId, String productName, String productDescription, String productPic, String productPrice, boolean productAvailability, String productQuantity, String status, Category category) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPic = productPic;
        this.productPrice = productPrice;
        this.productAvailability = productAvailability;
        this.productQuantity = productQuantity;
        this.status = status;
        this.category = category;
    }

}