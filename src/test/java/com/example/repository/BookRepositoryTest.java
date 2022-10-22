package com.example.repository;

import com.example.dtos.BookListItemResponse;
import com.example.dtos.SaveBookRequest;
import com.example.dtos.UserDto;
import com.example.model.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@DataJpaTest
@ActiveProfiles(value = "integration")
class BookRepositoryTest {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BookRepository bookRepository;


    @Autowired
    private ImageRepository imageRepository;

    @Test
    public void givenBookObject_whenSave_itShouldReturnBook(){



        User user = new User(2L , "username" , "password");
        User savedUser = userRepository.save(user);


        Category category = new Category(1L,  null ,null , "test-categoryName" , null );
        Category savedCategory = categoryRepository.save(category);

        Image image = new Image();
        Image savedImage = imageRepository.save(image);


        Book book = Book.builder()
                .category(savedCategory)
                .user(savedUser)
                .authorName("test")
                .image(savedImage)
                .publisher("test")
                .bookStatus(BookStatus.READED)
                .title("test")
                .totalPage(123)
                .lastPageNumber(12)
                .build();


        Book fromDb = bookRepository.save(book);

        assertThat(fromDb).isNotNull();
        //assertThat(fromDb.getId()).isGreaterThan(0);

    }


    @Test
    public void testGetByCategory_Id_itShouldReturnOptionalOfListBook(){

        User user = new User(1L , "username" , "password");
        User savedUser = userRepository.save(user);


        Category category = new Category(1L,  null ,null , "test-categoryName" , null );
        Category savedCategory = categoryRepository.save(category);

        Image image = new Image();
        Image savedImage = imageRepository.save(image);


        Book book = Book.builder()
                .category(savedCategory)
                .user(savedUser)
                .authorName("test")
                .image(savedImage)
                .publisher("test")
                .bookStatus(BookStatus.READED)
                .title("test")
                .totalPage(123)
                .lastPageNumber(12)
                .build();


        Book fromDb1 = bookRepository.save(book);



        User user2 = new User(2L , "username2" , "password");
        User savedUser2 = userRepository.save(user2);



        Category savedCategory2 = categoryRepository.getByCategoryName(category.getCategoryName()).get();

        Book book2 = Book.builder()
                .category(savedCategory2)
                .user(savedUser2)
                .authorName("test")
                .image(savedImage)
                .publisher("test")
                .bookStatus(BookStatus.READED)
                .title("test")
                .totalPage(123)
                .lastPageNumber(12)
                .build();


        Book fromDb2 = bookRepository.save(book2);



        List<Book> books = new ArrayList<>();
        books.add(fromDb1);
        books.add(fromDb2);

        List<Book> books1 =  bookRepository.getByCategory_Id(category.getId()).orElseThrow();

        assertEquals(books.get(0).getAuthorName() ,  books1.get(0).getAuthorName());


    }


    @Test
    public void testGetByTitle_itShouldReturnOptionalOfListOfBook(){
        User user = new User(1L , "username" , "password");
        User savedUser = userRepository.save(user);


        Category category = new Category(1L,  null ,null , "test-categoryName" , null );
        Category savedCategory = categoryRepository.save(category);

        Image image = new Image();
        Image savedImage = imageRepository.save(image);


        Book book = Book.builder()
                .category(savedCategory)
                .user(savedUser)
                .authorName("test")
                .image(savedImage)
                .publisher("test")
                .bookStatus(BookStatus.READED)
                .title("test")
                .totalPage(123)
                .lastPageNumber(12)
                .build();


        Book fromDb1 = bookRepository.save(book);

        List<Book> books = new ArrayList<>();
        books.add(fromDb1);

        List<Book> fromDbList = bookRepository.getByTitle(book.getTitle()).orElseThrow();

        assertEquals(books.get(0).getAuthorName() , fromDbList.get(0).getAuthorName());

    }










}