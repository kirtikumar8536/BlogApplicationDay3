package com.blog.service;

import com.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    public CommentDto createComment(Long postId, CommentDto commentDto);

    void deleteComment(Long commentId);

    List<CommentDto> getCommentsByPostId(Long postId);

    List<CommentDto> getAllComments();

    CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto);


    CommentDto upvoteCommentByIdAndPostId(Long postId, Long commentId);//upvote

    CommentDto downvoteCommentByIdAndPostId(Long postId, Long commentId);//downvote
}
