package com.codewithkpk.blog.controller;

import com.codewithkpk.blog.payloads.ApiResponse;
import com.codewithkpk.blog.payloads.PostsDto;
import com.codewithkpk.blog.services.EmailAuthentication;
import com.codewithkpk.blog.services.FileService;
import com.codewithkpk.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "http://localhost:4200")
public class PostsController {
    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Autowired
    private EmailAuthentication emailAuthentication;
    @Value("${project.image}")
    private String path;
   @PostMapping("/addPost/user/{userId}/category/{categoryId}")
    public ResponseEntity<String> createPost(@RequestBody PostsDto postsDto, @PathVariable Integer userId, @PathVariable Integer categoryId) throws Exception {
        boolean savePost = this.emailAuthentication.sendEmail(userId,categoryId,postsDto);
        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Post is created");
    }
    @GetMapping("/user/{userId}/")
    public ResponseEntity<List<PostsDto>>getPostByUser(@PathVariable Integer userId){
      List<PostsDto> postsDtoList =  this.postService.getPostByUsers(userId);
      return new ResponseEntity<List<PostsDto>>(postsDtoList,HttpStatus.OK);
    }
    @GetMapping("/category/{categoryId}/")
    public ResponseEntity<List<PostsDto>>getPostByCategory(@PathVariable Integer categoryId){
        List<PostsDto> postsDtoList =  this.postService.getPostByCategory(categoryId);
        return new ResponseEntity<List<PostsDto>>(postsDtoList,HttpStatus.OK);
    }
    @GetMapping("/getAllPost")
    public ResponseEntity<List<PostsDto>> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false)Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "8", required = false)Integer pageSize
    ){
        List<PostsDto> list =this.postService.getAllPost(pageNumber, pageSize);
        return ResponseEntity.ok(list);
    }
    @GetMapping("/getPostById/{postId}")
    public ResponseEntity<PostsDto>getPostById(@PathVariable Integer postId){
PostsDto postsDto = this.postService.getPostById(postId);
return new ResponseEntity<PostsDto>(postsDto,HttpStatus.OK);
    }
    @DeleteMapping("/deletePost/{postId}")
    public ResponseEntity<ApiResponse>deleteCategory(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Post is deleted Successfully!!",true),HttpStatus.OK);
    }
    @PutMapping("/updatePost/{postId}")
    public ResponseEntity<PostsDto>updatePost(@RequestBody PostsDto postsDto, @PathVariable Integer postId){
        PostsDto postUpdateDto=  this.postService.updatePost(postsDto,postId);
        return new ResponseEntity<PostsDto>(postUpdateDto, HttpStatus.OK);
    }
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<PostsDto>>searchByPostTitle(@PathVariable("keyword")String keyword){
       List<PostsDto>result = this.postService.postSearchByKeyword(keyword);
       return new ResponseEntity<List<PostsDto>>(result,HttpStatus.OK);
    }
    @PostMapping("/image/upload/{postId}")
    public  ResponseEntity<PostsDto> uploadPostImage(@RequestParam("image")MultipartFile image,@PathVariable Integer postId) throws IOException {
        String uploadImage = this.fileService.uploadImage(path, image);
        PostsDto postDto = this.postService.getPostById(postId);
        postDto.setPostImageName(uploadImage);
        PostsDto updatePost = this.postService.updatePost(postDto,postId);
        return new ResponseEntity<PostsDto>(updatePost,HttpStatus.OK);
    }
    @GetMapping(value = "/image/{postImageName}",produces = MediaType.IMAGE_PNG_VALUE)
    public void getImage(@PathVariable String postImageName, HttpServletResponse response) throws IOException {
        InputStream inputStream = this.fileService.getResource(path,postImageName);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(inputStream,response.getOutputStream());
    }
}
