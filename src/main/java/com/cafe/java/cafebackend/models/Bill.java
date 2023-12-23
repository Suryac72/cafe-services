package com.cafe.java.cafebackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.URL;

import java.util.UUID;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
@Table(name = "bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "bill_id")
    private UUID billId;

    @NotBlank(message = "Customer name cannot be blank")
    @Size(max = 255, message = "Customer name must be less than or equal to 255 characters")
    @Column(name = "customer_name")
    private String customerName;

    @Email(message = "Invalid email format")
    @Column(name = "customer_email", length = 3000)
    private String customerEmail;

    @NotBlank(message = "Contact number name cannot be blank")
    @Column(name = "contact_number")
    private String contactNumber;

    @NotBlank(message = "Payment method cannot be blank")
    @Column(name = "payment_method")
    private String paymentMethod;

    @NotNull(message = "Total amount cannot be null")
    @NotBlank(message = "Total amount must be greater than or equal to 0")
    @Column(name = "total_amount")
    private String totalAmount;

    @NotNull(message = "Product details cannot be null")
    @Column(name="product_details",columnDefinition = "json")
    private String productDetails;

    @Column(name="created_by")
    private String createdBy;

    @Column(name="created_at")
    private String createdAt;

    @Column(name="updated_by")
    private String updatedBy;

    @Column(name="updated_at")
    private String updatedAt;
}
