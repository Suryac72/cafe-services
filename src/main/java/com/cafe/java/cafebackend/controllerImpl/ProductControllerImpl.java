package com.cafe.java.cafebackend.controllerImpl;

import com.cafe.java.cafebackend.constants.CafeConstants;
import com.cafe.java.cafebackend.controller.ProductController;
import com.cafe.java.cafebackend.dto.ProductDTO;
import com.cafe.java.cafebackend.models.Product;
import com.cafe.java.cafebackend.services.product.ProductService;
import com.cafe.java.cafebackend.utils.CafeUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@Tag(name = "Product Services", description = "Product APIs of Cafe-Services")
public class ProductControllerImpl implements ProductController {

    @Autowired
    private ProductService productService;

    @Override
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        try {
            return productService.getAllProducts();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<ProductDTO>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProductDTO> getProduct(@PathVariable("productId") UUID productId) {
        try {
            return productService.getProduct(productId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<ProductDTO>(new ProductDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> addProduct(Product product, BindingResult result) {
        try {
            return productService.addProduct(product,result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(@RequestParam("productId") UUID productId, Map<String, String> requestMap) {
        try {
            return productService.updateProduct(productId,requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProduct(@RequestParam(name = "productId") UUID productId) {
        try {
            return productService.deleteProduct(productId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductDTO>> getByCategory(@PathVariable  UUID categoryId) {
        try{
            return productService.getByCategory(categoryId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<ProductDTO>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
