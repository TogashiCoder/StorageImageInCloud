package com.globaltasks.cloudinary.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Image {
    @Id
    @GeneratedValue
    private Integer id;
    private String imageUrl;
    private String cloudinaryImageId;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
