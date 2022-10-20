package com.example.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BookResponse {


    private Long id;

    private String authorName;

    private String title;

    private String imageUrl;

}
