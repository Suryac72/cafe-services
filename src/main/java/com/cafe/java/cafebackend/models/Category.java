package com.cafe.java.cafebackend.models;



import java.util.List;
import java.util.UUID;
import java.util.ArrayList;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity()
@Data
@DynamicInsert
@DynamicUpdate
@ToString
@Table(name="category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "category_id")
    private UUID categoryId;

    @Column(name = "category_title")
    @NotBlank(message = "Title cannot be blank")
    private String categoryTitle;

    @Column(name = "category_description", length = 1000)
    @NotBlank(message = "Description cannot be blank")
    private String categoryDescription;

}
