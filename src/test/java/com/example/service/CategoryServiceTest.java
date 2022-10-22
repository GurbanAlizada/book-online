package com.example.service;

import com.example.dtos.SaveCategoryRequest;
import com.example.exception.ErrorCode;
import com.example.exception.GenericException;
import com.example.model.Category;
import com.example.repository.CategoryRepository;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    private CategoryService categoryService;


    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp(){
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryService = new CategoryService(categoryRepository);
    }


    @Test
    void itShouldReturnCategoryById_whenCategoryExists(){

        Category expected  = Category.builder()
                .categoryName("test")
                .build();

        when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(expected));

        Category actual = categoryService.loadCategory(Mockito.anyLong());

        assertEquals(expected , actual);
        verify(categoryRepository , times(1)).findById(Mockito.anyLong());
    }




    @Test
    public void itShouldThrowException_whenCategoryDoesNotExists(){

        GenericException expectedError =  new GenericException(
                HttpStatus.NOT_FOUND ,
                ErrorCode.CATEGORY_NOT_FOUNDED,
                "Category not found");

        when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        GenericException actual = assertThrows(GenericException.class, () -> categoryService.loadCategory(Mockito.anyLong()));

        verify(categoryRepository , times(1)).findById(Mockito.anyLong());
        assertEquals(expectedError.getErrorCode() , actual.getErrorCode());


    }






    @Test
    public void testGetByCategoryName_whenCategoryNameExists_isShouldReturnCategory(){
        String categoryName = "test";
        Category expected = new Category("test" , null);
        when(categoryRepository.getByCategoryName(categoryName)).thenReturn(Optional.of(expected));
        Category actual = categoryService.getByCategoryName(categoryName);
        assertEquals(expected , actual);

    }


    @Test
    public void testGetByCategoryName_whenCategoryNameDoesExists_isShouldThrowCategoryNotFoundedException(){
        when(categoryRepository.getByCategoryName(Mockito.anyString())).thenReturn(Optional.empty());
        assertThrows(GenericException.class , ()-> categoryService.getByCategoryName(Mockito.anyString()));
    }



    @Test
    public void testSaveCategory_itShouldSaveCategory(){
        SaveCategoryRequest request = new SaveCategoryRequest("test-category");
        Category category = Category.builder()
                .categoryName(request.getCatgoryName())
                .build();

        when(categoryRepository.save(category)).thenReturn(category);

        Category actual = categoryService.save(request);
        assertEquals(category , actual);

    }







}