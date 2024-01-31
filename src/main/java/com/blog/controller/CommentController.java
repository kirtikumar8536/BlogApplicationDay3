package com.blog.controller;

import com.blog.payload.CommentDto;
import com.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestParam(name = "refPostId") Long postId, @RequestBody CommentDto commentDto) {
        CommentDto dto = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>("Comment is deleted :", HttpStatus.OK);
    }

    @GetMapping("/{postId}")//http://localhost:8080/api/comments/1
    public ResponseEntity<List<CommentDto>> getCommentByPostId(@PathVariable Long postId) {
        List<CommentDto> commentDtoList = commentService.getCommentsByPostId(postId);
        return new ResponseEntity<>(commentDtoList, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments() {
        List<CommentDto> commentDtos = commentService.getAllComments();
        return new ResponseEntity<>(commentDtos, HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postId") Long postId,
                                                    @PathVariable(value = "id") Long commentId,
                                                    @RequestBody CommentDto commentDto) {
        CommentDto updatedComment = commentService.updateComment(postId, commentId, commentDto);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @PostMapping("/posts/{postId}/comments/{commentId}/upvote")
    public ResponseEntity<CommentDto> upvoteComment(@PathVariable(value = "postId")Long postId,
                                                    @PathVariable(value = "commentId") Long commentId) {
        CommentDto commentDto = commentService.upvoteCommentByIdAndPostId(postId, commentId);
        return new ResponseEntity<>(commentDto,HttpStatus.OK);

    }
  @PostMapping("/posts/{postId}/comments/{commentId}/downvote")
    public ResponseEntity<CommentDto> downvoteComment(@PathVariable(value = "postId")Long postId,
                                                      @PathVariable(value = "commentId") Long commentId) {
        CommentDto commentDto = commentService.downvoteCommentByIdAndPostId(postId, commentId);
        return new ResponseEntity<>(commentDto,HttpStatus.OK);
    }

}
