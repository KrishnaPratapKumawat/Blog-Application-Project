package com.codewithkpk.blog.repo;

import com.codewithkpk.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment,Integer> {
}
