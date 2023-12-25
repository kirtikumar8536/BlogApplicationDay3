package com.blog.controller;

import com.blog.payload.PostDto;
import com.blog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    /*@PostMapping// 1if we don't want to show/send any response to postman
    public void createPost(@RequestBody PostDto postDto){
        //1.this will take json data from postman and store into the PostDto object(payload)
        //2.send postDto obj to service layer by calling the below code
        postService.createPost(postDto);
    }*/

    /*@PostMapping//2if we want to show/send any response to postman
    public ResponseEntity<String> createPost(@RequestBody PostDto postDto){
        //1.this will take json data from postman and store into the PostDto object(payload)
        //2.send postDto obj to service layer by calling the below code
        postService.createPost(postDto);
        return new ResponseEntity<>("post is created", HttpStatus.CREATED);//201
    }*/

    @PostMapping//if we want to show/send any dto response to postman
    public ResponseEntity<Object> createPost(@Valid @RequestBody PostDto postDto, BindingResult bindingResult) {
        //? or ResponseEntity<Object> bcz object is super most class of java
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);//string type
        }
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);//201 //postdto type
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>("post is deleted!", HttpStatus.OK);
    }

    //http://localhost:8080/api/posts?pageNo=0&pageSize=5&sortBy=title&sortDir=desc/asc
    @GetMapping //http://localhost:8080/api/posts?pageNo=0&pageSize=5&sortByField=title
    public ResponseEntity<List<PostDto>> getAllPosts(
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(name = "sortByField", defaultValue = "id", required = false) String sortByField,
            @RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        List<PostDto> postDto = postService.getAllPost(pageNo, pageSize, sortByField, sortDir);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @PutMapping//http://localhost:8080/api/posts?postId=1
    public ResponseEntity<PostDto> updatePost(
            @RequestParam(name="postId") Long postId,
            @RequestBody PostDto postDto)
    {
        PostDto dto = postService.updatePost(postId, postDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

}
