package com.example.service;


import com.example.dtos.BookListItemResponse;
import com.example.dtos.BookResponse;
import com.example.dtos.SaveBookRequest;
import com.example.exception.ErrorCode;
import com.example.exception.GenericException;
import com.example.model.Book;
import com.example.model.BookStatus;
import com.example.model.Category;
import com.example.model.User;
import com.example.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryService categoryService;
    private final AuthService authService;
    private final UserService userService;



    @Transactional
    public BookListItemResponse save(SaveBookRequest request){

      Category category = categoryService.loadCategory(request.getCategoryId());

      final Book book = Book.builder()
                .category(category)
                .bookStatus(request.getBookStatus())
                .authorName(request.getAuthorName())
                .title(request.getTitle())
                .lastPageNumber(request.getLastPageNumber())
                .totalPage(request.getTotalPage())
                .publisher(request.getPublisher())
                .build();

      final Book fromDb = bookRepository.save(book);

      return BookListItemResponse.builder()
              .id(fromDb.getId())
              .authorName(fromDb.getAuthorName())
              .bookStatus(fromDb.getBookStatus())
              .categoryName(fromDb.getCategory().getCategoryName())
              .title(fromDb.getTitle())
              .publisher(fromDb.getPublisher())
              .lastPageNumber(fromDb.getLastPageNumber())
              .totalPage(fromDb.getTotalPage())
              .build();
    }



    public List<BookResponse> listBooks(int pageNo , int size){

        final List<Book> books =  bookRepository.findAll(PageRequest.of(pageNo - 1 , size)).getContent();

        User user = userService.findByUserName(authService.getAuthenticatedUser().getUsername());

        List<BookResponse> result =  books.stream()
                .map(n->
              BookResponse.builder()
                      .id(n.getId())
                      .title(n.getTitle())
                      .authorName(n.getAuthorName())
              //        .imageUrl(n.getImage().getImageUrl())
                      .build())
                .collect(Collectors.toList());

        return result;
    }





    public List<BookResponse> searchByCategory(String categoryName){

        Category category = categoryService.getByCategoryName(categoryName);

        final List<Book> books = category.getBooks();

        List<BookResponse> result =  books.stream().map(n->
                        BookResponse.builder()
                                .id(n.getId())
                                .title(n.getTitle())
                                .authorName(n.getAuthorName())
              //                  .imageUrl(n.getImage().getImageUrl())
                                .build())
                .collect(Collectors.toList());

        return result;
    }




    public List<BookResponse> searchByCategoryId(Long categoryId){

        final List<Book> books = bookRepository.getByCategory_Id(categoryId).orElseThrow(()->new GenericException(HttpStatus.NOT_FOUND , ErrorCode.CATEGORY_NOT_FOUNDED));

        List<BookResponse> result =  books.stream().map(n->
                        BookResponse.builder()
                                .id(n.getId())
                                .title(n.getTitle())
                                .authorName(n.getAuthorName())
              //                  .imageUrl(n.getImage().getImageUrl())
                                .build())
                .collect(Collectors.toList());

        return result;
    }




    public List<BookResponse> searchByBookStatus(BookStatus bookStatus ){

        User user = userService.findByUserName(authService.getAuthenticatedUser().getUsername());

        List<Book> books = bookRepository.getByBookStatusAndUser_Id(bookStatus , user.getId()).orElseThrow(()-> new GenericException(HttpStatus.BAD_REQUEST , ErrorCode.BOOK_STATUS_NOT_FOUNDED));

        List<BookResponse> result = books.stream()
                .map(n->
        BookResponse.builder()
                .id(n.getId())
        //        .imageUrl(n.getImage().getImageUrl())
                .build())
                .collect(Collectors.toList());
        return result;
    }


    public List<BookResponse> searchByTitle(String title){

        List<Book> books = bookRepository.getByTitle(title).orElseThrow(()->new GenericException( HttpStatus.BAD_REQUEST , ErrorCode.BOOK_NOT_FOUNDED ));

        List<BookResponse> result =  books.stream().map(n->
                BookResponse.builder()
                        .id(n.getId())
                        .authorName(n.getAuthorName())
                        .title(n.getTitle())
        //                .imageUrl(n.getImage().getImageUrl())
                        .build())
                .collect(Collectors.toList());

        return result;
    }



    @Transactional
    public BookResponse updateLastPageNumber(Long bookId , int pageNo){

        Book book = bookRepository.findById(bookId).orElseThrow(()->new GenericException(HttpStatus.NOT_FOUND , ErrorCode.BOOK_NOT_FOUNDED));
        book.setLastPageNumber(pageNo);
        Book fromDb =  bookRepository.save(book);

        return BookResponse.builder()
                .id(bookId)
                .authorName(fromDb.getAuthorName())
                .title(fromDb.getTitle())
                .build();
    }




    public BookResponse delete(Long id){
        Book book = bookRepository.findById(id).orElseThrow( ()-> new GenericException(HttpStatus.NOT_FOUND , ErrorCode.BOOK_NOT_FOUNDED , "Kitab Bulunamadi"));
        bookRepository.delete(book);
        return BookResponse.builder()
                .title(book.getTitle())
                .authorName(book.getAuthorName())
                .id(book.getId())
                .build();
    }





}
