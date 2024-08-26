package com.globaltasks.cloudinary.service;

import com.cloudinary.Cloudinary;
import com.globaltasks.cloudinary.dto.responce.CloudinaryResponse;
import com.globaltasks.cloudinary.exception.FuncErrorException;
import com.globaltasks.cloudinary.util.FileUploadUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


@Service
public class CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;

    @Transactional
    public CloudinaryResponse uploadFile(MultipartFile file, String fileName) {
        try {
            final Map result = cloudinary.uploader().upload(file.getBytes(), Map.of("public_id", "nhndev/product/" + fileName));
            final String url = (String) result.get("secure_url");
            final String publicId = (String) result.get("public_id");
            return CloudinaryResponse.builder().publicId(publicId).url(url).build();
        } catch (Exception e) {
            throw new FuncErrorException("Failed to upload file");
        }
    }
}
