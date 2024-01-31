package com.blog.repository;

import com.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    //select * from comment where post.id=postid
    List<Comment>findByPostId(Long postId);//used in getCommentsByPostId service impl

    Optional<Comment> findByIdAndPostId(Long commentId, Long postId);//used in upvote& downvote
     //select c1_0.id,c1_0.body,c1_0.downvotes,c1_0.email,c1_0.name,c1_0.post_id,c1_0.upvotes from comments c1_0 where c1_0.id=? and c1_0.post_id=?
     // @Query("SELECT c FROM Comment c WHERE c.id = :commentId AND c.post.id = :postId")
}
