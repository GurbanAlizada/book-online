package com.example.dtos;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class SaveCategoryRequest {

    @NotBlank
    private String catgoryName;

}
