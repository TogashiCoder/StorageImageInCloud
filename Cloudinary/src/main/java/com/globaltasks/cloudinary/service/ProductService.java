package com.globaltasks.cloudinary.service;

import com.globaltasks.cloudinary.dto.responce.CloudinaryResponse;
import com.globaltasks.cloudinary.exception.NotFoundException;
import com.globaltasks.cloudinary.model.Product;
import com.globaltasks.cloudinary.repository.ProductRepository;
import com.globaltasks.cloudinary.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductService {

    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private ProductRepository repository;




    @Transactional
    public Product createProduct(Product product) {
        return repository.save(product);
    }




    @Transactional
    public void uploadImage (final Integer id, final MultipartFile file)
    {
        final Product product = this.repository.findById(id)
                .orElseThrow(()->new NotFoundException("Product not found"));
        FileUploadUtil.assertAllowed (file, FileUploadUtil.IMAGE_PATTERN);
        final String fileName = FileUploadUtil.getFileName (file.getOriginalFilename());
        final CloudinaryResponse response = this.cloudinaryService. uploadFile(file, fileName);
        product.setImageUrl(response.getUrl());
        product.setCloudinaryImageId(response.getPublicId());
        this.repository.save(product);
    }


}
