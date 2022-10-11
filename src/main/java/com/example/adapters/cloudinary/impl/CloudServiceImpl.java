package com.example.adapters.cloudinary.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.example.adapters.cloudinary.inter.CloudServiceInter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Service
public class CloudServiceImpl implements CloudServiceInter {


    private Cloudinary cloudinary;

    public CloudServiceImpl() {
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("cloud_name","alizada" );
        valuesMap.put("api_key", "231755285583346" );
        valuesMap.put("api_secret","P2WIwBBTmrFrWK3FxfBT1gkfugQ" );
        cloudinary = new Cloudinary(valuesMap);
    }

    @SneakyThrows
    @Override
    public String uploadImage(MultipartFile multipartFile) {
        File file = null;
        try {
            file = convert(multipartFile);
            Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            file.delete();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return "http://res.cloudinary.com/alizada/image/upload/v1659467567/"+file.getName();
    }

    // http://res.cloudinary.com/alizada/image/upload/v1659467567/hmwvtnt1qqpelevibg5s.jpg
    // http://res.cloudinary.com/alizada/image/upload/v1659467417/b61tjdqlihztitzylqjx.png


    private File convert(final MultipartFile multipartFile) {
        // convert multipartFile to File
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


    /*
    private  File convert(MultipartFile multipartFile) throws IOException {
            File file = new File(multipartFile.getOriginalFilename());
            FileOutputStream fileOutput = new FileOutputStream(file);
            fileOutput.write(multipartFile.getBytes());
            fileOutput.close();

            return file;
    }

     */





}
