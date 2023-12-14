package com.cafe.java.cafebackend.validators;

import org.apache.commons.lang3.ObjectUtils;

import com.cafe.java.cafebackend.constants.CategoryConstants;
import com.cafe.java.cafebackend.models.Category;

import jakarta.validation.ValidationException;

public class CategoryValidator {

    public static void validateCategoryForCreate(Category category) {
        if(category == null || category.isEmpty()){
            throw new ValidationException(CategoryConstants.INVALID_CATEGORY_REQUEST_PAYLOAD);
        }
        if (category.getCategoryTitle().isEmpty()) {
            throw new ValidationException(CategoryConstants.INVALID_CATEGORY_TITLE);
        }
        if( category.getCategoryDescription().isEmpty()) {
            throw new ValidationException(CategoryConstants.INVALID_CATEGORY_DESCRIPTION);
        }
    }

    public static void validateCategoryForUpdate(Category category) {
        // Implement update-specific validations
    }
}