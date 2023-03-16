package com.codewithkpk.blog.services.impl;

import com.codewithkpk.blog.entity.Category;
import com.codewithkpk.blog.entity.Posts;
import com.codewithkpk.blog.entity.User;
import com.codewithkpk.blog.exception.ResourceNotFoundException;
import com.codewithkpk.blog.payloads.PostsDto;
import com.codewithkpk.blog.repo.CategoryRepo;
import com.codewithkpk.blog.repo.PostRepo;
import com.codewithkpk.blog.repo.UserRepository;
import com.codewithkpk.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepo categoryRepo;
    @Override
    public PostsDto createPost(PostsDto postsDto,Integer userId, Integer categoryId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));

        Posts post = this.modelMapper.map(postsDto, Posts.class);
        post.setPostImageName("default.png");
        post.setCurrentDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Posts savePost = this.postRepo.save(post);
        return this.modelMapper.map(savePost,PostsDto.class);
    }

    @Override
    public PostsDto updatePost(PostsDto postsDto, Integer postId) {
        Posts post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
        post.setPostContent(postsDto.getPostContent());
        post.setPostTitle(postsDto.getPostTitle());
        post.setPostImageName(postsDto.getPostImageName());
        Posts updatePost = this.postRepo.save(post);
        return this.modelMapper.map(updatePost,PostsDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Posts postsDelete = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
        this.postRepo.delete(postsDelete);

    }

    @Override
    public List<PostsDto> getAllPost(Integer pageNumber, Integer pageSize) {
    	Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<Posts> postsPage = this.postRepo.findAll(pageable);
        List<Posts> allPost = postsPage.getContent();
        List<PostsDto> list = allPost.stream().map((post) -> this.modelMapper.map(post, PostsDto.class)).collect(Collectors.toList());
        return list;
    }

    @Override
    public PostsDto getPostById(Integer postId) {
        Posts posts = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
        return this.modelMapper.map(posts,PostsDto.class);
    }

    @Override
    public List<PostsDto> getPostByCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));
        List<Posts> posts = this.postRepo.findByCategory(category);
        List<PostsDto> postsDtos=posts.stream().map((post)->this.modelMapper.map(post,PostsDto.class)).collect(Collectors.toList());
        return postsDtos;
    }

    @Override
    public List<PostsDto> getPostByUsers(Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        List<Posts>postsList = this.postRepo.findByUser(user);
        List<PostsDto>postsDtoList = postsList.stream().map((post) -> this.modelMapper.map(post,PostsDto.class)).collect(Collectors.toList());
        return postsDtoList;
    }

    @Override
    public List<PostsDto> postSearchByKeyword(String keyword) {
        List<Posts> posts = this.postRepo.findByPostTitleContaining(keyword);
        List<PostsDto>postsDtoList = posts.stream().map((post) -> this.modelMapper.map(post,PostsDto.class)).collect(Collectors.toList());
        return postsDtoList;
    }
}
