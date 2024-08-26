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
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String videoUrl;
    private String cloudinaryVideoId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
