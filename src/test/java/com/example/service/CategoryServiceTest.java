package com.example.service;

import com.example.exception.ErrorCode;
import com.example.exception.GenericException;
import com.example.model.Category;
import com.example.repository.CategoryRepository;
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
@ActiveProfiles(value = "integration")
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;


    @Mock
    private CategoryRepository categoryRepository;


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
                "Category not found by given id");

        when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        GenericException actual = assertThrows(GenericException.class, () -> categoryService.loadCategory(Mockito.anyLong()));

        verify(categoryRepository , times(1)).findById(Mockito.anyLong());
        assertEquals(expectedError.getErrorCode() , actual.getErrorCode());


    }


}