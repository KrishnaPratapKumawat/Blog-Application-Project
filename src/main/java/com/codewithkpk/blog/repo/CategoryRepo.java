package com.codewithkpk.blog.repo;

import com.codewithkpk.blog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Integer> {
}
