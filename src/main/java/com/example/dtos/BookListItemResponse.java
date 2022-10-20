package com.example.dtos;


import com.example.model.BookStatus;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.File;

@Getter
@Builder
public final class BookListItemResponse {

    private Long id;

    private String title;

    private String authorName;

    private String publisher;

    private BookStatus bookStatus;

    private File image;

    private Integer lastPageNumber;

    private Integer totalPage;

    private String categoryName;


}
