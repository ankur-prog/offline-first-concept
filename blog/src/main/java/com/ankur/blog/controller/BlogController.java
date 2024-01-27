package com.ankur.blog.controller;


import com.ankur.blog.dto.BlogPostRequest;
import com.ankur.blog.dto.BlogPostResponse;
import com.ankur.blog.service.BlogPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


/**
 * @author ankur
 * Blog Controller API for all blogging services
 */

@RestController
@RequestMapping("/api/v1/blogs")
@RequiredArgsConstructor
@Tag(name = "Blogs",description = "Blogging API")

public class BlogController {

    private final BlogPostService blogPostService;

    /**
     *Create Blogs
     * @param BlogPostRequest
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create Blogs")
    @ApiResponses(value = {@ApiResponse(responseCode = "201",description = "Created")})
    public void createBlog(@RequestBody BlogPostRequest BlogPostRequest) {
        blogPostService.createBlog(BlogPostRequest);
    }


    /**
     *
     * @return get all blogs
     */
    @GetMapping
    @Operation(summary = "Get all Blogs")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",description = "OK"),@ApiResponse(responseCode = "204",description = "No Content")})
    public ResponseEntity<List<BlogPostResponse>> getAllBlogs() {
        List<BlogPostResponse> blogs = blogPostService.getAllBlogs();
        if (blogs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    /**
     *
     * @param id
     * @return get blog by id
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get Blog by Id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",description = "OK"),@ApiResponse(responseCode = "404",description = "Blog not found")})
    public ResponseEntity<BlogPostResponse> getBlogById(@PathVariable("id") Long id) {
        BlogPostResponse blog = blogPostService.getBlogById(id);
        if (blog == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(blog, HttpStatus.OK);
    }

    /**
     *
     * @param title
     * @param content
     * @return
     */
    @PutMapping("/{id}")
    @Operation(summary = "modify blog")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",description = "OK"),@ApiResponse(responseCode = "404",description = "Blog not found")})
    public ResponseEntity<String> updateBlog(@PathVariable("id") Long id, @RequestParam("title")  String title,@RequestParam("content") String content) {
        Boolean updatePrice = blogPostService.updateBlog(id,title,content);
        if (updatePrice) {
            return ResponseEntity.ok("Blog  updated successfully.");
        } else return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    @Operation(description = "Delete Blog ")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",description = "OK"),@ApiResponse(responseCode = "404",description = "Blog Not Found")})
    public ResponseEntity<String> deleteBlog(@PathVariable("id") Long id) {
        Boolean deleteBlog = blogPostService.deleteBlog(id);
        if (deleteBlog) {
            return ResponseEntity.ok("Blog is deleted successfully.");
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blog id not found.");
    }


}


