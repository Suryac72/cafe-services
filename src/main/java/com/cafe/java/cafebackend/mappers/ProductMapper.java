package com.cafe.java.cafebackend.mappers;

import com.cafe.java.cafebackend.dto.ProductDTO;
import com.cafe.java.cafebackend.models.Category;
import com.cafe.java.cafebackend.models.Product;
import com.cafe.java.cafebackend.utils.ProductUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.*;

@Service
public class ProductMapper {

    public Product toProductEntity(Map<String, String> requestMap) {
        Product product = new Product();

        for (Map.Entry<String, String> entry : requestMap.entrySet()) {
            String fieldName = entry.getKey();
            String methodName = "set" + ProductUtils.toTitleCase(fieldName);
            String value = entry.getValue();
            try {
                Method method = Product.class.getMethod(methodName, String.class);
                method.invoke(product, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return product;
    }

    public void toUpdatePersistence(Map<String, String> requestMap, Optional<Product> product){
        for (Map.Entry<String, String> entry : requestMap.entrySet()) {
            String fieldName = entry.getKey();
            String methodName = "set" + ProductUtils.toTitleCase(fieldName);
            String value = entry.getValue();
            try {
                if(methodName.contains("Category")){
                    System.out.println(methodName);
                    Method categoryMethod = Product.class.getMethod(methodName, Category.class);
                    Gson gson= new Gson();
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode rootNode = mapper.readTree(value);
                    String categoryId = rootNode.get("categoryId").asText();
                    String categoryTitle = rootNode.get("categoryTitle").asText();
                    String categoryDescription = rootNode.get("categoryDescription").asText();
                    Category category = new Category();
                    category.setCategoryId(UUID.fromString(categoryId));
                    category.setCategoryTitle(categoryTitle);
                    category.setCategoryDescription(categoryDescription);
                    System.out.println(category.toString());
                    product.get().setCategory(category);
                }
                else{
                    Method method = Product.class.getMethod(methodName, String.class);
                    method.invoke(product.get(), value);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ProductDTO toSingleProductDtoFromPersistence(Object[] product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId((UUID) product[0]);
        productDTO.setProductAvailability((Boolean) product[1]);
        productDTO.setProductDescription((String) product[2]);
        productDTO.setProductName((String) product[3]);
        productDTO.setProductPic((String) product[4]);
        productDTO.setProductPrice((String) product[5]);
        productDTO.setProductQuantity((String) product[6]);
        productDTO.setStatus((String) product[7]);

        // Adjust indices for Category
        Category category = new Category();
        category.setCategoryId((UUID) product[8]);
        category.setCategoryTitle((String) product[9]);
        category.setCategoryDescription((String) product[10]);
        productDTO.setCategory(category);

        return productDTO;
    }

    public List<ProductDTO> toProductDtoFromPersistence(List<Object[]> products){
        List<ProductDTO> productList = new ArrayList<>();
        for (Object[] row : products) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductId((UUID) row[0]);
            productDTO.setProductAvailability((Boolean) row[1]);
            productDTO.setProductDescription((String) row[2]);
            productDTO.setProductName((String) row[3]);
            productDTO.setProductPic((String) row[4]);
            productDTO.setProductPrice((String) row[5]);
            productDTO.setProductQuantity((String) row[6]);
            productDTO.setStatus((String) row[7]);
            Category category = new Category();
            category.setCategoryId((UUID) row[8]);
            category.setCategoryTitle((String) row[10]);
            category.setCategoryDescription((String) row[12]);
            productDTO.setCategory(category);
            productList.add(productDTO);
        }
        return productList;
    }

    private Category convertJsonNodeToCategory(JsonNode rootNode) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.treeToValue(rootNode, Category.class);
    }
}
