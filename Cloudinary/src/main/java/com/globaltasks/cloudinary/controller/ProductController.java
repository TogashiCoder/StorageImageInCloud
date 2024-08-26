package com.globaltasks.cloudinary.controller;

import com.globaltasks.cloudinary.model.Product;
import com.globaltasks.cloudinary.service.CloudinaryService;
import com.globaltasks.cloudinary.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CloudinaryService cloudinaryService;




    @PostMapping("/add")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productService.createProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

//
//    @PostMapping("/images/{id}")
//    private ResponseEntity<?> uploadImage(@PathVariable final Integer productId, @RequestPart final MultipartFile file) {
//        this.productService.uploadImage(productId, file);
//     return ResponseEntity.ok("Upload Success");
//    }

    @PostMapping("/images/{productId}")
    public ResponseEntity<?> uploadImage(@PathVariable("productId") Integer productId, @RequestPart("file") MultipartFile file) {
        try {
            this.productService.uploadImage(productId, file);
            return ResponseEntity.ok("Upload Success");
        } catch (Exception e) {
            // Log the exception message for debugging
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }





}
