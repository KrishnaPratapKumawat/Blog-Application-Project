package com.codewithkpk.blog.payloads;

import com.codewithkpk.blog.entity.Category;
import com.codewithkpk.blog.entity.Comment;
import com.codewithkpk.blog.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
public class PostsDto {
    private Integer postId;
    private String postTitle;
    private String postContent;
    private String postImageName;
    private Date currentDate;
    private Category category;
    private User user;
    private Set<CommentDto>comment = new HashSet<>();
}
