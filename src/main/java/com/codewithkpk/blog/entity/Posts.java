package com.codewithkpk.blog.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;
    private String postTitle;
    private String postContent;
    private String postImageName;
    private Date currentDate;
    @ManyToOne
    private Category category;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Comment> comment = new HashSet<>();
}
