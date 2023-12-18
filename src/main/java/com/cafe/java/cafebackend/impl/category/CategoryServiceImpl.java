package com.cafe.java.cafebackend.impl.category;

import com.cafe.java.cafebackend.constants.CafeConstants;
import com.cafe.java.cafebackend.models.Category;
import com.cafe.java.cafebackend.repo.CategoryRepository;
import com.cafe.java.cafebackend.services.auth.JwtFilter;
import com.cafe.java.cafebackend.services.category.CategoryService;
import com.cafe.java.cafebackend.utils.CafeUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<List<Category>> getAllCategories() {
        try{
            List<Category> categories = categoryRepository.getAllCategories();
            return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<Category>>(new ArrayList<>(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> addCategory(Category category, BindingResult result) {
       try {
           if (category.getCategoryTitle() == null && category.getCategoryDescription() == null) {
               return CafeUtils.getResponseEntity(CafeConstants.INVALID_REQUEST_PAYLOAD,
                       HttpStatus.INTERNAL_SERVER_ERROR);
           }
           else if (result.hasErrors()) {
               return CafeUtils.handleValidationErrors(result);
           }
           else if(jwtFilter.isUser()){
               return CafeUtils.getResponseEntity(CafeConstants.USER_CANNOT_AUTHORIZED_TO_PERFORM_THIS_OPERATION,HttpStatus.UNAUTHORIZED);
           }
           else {
               Optional<Category> foundCategory = categoryRepository.findByCategoryTitle(category.getCategoryTitle());
               if(foundCategory.get().getCategoryId() != null){
                   return CafeUtils.getResponseEntity(CafeConstants.CATEGORY_ALREADY_PRESENT,HttpStatus.OK);
               }
               categoryRepository.save(category);
               return CafeUtils.getResponseEntity(CafeConstants.CATEGORY_ADDED_SUCCESSFULLY,HttpStatus.OK);
           }

       }catch (Exception e){
           e.printStackTrace();
       }
       return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteCategory(UUID categoryId) {
        try{
            Optional<Category> category = categoryRepository.findById(categoryId);
            if(category.isEmpty()){
                return CafeUtils.getResponseEntity(CafeConstants.CATEGORY_NOT_FOUND,HttpStatus.BAD_REQUEST);
            }
            else if(jwtFilter.isUser()){
                return CafeUtils.getResponseEntity(CafeConstants.USER_CANNOT_AUTHORIZED_TO_PERFORM_THIS_OPERATION,HttpStatus.UNAUTHORIZED);
            }
            else {
                categoryRepository.delete(category.get());
                return CafeUtils.getResponseEntity(CafeConstants.CATEGORY_DELETED_SUCCESSFULLY,HttpStatus.OK);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(UUID categoryId,Map<String, String> requestMap) {
        try{
            if(requestMap.size() < 0){
                return CafeUtils.getResponseEntity(CafeConstants.INVALID_REQUEST_PAYLOAD,HttpStatus.BAD_REQUEST);
            }
            Optional<Category> category = categoryRepository.findById(categoryId);
            if(category.isEmpty()){
                return CafeUtils.getResponseEntity(CafeConstants.CATEGORY_NOT_FOUND,HttpStatus.BAD_REQUEST);
            }
            else if(jwtFilter.isUser()){
                return CafeUtils.getResponseEntity(CafeConstants.USER_CANNOT_AUTHORIZED_TO_PERFORM_THIS_OPERATION,HttpStatus.UNAUTHORIZED);
            }
            else {
                if(requestMap.get("categoryTitle") != null){
                    category.get().setCategoryTitle(requestMap.get("categoryTitle"));
                }
                if(requestMap.get("categoryDescription") != null){
                    category.get().setCategoryDescription(requestMap.get("categoryDescription"));
                }
                categoryRepository.save(category.get());
                return CafeUtils.getResponseEntity(CafeConstants.CATEGORY_UPDATED_SUCCESSFULLY,HttpStatus.OK);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Category> getCategory(UUID categoryId) {
        try{
            Optional<Category> category = categoryRepository.findById(categoryId);
            return new ResponseEntity<Category>(category.get(),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<Category>(new Category(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
