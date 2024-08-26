package com.globaltasks.cloudinary.service;

import com.globaltasks.cloudinary.dto.responce.CloudinaryResponse;
import com.globaltasks.cloudinary.model.Image;
import com.globaltasks.cloudinary.model.Product;
import com.globaltasks.cloudinary.model.Video;
import com.globaltasks.cloudinary.repository.ProductRepository;
import com.globaltasks.cloudinary.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private ProductRepository repository;

    @Transactional
    public Product createProductWithMedia(Product product, List<MultipartFile> images, List<MultipartFile> videos) {
        List<Image> imageList = new ArrayList<>();
        List<Video> videoList = new ArrayList<>();

        // Process my beautiful images
        for (MultipartFile file : images) {
            FileUploadUtil.assertAllowed(file, FileUploadUtil.IMAGE_PATTERN);
            String fileName = FileUploadUtil.getFileName(file.getOriginalFilename());
            CloudinaryResponse response = cloudinaryService.uploadFile(file, fileName, "image");

            Image image = new Image();
            image.setImageUrl(response.getUrl());
            image.setCloudinaryImageId(response.getPublicId());
            image.setProduct(product);

            imageList.add(image);
        }

        // also Process my wonderful videos
        for (MultipartFile file : videos) {
            FileUploadUtil.assertAllowed(file, FileUploadUtil.VIDEO_PATTERN);
            String fileName = FileUploadUtil.getFileName(file.getOriginalFilename());
            CloudinaryResponse response = cloudinaryService.uploadFile(file, fileName, "video");

            Video video = new Video();
            video.setVideoUrl(response.getUrl());
            video.setCloudinaryVideoId(response.getPublicId());
            video.setProduct(product);

            videoList.add(video);
        }

        product.setImages(imageList);
        product.setVideos(videoList);
        return repository.save(product);
    }
}
