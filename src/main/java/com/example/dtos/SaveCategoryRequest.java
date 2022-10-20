package com.example.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class SaveCategoryRequest {

    @NotBlank
    private String catgoryName;

}
