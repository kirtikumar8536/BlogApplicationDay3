package com.blog.service.impl;

import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.CommentDto;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import com.blog.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private PostRepository postRepository;
    private CommentRepository commentRepository;

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id:" + postId));
        Comment comment = new Comment();
        // set post to comment entity
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        comment.setPost(post);
        // comment entity to DB
        Comment saveComment = commentRepository.save(comment);
        CommentDto dto = new CommentDto();
        dto.setId(saveComment.getId());
        dto.setName(saveComment.getName());
        dto.setEmail(saveComment.getEmail());
        dto.setBody(saveComment.getBody());
        return dto;
    }

    @Override
    public void deleteComment(Long commentId) {
       // Optional<Comment> byId = commentRepository.findById(commentId);
       Comment comment = commentRepository.findById(commentId).orElseThrow(
               () -> new ResourceNotFoundException("Comment Not Found With Id:" + commentId)
       );
        // why here is error  System.out.println("printing Post data in service impl: " + comment.toString());
        // System.out.println(comment!=null);
      //System.out.println("comment obj at serviceImpl: "+ comment);
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
//   commentRepository.findByPostId(postId).orElseThrow(
//          () -> new ResourceNotFoundException("Comment Not Found With Id:" + postId));

        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentDto> commentDtos = comments.stream().map(c -> mapToDto(c)).collect(Collectors.toList());
        return commentDtos;
    }

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> commments = commentRepository.findAll();
        List<CommentDto> commentDtos = commments.stream().map(c -> mapToDto(c)).collect(Collectors.toList());

        return commentDtos;
    }
    //update comment
    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post Not Found With Id: "+postId));
        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment Not Found With Id:"+ commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new ResourceNotFoundException("Comment does not belongs to post"+postId);
        }
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment updatedComment = commentRepository.save(comment);
        CommentDto updatedCommentDto = mapToDto(updatedComment);
        return updatedCommentDto;
    }

    @Override
    public CommentDto upvoteCommentByIdAndPostId(Long postId, Long commentId) {
        Comment comment = commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment Not Found with ID: " + commentId + " for Post ID: " + postId));
                comment.upvote();
        Comment upvotedComment = commentRepository.save(comment);
        CommentDto upvotedcommentDto = mapToDto(upvotedComment);
        return upvotedcommentDto;
    }
    @Override
    public CommentDto downvoteCommentByIdAndPostId(Long postId, Long commentId) {
        Comment comment = commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment Not Found with ID: " + commentId + " for Post ID: " + postId));
        comment.downvote();
        Comment downvotedComment = commentRepository.save(comment);
        CommentDto downvotedCommentDto = mapToDto(downvotedComment);
        return downvotedCommentDto;
    }

    public CommentDto mapToDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setName(comment.getName());
        dto.setEmail(comment.getEmail());
        dto.setBody(comment.getBody());
        dto.setUpvotes(comment.getUpvotes());
        dto.setDownvotes(comment.getDownvotes());
        //dto.setPostId(comment.getPost());
        return dto;
    }
}
