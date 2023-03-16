package com.codewithkpk.blog.controller;

import com.codewithkpk.blog.payloads.ApiResponse;
import com.codewithkpk.blog.payloads.CommentDto;
import com.codewithkpk.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;
@PostMapping("addComment/{postId}")
public ResponseEntity<CommentDto>createComment(@RequestBody CommentDto comment, @PathVariable Integer postId){
    CommentDto serviceComment = this.commentService.createComment(comment, postId);
    return new ResponseEntity<CommentDto>(serviceComment, HttpStatus.CREATED);
}
@DeleteMapping("/deleteComment/{commentId}")
    public ResponseEntity<ApiResponse>deleteComment(@PathVariable Integer commentId){
    this.commentService.deleteComment(commentId);
    return new ResponseEntity<ApiResponse>(new ApiResponse("Comment delete Successfully!!",true),HttpStatus.OK);
}
}
