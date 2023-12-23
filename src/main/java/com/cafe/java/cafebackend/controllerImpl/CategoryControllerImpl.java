package com.cafe.java.cafebackend.controllerImpl;

import com.cafe.java.cafebackend.constants.CafeConstants;
import com.cafe.java.cafebackend.controller.CategoryController;
import com.cafe.java.cafebackend.models.Category;
import com.cafe.java.cafebackend.services.category.CategoryService;
import com.cafe.java.cafebackend.utils.CafeUtils;
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
public class CategoryControllerImpl implements CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Override
    public ResponseEntity<List<Category>> getAllCategories(@RequestParam(name="filterValue",required = false) String filterValue) {
        try {
            return categoryService.getAllCategories(filterValue);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<Category>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Category> getCategory(@PathVariable(name = "categoryId") UUID categoryId) {
        try {
            return categoryService.getCategory(categoryId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<Category>(new Category(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> addCategory(Category category, BindingResult result) {
        try {
            return categoryService.addCategory(category,result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(@RequestParam(name = "categoryId") UUID categoryId, Map<String, String> requestMap) {
        try {
            return categoryService.updateCategory(categoryId,requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteCategory(UUID categoryId) {
        try {
            return categoryService.deleteCategory(categoryId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
