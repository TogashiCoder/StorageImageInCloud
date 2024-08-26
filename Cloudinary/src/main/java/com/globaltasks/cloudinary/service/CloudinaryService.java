package com.globaltasks.cloudinary.service;

import com.cloudinary.Cloudinary;
import com.globaltasks.cloudinary.dto.responce.CloudinaryResponse;
import com.globaltasks.cloudinary.exception.FuncErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;

    public CloudinaryResponse uploadFile(MultipartFile file, String fileName, String resourceType) {
        try {
            final Map<String, Object> result = cloudinary.uploader().upload(file.getBytes(), Map.of(
                    "public_id", "nhndev/product/" + fileName,
                    "resource_type", resourceType
            ));
            final String url = (String) result.get("secure_url");
            final String publicId = (String) result.get("public_id");
            return CloudinaryResponse.builder().publicId(publicId).url(url).build();
        } catch (Exception e) {
            throw new FuncErrorException("Failed to upload file");
        }
    }
}
