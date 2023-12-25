package com.blog.repository;

import com.blog.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post,Long> {
//    @Query("SELECT p FROM Post p ORDER BY LOWER(p.Description) ASC")
//    Page<Post> findAllIgnoreCaseOrderByName(Pageable pageable);
}
