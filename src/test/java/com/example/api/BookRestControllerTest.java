package com.example.api;

import com.example.dtos.BookListItemResponse;
import com.example.dtos.SaveBookRequest;
import com.example.model.BookStatus;
import com.example.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
class BookRestControllerTest {


    @MockBean
    private BookService bookService;


    @Autowired
      MockMvc mockMvc ;


    private  ObjectMapper objectMapper = new ObjectMapper();



    @Test
    @WithMockUser(value = "spring")
    public void testSave_whenValidSaveBookRequestBody() throws Exception {

        SaveBookRequest request =  SaveBookRequest.builder()
                .title("title")
                .totalPage(123)
                .bookStatus(BookStatus.READED)
                .categoryId(1L)
                .lastPageNumber(12)
                .authorName("authorName")
                .publisher("test")
                .build();

        BookListItemResponse response = BookListItemResponse.builder()
                .title(request.getTitle())
                .authorName(request.getAuthorName())
                .publisher(request.getPublisher())
                .totalPage(request.getTotalPage())
                .bookStatus(request.getBookStatus())
                .categoryName("test-categoty-name")
                .build();

        when(bookService.save(request)).thenReturn(response);

        mockMvc
                .perform(post("/api/v1.0/book/save")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(serializeJson(request)))
                .andDo(print())
                .andExpect(jsonPath("$.title").value(request.getTitle()))
                .andExpect(status().isCreated());

    }











    private String serializeJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }


}