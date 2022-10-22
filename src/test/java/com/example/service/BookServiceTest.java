package com.example.service;

import com.example.dtos.BookListItemResponse;
import com.example.dtos.BookResponse;
import com.example.dtos.SaveBookRequest;
import com.example.dtos.UserDto;
import com.example.exception.GenericException;
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
import java.util.Optional;
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



    @Test
    public void testSearchByCategory_whenCategoryExists_itShouldReturnBookResponse(){
        String categoryName = "category-name";

        Image image = new Image();

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

            Category category = Category.builder()
                    .categoryName("test")
                    .books(books)
                    .build();

        when(categoryService.getByCategoryName(categoryName)).thenReturn(category);

        List<Book> list = category.getBooks();

        BookResponse bookResponse1 = new BookResponse( 1L, book1.getAuthorName() , book1.getTitle(), book1.getImage().getImageUrl() );
        BookResponse bookResponse2 = new BookResponse( 2L, book2.getAuthorName() , book2.getTitle(), book2.getImage().getImageUrl() );


        List<BookResponse> expected = new ArrayList<>();
        expected.add(bookResponse1);
        expected.add(bookResponse2);

        List<BookResponse> actual = bookService.searchByCategory(categoryName);

        assertEquals(expected.get(1).getAuthorName() , actual.get(1).getAuthorName());
        verify(categoryService , times(1)).getByCategoryName(categoryName);
    }


    @Test
    public void testSearchByCategoryId_whenCategoryExists_itShouldReturnBookResponseList(){
        Long categoryId = 1L;

        Category category = Category.builder()
                .categoryName("test-category")
                .build();
        category.setId(categoryId);

        Image image  = new Image();

        Book book1 = Book.builder()
                .title("title")
                .category(category)
                .publisher("publisher")
                .image(image)
                .build();

        Book book2 = Book.builder()
                .title("title")
                .category(category)
                .publisher("publisher")
                .image(image)
                .build();

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);

        when(bookRepository.getByCategory_Id(categoryId)).thenReturn(Optional.of(books));

        BookResponse bookResponse1 = new BookResponse( 1L, book1.getAuthorName() , book1.getTitle(), book1.getImage().getImageUrl() );
        BookResponse bookResponse2 = new BookResponse( 2L, book2.getAuthorName() , book2.getTitle(), book2.getImage().getImageUrl() );


        List<BookResponse> expected = new ArrayList<>();
        expected.add(bookResponse1);
        expected.add(bookResponse2);

        List<BookResponse> actual = bookService.searchByCategoryId(categoryId);

        assertEquals(expected.get(1).getAuthorName() , actual.get(1).getAuthorName());
        verify(bookRepository , times(1)).getByCategory_Id(categoryId);


    }




    @Test
    public void testSearchByCategoryId_whenCategoryDoesNotExists_itShouldThrowGenericsException(){

        when(bookRepository.getByCategory_Id(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(GenericException.class , ()-> bookService.searchByCategoryId(Mockito.anyLong()));
    }




    @Test
    public void testSearchByBookStatus_whenBookStatusAndUserExists_isShouldReturnBookResponseList(){
        BookStatus bookStatus = BookStatus.READED;

        User user = new User(2L , "username" , "password");

        UserDto userDto = UserDto.builder()
                .username(user.getUsername())
                .id(user.getId())
                .build();

        when(authService.getAuthenticatedUser()).thenReturn(userDto);

        when(userService.findByUserName(Mockito.anyString())).thenReturn(user);

        Image image  = new Image();

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


        when(bookRepository.getByBookStatusAndUser_Id(bookStatus,user.getId())).thenReturn(Optional.of(books));


        BookResponse bookResponse1 = new BookResponse( 1L, book1.getAuthorName() , book1.getTitle(), book1.getImage().getImageUrl() );
        BookResponse bookResponse2 = new BookResponse( 2L, book2.getAuthorName() , book2.getTitle(), book2.getImage().getImageUrl() );


        List<BookResponse> expected = new ArrayList<>();
        expected.add(bookResponse1);
        expected.add(bookResponse2);

        List<BookResponse> actual = bookService.searchByBookStatus(bookStatus);

        assertEquals(expected.get(1).getAuthorName() , actual.get(1).getAuthorName());
        verify(bookRepository , times(1)).getByBookStatusAndUser_Id(bookStatus , user.getId());



    }



    @Test
    public void testSearchByBookStatus_whenBookStatusAndUserDoesNotExists_isShouldReturnBookResponseList(){
        User user = new User(2L , "username" , "password");

        UserDto userDto = UserDto.builder()
                .username(user.getUsername())
                .id(user.getId())
                .build();

        when(authService.getAuthenticatedUser()).thenReturn(userDto);

        when(userService.findByUserName(Mockito.anyString())).thenReturn(user);

        when(bookRepository.getByBookStatusAndUser_Id(BookStatus.READED,2L)).thenReturn(Optional.empty());
        assertThrows(GenericException.class , ()-> bookService.searchByBookStatus(BookStatus.READED));

    }
    @Test
    public void testSearchByTitle_whenBooksExistsGivenTitle_itShouldReturnBookResponse(){
        String title = "title";
        Image image  = new Image();

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


        when(bookRepository.getByTitle(title)).thenReturn(Optional.of(books));

        BookResponse bookResponse1 = new BookResponse( 1L, book1.getAuthorName() , book1.getTitle(), book1.getImage().getImageUrl() );
        BookResponse bookResponse2 = new BookResponse( 2L, book2.getAuthorName() , book2.getTitle(), book2.getImage().getImageUrl() );


        List<BookResponse> expected = new ArrayList<>();
        expected.add(bookResponse1);
        expected.add(bookResponse2);

        List<BookResponse> actual = bookService.searchByTitle(title);

        assertEquals(expected.get(1).getAuthorName() , actual.get(1).getAuthorName());
        verify(bookRepository , times(1)).getByTitle(title);

    }


    @Test
    public void testSearchByTitle_whenBooksDoesNotExistsGivenTitle_itShouldReturnBookResponse(){


        when(bookRepository.getByTitle(Mockito.anyString())).thenReturn(Optional.empty());

        assertThrows(GenericException.class , () ->  bookService.searchByTitle(Mockito.anyString()) );
    }




    @Test
    public void testUpdateLastPageNumber_whenBookExist_itShouldReturnBookResponse(){
        Long bookId  = 1L;
        int pageNo = 111;

        Image image = new Image();

        Book book = Book.builder()
                .title("title")
                .publisher("publisher")
                .image(image)
                .build();
        book.setId(bookId);

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        book.setLastPageNumber(pageNo);

        when(bookRepository.save(book)).thenReturn(book);


        BookResponse expected = new BookResponse( book.getId(), book.getAuthorName() , book.getTitle(), book.getImage().getImageUrl() );

        BookResponse actual  =bookService.updateLastPageNumber(bookId , pageNo);

        assertEquals(expected.getAuthorName() , actual.getAuthorName());
        verify(bookRepository , times(1)).findById(bookId);

    }

    @Test
    public void testUpdateLastPageNumber_whenBookDoesNotExist_itShouldReturnBookResponse(){
        when(bookRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        assertThrows(GenericException.class , ()-> bookService.updateLastPageNumber(Mockito.anyLong() , 2));

    }
    
    @Test
    public void testDelete_whenBookExists_itShouldBookResponse(){
        Long id = 1L;

        Image image = new Image();

        Book book = Book.builder()
                .title("title")
                .publisher("publisher")
                .image(image)
                .build();
        book.setId(id);

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        BookResponse expected = new BookResponse( book.getId(), book.getAuthorName() , book.getTitle(), book.getImage().getImageUrl() );

        BookResponse actual  =bookService.delete(id);

        assertEquals(expected.getAuthorName() , actual.getAuthorName());
        verify(bookRepository , times(1)).findById(id);


    }



    @Test
    public void testDelete_whenBookDoesNotExists_itShouldBookResponse(){
        when(bookRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(GenericException.class  , ()-> bookService.delete(Mockito.anyLong()));
    }





    }