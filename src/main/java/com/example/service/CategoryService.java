package com.example.service;


import com.example.dtos.SaveCategoryRequest;
import com.example.exception.ErrorCode;
import com.example.exception.GenericException;
import com.example.model.Category;
import com.example.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;



    public Category loadCategory(Long id){
        Category result = categoryRepository.findById(id).orElseThrow(()->new GenericException(HttpStatus.NOT_FOUND , ErrorCode.CATEGORY_NOT_FOUNDED ));
        return  result;
    }


    public Category getByCategoryName(String categoryName){
          Category result =   categoryRepository.getByCategoryName(categoryName).orElseThrow( ()->new GenericException(HttpStatus.NOT_FOUND , ErrorCode.CATEGORY_NOT_FOUNDED));
          return result;
    }


    @Transactional
    public Category save(SaveCategoryRequest request){
        Category category = Category.builder()
                .categoryName(request.getCatgoryName())
                .build();
        Category fromDb = categoryRepository.save(category);
        return fromDb;
    }



}
