package com.codewithkpk.blog.services.impl;

import com.codewithkpk.blog.entity.Category;
import com.codewithkpk.blog.exception.ResourceNotFoundException;
import com.codewithkpk.blog.payloads.CategoryDto;
import com.codewithkpk.blog.repo.CategoryRepo;
import com.codewithkpk.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServicesImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDto createCategory(CategoryDto dto) {
        Category cat = this.modelMapper.map(dto, Category.class);
        Category addCat = this.categoryRepo.save(cat);
        return this.modelMapper.map(addCat,CategoryDto.class);
    }
    @Override
    public CategoryDto updateCategory(CategoryDto dto, Integer categoryId) {
        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));
        cat.setCategoryTitle(dto.getCategoryTitle());
        cat.setCategoryDescription(dto.getCategoryDescription());
        Category updateCategory = this.categoryRepo.save(cat);
        return this.modelMapper.map(updateCategory,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
      Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));
      this.categoryRepo.delete(cat);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));
        return this.modelMapper.map(cat,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> Categories = this.categoryRepo.findAll();
        List<CategoryDto> categoryDtos = Categories.stream().map((cat)->this.modelMapper.map(cat,CategoryDto.class)).collect(Collectors.toList());
        return categoryDtos;
    }
}
