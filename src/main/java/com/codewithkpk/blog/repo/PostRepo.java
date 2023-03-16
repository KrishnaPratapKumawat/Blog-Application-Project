package com.codewithkpk.blog.repo;

import com.codewithkpk.blog.entity.Category;
import com.codewithkpk.blog.entity.Posts;
import com.codewithkpk.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Posts,Integer> {
    List<Posts>findByUser(User user);
    List<Posts>findByCategory(Category category);
    List<Posts>findByPostTitleContaining(String postTitle);

}
