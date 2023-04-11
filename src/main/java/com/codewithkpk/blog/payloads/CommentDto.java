package com.codewithkpk.blog.payloads;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private int commentId;
    private String Content;
}
