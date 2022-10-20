package com.example.dtos;

import com.example.model.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.File;

@AllArgsConstructor
@Builder
@Data
public final class SaveBookRequest {


    @NotBlank
    private String title;

    @NotBlank
    private String authorName;

    @NotBlank
    private String publisher;

    @NotNull
    private BookStatus bookStatus;

    //@NotNull
    private File image;

    @NotNull
    private Integer lastPageNumber;

    @NotNull
    private Integer totalPage;

    @NotNull
    private Long categoryId;





}
