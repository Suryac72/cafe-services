package com.cafe.java.cafebackend.models;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity()
@Getter()
@Setter()
@AllArgsConstructor()
@NoArgsConstructor()
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    private UUID productId;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_description", length = 3000)
    private String productDescription;
    @Column(name="product_pic")
    private String productPic;
    @Column(name="product_price")
    private String productPrice;
    @Column(name="product_availability")
    private boolean productAvailability;
    @Column(name="product_quantity")
    private int productQuantity;

    @ManyToOne
    private Category category;

}