package com.blog.service;

import com.blog.payload.PostDto;

import java.util.List;

public interface PostService {
    //1public void createPost(PostDto postDto);
    public PostDto createPost(PostDto postDto);

    void deletePost(Long id);

    List<PostDto> getAllPost(int pageNo, int pageSize, String sortByField, String sortDir);

    PostDto updatePost(Long postId, PostDto postDto);
}
