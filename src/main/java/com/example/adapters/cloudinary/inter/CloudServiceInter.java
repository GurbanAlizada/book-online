package com.example.adapters.cloudinary.inter;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CloudServiceInter {


    String uploadImage(MultipartFile multipartFile);


}
