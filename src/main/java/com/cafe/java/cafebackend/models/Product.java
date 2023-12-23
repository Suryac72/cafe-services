package com.cafe.java.cafebackend.models;

import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.URL;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    private UUID productId;

    @NotBlank(message = "Product name cannot be blank")
    @Size(max = 255, message = "Product name must be less than or equal to 255 characters")
    @Column(name = "product_name")
    private String productName;

    @Size(max = 3000, message = "Product description must be less than or equal to 3000 characters")
    @Column(name = "product_description", length = 3000)
    private String productDescription;

    @URL(message = "Invalid URL format for product picture")
    @Column(name = "product_pic")
    private String productPic;

    @NotBlank(message = "Product price must be greater than or equal to 0")
    @Column(name = "product_price")
    private String productPrice;

    @NotNull(message = "Product availability cannot be null")
    @Column(name = "product_availability")
    private boolean productAvailability;

    @NotBlank(message = "Product quantity must be greater than or equal to 0")
    @Column(name = "product_quantity")
    private String productQuantity;

    @NotBlank(message = "Product status cannot be blank")
    @Column(name = "product_status")
    private String status;

    @NotNull(message = "Category cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_fk", nullable = false)
    private Category category;
}
