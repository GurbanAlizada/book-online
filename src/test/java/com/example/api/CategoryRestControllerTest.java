package com.example.api;

import com.example.dtos.SaveCategoryRequest;
import com.example.model.Category;
import com.example.service.CategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@ActiveProfiles(value = "integration")
class CategoryRestControllerTest {



    @MockBean
    private CategoryService categoryService;



    @Autowired
    MockMvc mockMvc ;


    private ObjectMapper objectMapper = new ObjectMapper();



    @Test
    @WithMockUser(value = "spring")
    public void testSave_whenGivenValidSaveCategoryRequest_itShouldReturnCategory() throws Exception {

        SaveCategoryRequest request = SaveCategoryRequest.builder()
                .categoryName("test-name")
                .build();


        Category category = Category.builder()
                .categoryName(request.getCategoryName())
                .build();



        when(categoryService.save(request)).thenReturn(category);


        mockMvc
                .perform(post("/api/v1.0/category/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeJson(request)))
                .andDo(print())
                .andExpect(jsonPath("$.categoryName").value(category.getCategoryName()))
                .andExpect(status().isCreated());

    }




    private String serializeJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }





}