package com.codewithkpk.blog.controller;

import com.codewithkpk.blog.payloads.ApiResponse;
import com.codewithkpk.blog.payloads.CategoryDto;
import com.codewithkpk.blog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/addCategory")
    public ResponseEntity<CategoryDto>createCategory(@RequestBody CategoryDto categoryDto){
      CategoryDto createCategory=  this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
    }

    @PutMapping("/updateCategory/{categoryId}")
    public ResponseEntity<CategoryDto>updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId){
        CategoryDto updateCategory=  this.categoryService.updateCategory(categoryDto,categoryId);
        return new ResponseEntity<CategoryDto>(updateCategory, HttpStatus.OK);
    }
    @DeleteMapping("/deleteCategory/{categoryId}")
    public ResponseEntity<ApiResponse>deleteCategory(@PathVariable Integer categoryId){
     this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category is deleted Successfully!!",true),HttpStatus.OK);
    }
    @GetMapping("/getCategoryById/{categoryId}")
    public ResponseEntity<CategoryDto>getCategoryById(@PathVariable Integer categoryId){
        CategoryDto categoryDto = this.categoryService.getCategory(categoryId);
        return new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.OK);
    }
    @GetMapping("/getAllCategories")
    public ResponseEntity<List<CategoryDto>>getAllCategories(){
       List<CategoryDto> categories =  this.categoryService.getCategories();
       return ResponseEntity.ok(categories);
    }
    @DeleteMapping("/deletePost/{postId}")
    public void deletePost(@PathVariable Integer postId){

    }

}
