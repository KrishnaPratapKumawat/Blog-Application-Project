package com.codewithkpk.blog.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table
@Entity(name ="Categories")
@Data

public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;
    private String categoryTitle;
    private String categoryDescription;
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<Posts>posts= new ArrayList<>();
}
