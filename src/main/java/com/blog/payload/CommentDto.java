package com.blog.payload;


import com.blog.entity.Post;
import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private String name;
    private String email;
    private String body;
    private int upvotes;
    private int downvotes;
   // private Post postId;
}
