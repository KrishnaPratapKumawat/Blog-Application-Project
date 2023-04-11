package com.codewithkpk.blog.services;

import com.codewithkpk.blog.payloads.PostsDto;

import java.util.List;

public interface PostService {
    PostsDto createPost(PostsDto postsDto,Integer userId, Integer categoryId);
    PostsDto updatePost(PostsDto postsDto, Integer postId);
     void deletePost(Integer postId);
     List<PostsDto>getAllPost(Integer pageNumber, Integer pageSize);
    PostsDto getPostById(Integer postId);
     List<PostsDto>getPostByCategory(Integer categoryId);
     List<PostsDto>getPostByUsers(Integer userId);
     List<PostsDto>postSearchByKeyword(String keyword);
}
