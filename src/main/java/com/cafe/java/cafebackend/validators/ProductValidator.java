package com.cafe.java.cafebackend.validators;

import com.cafe.java.cafebackend.constants.CafeConstants;
import com.cafe.java.cafebackend.utils.CafeUtils;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProductValidator {

    public  void validateProduct(Map<String, String> product) throws ValidationException {
        if(product.containsKey("productName"))
            validateStringNotEmpty(product, "productName", CafeConstants.PRODUCT_NAME_CANNOT_BE_EMPTY);
        if(product.containsKey("productDescription"))
            validateStringNotEmpty(product, "productDescription", CafeConstants.PRODUCT_DESCRIPTION_CANNOT_BE_EMPTY);
        if(product.containsKey("productPrice"))
            validatePositiveDouble(product, "productPrice", CafeConstants.PRODUCT_PRICE_POSITIVE);
        if(product.containsKey("productQuantity"))
            validatePositiveInteger(product, "productQuantity", CafeConstants.PRODUCT_QUANTITY_POSITIVE);
        if(product.containsKey("productPic"))
            validateStringNotEmpty(product, "productPic", CafeConstants.PRODUCT_PIC_CANNOT_BE_EMPTY);
        if(product.containsKey("status"))
            validateStringNotEmpty(product, "status", CafeConstants.PRODUCT_STATUS_CANNOT_BE_EMPTY);
        if(product.containsKey("categoryId"))
            validateStringNotEmpty(product, "categoryId", CafeConstants.CATEGORY_ID_CANNOT_BE_EMPTY);
        if(product.containsKey("productAvailability"))
            validateStringNotEmpty(product, "productAvailability", CafeConstants.PRODUCT_AVAILABILITY_CANNOT_BE_EMPTY);
    }

    private  void validateStringNotEmpty(Map<String, String> map, String key, String errorMessage) throws ValidationException {
        if (!map.containsKey(key) || map.get(key).isEmpty()) {
            throw new ValidationException(errorMessage);
        }
    }

    private  void validatePositiveDouble(Map<String, String> map, String key, String errorMessage) throws ValidationException {
        if (!map.containsKey(key)) {
            return; // Optional field
        }

        try {
            double value = Double.parseDouble(map.get(key));
            if (value < 0) {
                throw new ValidationException(errorMessage);
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid format for " + key);
        }
    }

    private  void validatePositiveInteger(Map<String, String> map, String key, String errorMessage) throws ValidationException {
        if (!map.containsKey(key)) {
            return; // Optional field
        }

        try {
            int value = Integer.parseInt(map.get(key));
            if (value < 0) {
                throw new ValidationException(errorMessage);
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid format for " + key);
        }
    }
}