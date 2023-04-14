package com.codewithkpk.blog.services.impl;

import com.codewithkpk.blog.entity.Comment;
import com.codewithkpk.blog.entity.Posts;
import com.codewithkpk.blog.exception.ResourceNotFoundException;
import com.codewithkpk.blog.payloads.CommentDto;
import com.codewithkpk.blog.repo.CommentRepo;
import com.codewithkpk.blog.repo.PostRepo;
import com.codewithkpk.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Posts post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Post Id", postId));
        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment saveComment = this.commentRepo.save(comment);
        return this.modelMapper.map(saveComment,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment com = this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "Comment Id", commentId));
        this.commentRepo.delete(com);
    }
}
