package com.example.repository;

import com.example.exception.ErrorCode;
import com.example.exception.GenericException;
import com.example.model.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;



@DataJpaTest
@ActiveProfiles(value = "integration")
class CategoryRepositoryTest {


    @Autowired
    private  CategoryRepository categoryRepository;




    @Test
    public void givenCategoryObject_whenSave_thenReturnSavedCategory(){
        Category category = Category.builder()
                .categoryName("test-categoryName")
                .build();


        Category fromDb = categoryRepository.save(category);

        assertThat(fromDb.getId()).isGreaterThan(0);
        assertThat(fromDb).isNotNull();


    }



    @Test
    public void testGetByCategoryName_itShoulReturnOptionalOfCategory(){
        Category category = Category.builder()
                .categoryName("test-categoryName")
                .build();


        categoryRepository.save(category);

        Category fromDb = categoryRepository.getByCategoryName(category.getCategoryName()).orElseThrow(
                ()-> new GenericException(HttpStatus.NOT_FOUND , ErrorCode.CATEGORY_NOT_FOUNDED)

        );

        assertThat(fromDb).isNotNull();


    }








}