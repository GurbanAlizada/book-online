package com.example.api;

import com.example.dtos.BookListItemResponse;
import com.example.dtos.BookResponse;
import com.example.dtos.SaveBookRequest;
import com.example.model.BookStatus;
import com.example.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/book")
public class BookRestController {

    private final BookService bookService;



    @PostMapping("/save")
    public ResponseEntity<BookListItemResponse> save(@Valid @RequestBody SaveBookRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookService.save(request));
    }


    @GetMapping("/listBooks")
    public ResponseEntity<List<BookResponse>> listBooks(
            @RequestParam(name = "pageNo" ) int pageNo ,
            @RequestParam(name = "size") int size){

        return ResponseEntity.ok(bookService.listBooks(pageNo, size));
    }



    @GetMapping("/searchByCategory/{categoryName}")
    public ResponseEntity<List<BookResponse>> searchByCategory(@PathVariable("categoryName") String categoryName){
        return ResponseEntity.ok(bookService.searchByCategory(categoryName));
    }



    @GetMapping("/searchByCategoryId/{categoryId}")
    public ResponseEntity<List<BookResponse>> searchByCategoryId(@PathVariable("categoryId") Long categoryId){
        return ResponseEntity.ok(bookService.searchByCategoryId(categoryId));
    }



    @GetMapping("/searchByBookStatus")
    public ResponseEntity<List<BookResponse>> searchByBookStatus(@RequestParam(name = "bookstatus") BookStatus bookStatus ){
        return ResponseEntity.ok(bookService.searchByBookStatus(bookStatus));
    }


    @GetMapping("/searchByTitle/{title}")
    public ResponseEntity<List<BookResponse>>  searchByTitle(@PathVariable("title") String title){
        return ResponseEntity.ok(bookService.searchByTitle(title));
    }




    @PutMapping("/updateLastPageNumber/{bookId}/{pageNo}")
    public ResponseEntity<BookResponse> updateLastPageNumber(@PathVariable("bookId") Long bookId ,@PathVariable("pageNo") int pageNo){
        return ResponseEntity.ok(bookService.updateLastPageNumber(bookId, pageNo));
    }


    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<BookResponse> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(bookService.delete(id));
    }



    }
