package com.example.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class SaveCategoryRequest {

    @NotBlank
    private String categoryName;

}
