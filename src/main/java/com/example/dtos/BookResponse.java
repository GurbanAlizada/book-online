package com.example.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {


    private Long id;

    private String authorName;

    private String title;

    private String imageUrl;

}
