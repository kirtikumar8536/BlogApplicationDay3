package com.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok's annotation to generate getters, setters, toString, equals, and hashCode methods
@AllArgsConstructor // Lombok's annotation to generate an all-args constructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    @NotEmpty
    @Size(min = 2, message = "Title should be at least 2 character")
    private String title;//if we are not giving message attribute then default msg will be displayed
    @NotEmpty
    @Size(min = 4, message = "Description should be at least 4 character")
    private String Description;
    @NotEmpty
    @Size(min = 4, message = "content should be at least 4 character")
    private String content;
   // private String message;//additional field i.e not present in entity
   {
       System.out.println("atpostdto "+this.getContent());
       System.out.println("atpostdto "+this.getId());
   }
}
// spring validation should be happened in controller layer
// spring validation annotation used in payload