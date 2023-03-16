package com.codewithkpk.blog.services;
import com.codewithkpk.blog.payloads.PostsDto;

public interface EmailAuthentication {
    boolean sendEmail(Integer userId, Integer categoryId, PostsDto postsDto);

}
