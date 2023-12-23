package com.cafe.java.cafebackend.repo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.cafe.java.cafebackend.dto.ProductDTO;
import com.cafe.java.cafebackend.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;


public interface ProductRepository extends JpaRepository<Product,UUID>{

    @Query(value = "SELECT product.*, category.category_title,category.category_id,category.category_description FROM product INNER JOIN category ON product.category_fk = category.category_id", nativeQuery = true)
    List<Object[]> getAllProducts();

    @Query("SELECT new com.cafe.java.cafebackend.dto.ProductDTO(p.productId, p.productName, p.productDescription, p.productPic, p.productPrice, p.productAvailability, p.productQuantity, p.status, p.category) FROM Product p WHERE p.productId = :productId")
    ProductDTO getProduct(@Param("productId") UUID productId);
    @Query(value = "SELECT * from product WHERE product_name = :productName", nativeQuery = true)
    Optional<Product> findByProductName(@Param("productName") String productName);


    @Query("SELECT new com.cafe.java.cafebackend.dto.ProductDTO(p.productId, p.productName, p.productDescription, p.productPic, p.productPrice, p.productAvailability, p.productQuantity, p.status, p.category) FROM Product p WHERE p.category.categoryId = :categoryId")
    List<ProductDTO> getProductByCategory(@Param("categoryId") UUID categoryId);
}
