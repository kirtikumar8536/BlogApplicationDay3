package com.blog.service.impl;

import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.PostDto;
import com.blog.repository.PostRepository;
import com.blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostserviceImpl implements PostService {
    //   @Autowired --field injection
//    private PostRepository postRepo;
    private PostRepository postRepo;

    public PostserviceImpl(PostRepository postRepo) {
        // dependency injection using constructor
        this.postRepo = postRepo;
    }

    /* @Override
     public void createPost(PostDto postDto) {//called from controller comment 1,2
         //3.now create entity object and set the field using setter injection
         Post post = new Post();
         post.setTitle(postDto.getTitle());
         post.setContent(postDto.getContent());
         post.setDescription(postDto.getDescription());
         //4.using constructor dependency we can get the PostRepository obj PostRepo and able to call the save method of
         //jpa repository by passing entity object
         postRepo.save(post);

     }*/
    @Override
    public PostDto createPost(PostDto postDto) {//called from controller
        //3.now create entity object and set the field using setter injection
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());


        Post savedPost = postRepo.save(post);//return entity type
        // why dto/payload require
        PostDto dto = new PostDto();
        dto.setId(savedPost.getId());
        dto.setContent(savedPost.getContent());
        dto.setDescription(savedPost.getDescription());
        dto.setTitle(savedPost.getTitle());
        // in dto we can add one more field without bothering to the entity/Post
        // if we add field in dto it won't affect in our database
        //--------dto.setMessage("Post is Created");
        // if we do same thing to post/entity data there is chance of tempering
        return dto;
    }

    //    @Override
//    public void deletePost(Long id) {
//        Optional<Post> byId = postRepo.findById(id);
//        if(byId.isPresent()){
//            postRepo.deleteById(id);
//        }else {
//            throw new ResourceNotFoundException("Post Not found with id"+id);
//        }
//    }
    @Override
    public void deletePost(Long id) {
        //using java 8 feature

        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post Not Found With Id:" + id)
        );
        System.out.println("printing Post data in service impl: " + post);
        //Post(id=6, title=Coding Adventures, Description=Discovering Java 8, content=Embark on a journey.)
       //Post(id=16, title=Python for Beginners, description=Learn Python from Scratch, content=Start your programming journey with Python and build a solid foundation., comments=[])
        postRepo.deleteById(id);
    }

    @Override  //http://localhost:8080/api/posts?pageNo=0&pageSize=4&sortByField=description
    public List<PostDto> getAllPost(int pageNo, int pageSize, String sortByField, String sortDir) {
        //  Sort asc = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortDir).ascending() : Sort.by(sortDir).descending();
        //Sort.by(field name).ascending/descending
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortByField).ascending() : Sort.by(sortByField).descending();
        //Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortByField)); //converting String to Sort by Sort.by()
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> pagePosts = postRepo.findAll(pageable);
        List<Post> posts = pagePosts.getContent();
        List<PostDto> dtos = posts.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
        return dtos;

        /* solve:2 Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Post> pagePosts = postRepo.findAllIgnoreCaseOrderByName(pageable);
        List<Post> posts = pagePosts.getContent();
        List<PostDto> dtos = posts.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
        return dtos;*/

        // List<PostDto> dtos = posts.stream().map(p -> mapToDto(p)).toList();
        //List<PostDto> dtos = posts.stream().map(this::mapToDto).collect(Collectors.toList());
      /*  //we are getting all post data the from db ,using
        //here we are getting list of all post and its type is post
        List<Post> posts = postRepo.findAll();
        //but we want postDto object ?(we do not want our Post object to be tempered)
        //so that we are using PostDto object (this will go /interact with controller)
        // Create a list to store PostDto objects
        List<PostDto> postDto = new ArrayList<>();
        // Iterate through the list of Post entities and convert each to PostDto
        for (Post post : posts) {
            // Create a new PostDto object
            PostDto dto = new PostDto();
            // Set values from the Post entity to the PostDto object
            dto.setId(post.getId());
            dto.setContent(post.getContent());
            dto.setDescription(post.getDescription());
            dto.setTitle(post.getTitle());
            // Add the PostDto object to the list
            postDto.add(dto);
        }
        // Return the list of PostDto objects
        return postDto;*/

    }

    @Override
    public PostDto updatePost(Long postId, PostDto postDto) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post Not Found! with id" + postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        Post savePost = postRepo.save(post);
        PostDto dto = mapToDto(savePost);
        return dto;
    }

    PostDto mapToDto(Post post) {
        PostDto postDto = new PostDto();
        System.out.println("post id : "+post.getId());
        postDto.setId(post.getId());
        System.out.println("at service Impl"+postDto.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }


}
//all crud operation performed and spring validation is done+exception handling is done