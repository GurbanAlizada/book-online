package com.example.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookResponse {


    private Long id;

    private String authorName;

    private String title;

    private String imageUrl;

}
