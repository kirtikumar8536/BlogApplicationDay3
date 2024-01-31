package com.blog.entity;

import  javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data // Lombok annotation to generate getters, setters, toString, equals, and hashCode methods
@AllArgsConstructor // Lombok annotation to generate an all-args constructor
@NoArgsConstructor // Lombok annotation to generate a no-args constructor
@Entity // JPA annotation to mark the class as a JPA entity
@Table(name = "post") // JPA annotation to specify the table name for the entity
public class Post {

    @Id //  to mark the property as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)// JPA annotation to specify the generation strategy for the primary key
    private Long id;
    private String title;
    //private String Description;
    private String description;
    private String content;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Comment> comments=new ArrayList<>();

}

