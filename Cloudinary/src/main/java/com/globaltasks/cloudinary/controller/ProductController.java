package com.globaltasks.cloudinary.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globaltasks.cloudinary.model.Product;
import com.globaltasks.cloudinary.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

//http://localhost:8080/api/products/add
//Togashi Here
@RestController
@RequestMapping("api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(
            @RequestPart("product") String productJson,
            @RequestPart(value = "images", required = false) MultipartFile[] images,
            @RequestPart(value = "videos", required = false) MultipartFile[] videos) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Product product = mapper.readValue(productJson, Product.class);

            if ((images == null || images.length == 0) && (videos == null || videos.length == 0)) {
                return ResponseEntity.badRequest().body("Error: No images or videos provided");
            }

            // Handle my images
            List<MultipartFile> imageList = images != null ? Arrays.asList(images) : List.of();
            // Handle my videos
            List<MultipartFile> videoList = videos != null ? Arrays.asList(videos) : List.of();

            // Process product with images and videos
            Product savedProduct = productService.createProductWithMedia(product, imageList, videoList);
            return ResponseEntity.ok(savedProduct);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body("Error parsing product JSON: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating product: " + e.getMessage());
        }
    }
}
