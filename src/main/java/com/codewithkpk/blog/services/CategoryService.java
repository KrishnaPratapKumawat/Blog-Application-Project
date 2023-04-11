package com.codewithkpk.blog.services;

import com.codewithkpk.blog.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto dto);
    CategoryDto updateCategory(CategoryDto dto,Integer categoryId);
    public void deleteCategory(Integer categoryId);
    CategoryDto getCategory(Integer categoryId);
    List<CategoryDto>getCategories();
}
