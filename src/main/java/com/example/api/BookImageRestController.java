package com.example.api;


import com.example.adapters.cloudinary.inter.CloudServiceInter;
import com.example.service.BookService;
import com.example.service.ImageStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/bookImage")
@RequiredArgsConstructor
public class BookImageRestController {


    private final BookService bookService;
    private final ImageStoreService imageStoreService;
    private final CloudServiceInter cloudServiceInter;

    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadImage(@RequestParam("bookId") Long bookId, @RequestParam("file") MultipartFile file) {
       // final String uploadImg = imageStoreService.uploadImg(convert(file), bookId);
        final String uploadImg = cloudServiceInter.uploadImage(file);
        bookService.saveImage(bookId, uploadImg);
        return ResponseEntity.ok(uploadImg);
    }





}
