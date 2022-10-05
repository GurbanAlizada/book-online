package com.example.repository;

import com.example.model.Book;
import com.example.model.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book , Long> ,JpaSpecificationExecutor<Book> {

    Optional<List<Book>> getByCategory_Id(Long categoryId);

    Optional<List<Book>> getByBookStatusAndUser_Id(BookStatus bookStatus,Long userId);

    Optional<List<Book>> getByTitle(String title);



}
