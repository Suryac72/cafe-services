package com.cafe.java.cafebackend.models;



import java.util.List;
import java.util.UUID;
import java.util.ArrayList;
import com.cafe.java.cafebackend.validators.CategoryValidator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity()
@Getter()
@Setter()
@AllArgsConstructor()
@NoArgsConstructor()
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "category_id")
    private UUID categoryId;

    @Column(name = "category_title")
    private String categoryTitle;

    @Column(name = "category_description", length = 1000)
    private String categoryDescription;
    
    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<Product>();

     public void validateForCreate(Category category) {
        CategoryValidator.validateCategoryForCreate(category);
    }

    public void validateForUpdate(Category category) {
        CategoryValidator.validateCategoryForUpdate(this);
    }

    public boolean isEmpty() {
        return this == null;
    }
}
