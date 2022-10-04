package com.example.api;


import com.example.dtos.SaveCategoryRequest;
import com.example.model.Category;
import com.example.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/category")
public class CategoryRestController {


    private final CategoryService categoryService;


    @PostMapping("/save")
    public ResponseEntity<Category> save(@Valid @RequestBody SaveCategoryRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryService.save(request));
    }



}
