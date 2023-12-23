package com.cafe.java.cafebackend.impl.product;

import com.cafe.java.cafebackend.constants.CafeConstants;
import com.cafe.java.cafebackend.dto.ProductDTO;
import com.cafe.java.cafebackend.mappers.ProductMapper;
import com.cafe.java.cafebackend.models.Category;
import com.cafe.java.cafebackend.models.Product;
import com.cafe.java.cafebackend.repo.CategoryRepository;
import com.cafe.java.cafebackend.repo.ProductRepository;
import com.cafe.java.cafebackend.services.auth.JwtFilter;
import com.cafe.java.cafebackend.services.product.ProductService;
import com.cafe.java.cafebackend.utils.CafeUtils;
import com.cafe.java.cafebackend.validators.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private ProductValidator productValidator;

    @Override
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        try {
            List<Object[]> result = productRepository.getAllProducts();
            List<ProductDTO> products = this.productMapper.toProductDtoFromPersistence(result);
            return new ResponseEntity<List<ProductDTO>>(products, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<ProductDTO>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(UUID productId, Map<String, String> requestMap) {
        try {
            if (requestMap.isEmpty()) {
                return CafeUtils.getResponseEntity(CafeConstants.INVALID_REQUEST_PAYLOAD,
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Optional<Product> product = productRepository.findById(productId);
            if (product.isEmpty()) {
                return CafeUtils.getResponseEntity(CafeConstants.PRODUCT_NOT_FOUND, HttpStatus.BAD_REQUEST);
            } else if (jwtFilter.isUser()) {
                return CafeUtils.getResponseEntity(CafeConstants.USER_CANNOT_AUTHORIZED_TO_PERFORM_THIS_OPERATION, HttpStatus.UNAUTHORIZED);
            } else {
                productValidator.validateProduct(requestMap);
                productMapper.toUpdatePersistence(requestMap, product);
                if(requestMap.containsKey("categoryId")) {
                    Optional<Category> category = categoryRepository.findById(UUID.fromString(requestMap.get("categoryId")));
                    if(category.isEmpty()){
                        return CafeUtils.getResponseEntity(CafeConstants.CATEGORY_NOT_FOUND, HttpStatus.BAD_REQUEST);
                    }
                    product.get().setCategory(category.get());
                }
                productRepository.save(product.get());
                return CafeUtils.getResponseEntity(CafeConstants.PRODUCT_UPDATED_SUCCESSFULLY, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CafeUtils.getResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> addProduct(Product product, BindingResult result) {
        try {
            if (product == null) {
                return CafeUtils.getResponseEntity(CafeConstants.INVALID_REQUEST_PAYLOAD,
                        HttpStatus.INTERNAL_SERVER_ERROR);
            } else if (result.hasErrors()) {
                return CafeUtils.handleValidationErrors(result);
            } else if (jwtFilter.isUser()) {
                return CafeUtils.getResponseEntity(CafeConstants.USER_CANNOT_AUTHORIZED_TO_PERFORM_THIS_OPERATION, HttpStatus.UNAUTHORIZED);
            } else {
                Optional<Product> foundProduct = productRepository.findByProductName(product.getProductName());
                if (!foundProduct.isEmpty()) {
                    return CafeUtils.getResponseEntity(CafeConstants.PRODUCT_ALREADY_PRESENT, HttpStatus.BAD_REQUEST);
                }
                productRepository.save(product);
                return CafeUtils.getResponseEntity(CafeConstants.PRODUCT_ADDED_SUCCESSFULLY, HttpStatus.OK);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProduct(UUID productId) {
        try {
            Optional<Product> product = productRepository.findById(productId);
            if (product.isEmpty()) {
                return CafeUtils.getResponseEntity(CafeConstants.PRODUCT_NOT_FOUND, HttpStatus.BAD_REQUEST);
            } else if (jwtFilter.isUser()) {
                return CafeUtils.getResponseEntity(CafeConstants.USER_CANNOT_AUTHORIZED_TO_PERFORM_THIS_OPERATION, HttpStatus.UNAUTHORIZED);
            } else {
                productRepository.delete(product.get());
                return CafeUtils.getResponseEntity(CafeConstants.PRODUCT_DELETED_SUCCESSFULLY, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProductDTO> getProduct(UUID productId) {
        try {
            ProductDTO product = productRepository.getProduct(productId);
            return new ResponseEntity<ProductDTO>(product, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<ProductDTO>(new ProductDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductDTO>> getByCategory(UUID categoryId) {
        try{
            return new ResponseEntity<List<ProductDTO>>(productRepository.getProductByCategory(categoryId),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<ProductDTO>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
