package com.ankur.blog;
import com.ankur.blog.model.BlogPost;
import lombok.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Data
@Getter
@Setter

public class BlogPostTest {



    BlogPost blogPost = new BlogPost();

    @Test
    public void testBlogPost() {

        blogPost.setTitle("Test Title");
        blogPost.setContent("Test Content");
        assertEquals("Test Title",blogPost.getTitle());
        assertEquals("Test Content",blogPost.getContent());
    }



}
