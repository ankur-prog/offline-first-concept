package com.ankur.blog.service;

import com.ankur.blog.dto.BlogPostRequest;
import com.ankur.blog.dto.BlogPostResponse;
import com.ankur.blog.model.BlogPost;
import com.ankur.blog.repository.BlogPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class BlogPostService {
    private final BlogPostRepository blogPostRepository;


    private final DataSource dataSource;
    public void createBlog(BlogPostRequest blogPostRequest) {

        BlogPost blogPost = BlogPost.builder().author(blogPostRequest.getAuthor())
                                                .title(blogPostRequest.getTitle())
                                                .content(blogPostRequest.getContent())
                                                .createdAt(LocalDateTime.now())
                                                .modifiedAt(LocalDateTime.now())
                                                .version(1.0)
                                                .build();

        blogPostRepository.save(blogPost);
        log.info("Blog with author {} and  title {} is created successfully.",blogPost.getAuthor(),blogPost.getTitle());
    }




    public List<BlogPostResponse> getAllBlogs() {
        List<BlogPost> blogs = blogPostRepository.findAllByOrderByModifiedAtDesc();
        return blogs.stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }

    private BlogPostResponse mapToProductResponse(BlogPost blogPost) {
        return BlogPostResponse.builder()
                .id(blogPost.getId())
                .author(blogPost.getAuthor())
                .title(blogPost.getTitle())
                .content(blogPost.getContent())
                .createdAt(blogPost.getCreatedAt())
                .modifiedAt(blogPost.getModifiedAt())
                .version(blogPost.getVersion())
                .build();
    }

    public Boolean updateBlog(Long id, String title, String content) {
        Optional<BlogPost> blogPostOptional = blogPostRepository.findByIdAndTitleIgnoreCase(id,title);
        if (blogPostOptional.isPresent()) {
            BlogPost blogPost = blogPostOptional.get();
            blogPost.setContent(content);
            blogPost.setModifiedAt(LocalDateTime.now());
            blogPost.setVersion(blogPost.getVersion()+1);
            blogPostRepository.save(blogPost);
            return true;
        }
        return false;
    }

    public Boolean deleteBlog(Long id) {
        try {
            if (blogPostRepository.existsById(id)) {
                deleteBlogAndInsertRecord(id);
                return true;
            }
        } catch (SQLException e) {
            // Log or handle the exception appropriately
            e.printStackTrace();
        }
        return false;
    }

    private void deleteBlogAndInsertRecord(Long id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);  // Start a transaction

            // Delete the blog post
            blogPostRepository.deleteById(id);

            // Insert into deleted_records
            String insertDeletedRecordQuery = "INSERT INTO deleted_records (id, table_name) VALUES (?, 't_blog_post')";
            try (PreparedStatement stmt = conn.prepareStatement(insertDeletedRecordQuery)) {
                stmt.setLong(1, id);
                stmt.executeUpdate();
            }

            conn.commit();  // Commit the transaction
        }
    }


    public BlogPostResponse getBlogById(Long id) {
        Optional<BlogPost> blogPostOptional = blogPostRepository.findById(id);
        if (blogPostOptional.isPresent()) {
            return mapToProductResponse(blogPostOptional.get());
        }
        return null;
    }
}
