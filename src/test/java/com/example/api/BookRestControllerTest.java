package com.example.api;

import com.example.dtos.BookListItemResponse;
import com.example.dtos.BookResponse;
import com.example.dtos.SaveBookRequest;
import com.example.model.BookStatus;
import com.example.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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


    @Test
    @WithMockUser(value = "spring")
    public void testSearchByBookStatus_whenGivenBookStatus_itShouldReturnListOfBookResponse() throws Exception {
        BookStatus bookStatus1 = BookStatus.READED;

        BookResponse bookResponse1 = BookResponse.builder()
                .title("test1")
                .id(1L)
                .build();

        BookResponse bookResponse2 = BookResponse.builder()
                .title("test2")
                .id(2L)
                .build();

        List<BookResponse> list = new ArrayList<>();
        list.add(bookResponse1);
        list.add(bookResponse2);


        when(bookService.searchByBookStatus(bookStatus1)).thenReturn(list);

        mockMvc
                .perform(get("/api/v1.0/book/searchByBookStatus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("bookstatus" , String.valueOf(bookStatus1)))
                .andDo(print())
                .andExpect(jsonPath("$.[0].title").value(bookResponse1.getTitle()))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(value = "spring")
    public void testSearchByStatus_whenGivenTitle_itShouldReturnListOfBookResponse() throws Exception {

        String title = "test-title";

        BookResponse bookResponse1 = BookResponse.builder()
                .title("test1")
                .id(1L)
                .build();

        BookResponse bookResponse2 = BookResponse.builder()
                .title("test2")
                .id(2L)
                .build();

        List<BookResponse> list = new ArrayList<>();
        list.add(bookResponse1);
        list.add(bookResponse2);


        when(bookService.searchByTitle(title)).thenReturn(list);

        mockMvc
                .perform(get(String.format("/api/v1.0/book/searchByTitle/%s" , title))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.[0].title").value(bookResponse1.getTitle()))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(status().isOk());



    }







    @Test
    @WithMockUser(value = "spring")
    public void testListBooks_whenGivenPageSizeAndPageNo_itShouldReturnListOfBookResponse() throws Exception {

        int pageNo = 1 ;
        int size = 2;

        BookResponse bookResponse1 = BookResponse.builder()
                .title("test1")
                .id(1L)
                .build();

        BookResponse bookResponse2 = BookResponse.builder()
                .title("test2")
                .id(2L)
                .build();

        List<BookResponse> list = new ArrayList<>();
        list.add(bookResponse1);
        list.add(bookResponse2);


        when(bookService.listBooks(pageNo , size)).thenReturn(list);

        mockMvc
                .perform(get("/api/v1.0/book/listBooks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("pageNo" , String.valueOf(pageNo))
                        .param("size" , String.valueOf(size)))
                .andDo(print())
                .andExpect(jsonPath("$.[0].title").value(bookResponse1.getTitle()))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(status().isOk());

    }





    @Test
    @WithMockUser(value = "spring")
    public void testListBooks_whenGivenPageSizeAndPageNo_itShouldReturnListOfBookResponse2() throws Exception {

        int pageNo = 1 ;
        int size = 2;

        BookResponse bookResponse1 = BookResponse.builder()
                .title("test1")
                .id(1L)
                .build();

        BookResponse bookResponse2 = BookResponse.builder()
                .title("test2")
                .id(2L)
                .build();

        List<BookResponse> list = new ArrayList<>();
        list.add(bookResponse1);
        list.add(bookResponse2);


        when(bookService.listBooks(pageNo , size)).thenReturn(list);

        mockMvc
                .perform(get("/api/v1.0/book/listBooks?pageNo="+pageNo+"&size="+size)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.[0].title").value(bookResponse1.getTitle()))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(status().isOk());

    }



    @Test
    @WithMockUser(username = "test",authorities ="ADMIN")
    public void testUpdateLastPageNumber_whenGivenBookIdAndPageNo_itShouldReturnBookResponse() throws Exception {


        BookResponse bookResponse = BookResponse.builder()
                .title("test1")
                .id(1L)
                .authorName("test")
                .imageUrl("dsssds")
                .build();

        when(bookService.updateLastPageNumber(Mockito.anyLong() , Mockito.anyInt())).thenReturn(bookResponse);

        mockMvc
                .perform(put("/api/v1.0/book/updateLastPageNumber/{bookId}/{pageNo}" ,1,1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.title").value(bookResponse.getTitle()))
                .andExpect(status().isOk());

    }




    @Test
    //@WithMockUser(value = "spring")
    @WithMockUser(username = "test",authorities ="ADMIN")
    public void testDeleteBook_whenGivenBookId_itShouldReturnBookResponse() throws Exception {

        BookResponse bookResponse = BookResponse.builder()
                .title("test1")
                .id(1L)
                .authorName("test")
                .imageUrl("dsssds")
                .build();

        when(bookService.delete(Mockito.anyLong() )).thenReturn(bookResponse);

        mockMvc
                .perform(delete("/api/v1.0/book/deleteBook/{id}" ,1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
               // .andExpect(jsonPath("$.title").value(bookResponse.getTitle()))
                .andExpect(status().isOk());

    }








    private String serializeJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }


}