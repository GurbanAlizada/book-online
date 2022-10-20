package com.example.service;

import com.example.dtos.BookListItemResponse;
import com.example.dtos.BookResponse;
import com.example.dtos.SaveBookRequest;
import com.example.dtos.UserDto;
import com.example.model.*;
import com.example.repository.BookRepository;
import com.example.repository.ImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BookServiceTest {


    private  BookRepository bookRepository;
    private  CategoryService categoryService;
    private  AuthService authService;
    private  UserService userService;
    private  ImageRepository imageRepository;
    private BookService bookService;

    @BeforeEach
    public void setUp(){
        bookRepository = Mockito.mock(BookRepository.class);
        categoryService = Mockito.mock(CategoryService.class);
        authService = Mockito.mock(AuthService.class);
        userService = Mockito.mock(UserService.class);
        imageRepository = Mockito.mock(ImageRepository.class);
        bookService = new BookService(bookRepository , categoryService , authService , userService ,imageRepository);
    }



    @Test
    public void testSaveBook(){


        SaveBookRequest request =SaveBookRequest.builder()
                .authorName("authorName")
                .title("title")
                .categoryId(1L)
                .build();

        String username = "username";

        User user = new User(2L , username , "password");

        UserDto userDto = UserDto.builder()
                .username(user.getUsername())
                .id(user.getId())
                .build();

        when(authService.getAuthenticatedUser()).thenReturn(userDto);

        when(userService.findByUserName(Mockito.anyString())).thenReturn(user);

        Category category = new Category(request.getCategoryId() ,  null ,null , "test-categoryName" , null );

        when(categoryService.loadCategory(Mockito.anyLong())).thenReturn(category);

        Book book = Book.builder()
                .category(category)
                .user(user)
                .authorName(request.getAuthorName())
                .title(request.getTitle())
                .build();

        when(bookRepository.save(book)).thenReturn(book);

        BookListItemResponse expected = BookListItemResponse.builder()
                .categoryName(book.getCategory().getCategoryName())
                .bookStatus(book.getBookStatus())
                .totalPage(book.getTotalPage())
                .authorName(book.getAuthorName())
                .title(book.getTitle())
                .build();


        BookListItemResponse actual = bookService.save(request);

        assertEquals(expected.getAuthorName(), actual.getAuthorName());
        assertEquals(expected.getCategoryName(), actual.getCategoryName());

        verify(bookRepository , times(1)).save(book);
        verify(authService , times(1)).getAuthenticatedUser();
        verify(userService , times(1)).findByUserName(user.getUsername());
        verify(categoryService , times(1)).loadCategory(Mockito.anyLong());

    }



    @Test
    public void testListBooks_itShouldReturnBookResponse(){

        Image image = new Image("test-url");

        Book book1 = Book.builder()
                .title("title")
                .publisher("publisher")
                .image(image)
                .build();

        Book book2 = Book.builder()
                .title("title")
                .publisher("publisher")
                .image(image)
                .build();

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);

        when(bookRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(books));

        String username = "username";

        User user = new User(2L , username , "password");

        UserDto userDto = UserDto.builder()
                .username(user.getUsername())
                .id(user.getId())
                .build();

        when(authService.getAuthenticatedUser()).thenReturn(userDto);

        when(userService.findByUserName(Mockito.anyString())).thenReturn(user);

        BookResponse response1 = BookResponse.builder()
                .title("title")
                .imageUrl(image.getImageUrl())
                .build();

        BookResponse response2 = BookResponse.builder()
                .title("title")
                .imageUrl(image.getImageUrl())
                .build();

        List<BookResponse> expected = new ArrayList<>();

        expected.add(response1);
        expected.add(response2);

        List<BookResponse> actual = bookService.listBooks(1, 5);

        assertEquals(expected.get(1).getAuthorName() , actual.get(1).getAuthorName());
        verify(bookRepository , times(1)).findAll(Mockito.any(PageRequest.class));
        verify(authService , times(1)).getAuthenticatedUser();
    }















}