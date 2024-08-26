package com.globaltasks.cloudinary.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Integer price;
    private Integer discount;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Video> videos;
}
