package com.ankur.blog.controller;


import com.ankur.blog.dto.AuthorRequest;
import com.ankur.blog.dto.AuthorResponse;
import com.ankur.blog.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ankur
 * Author Controller API for  author services
 */

@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
@Tag(name = "Author",description = "Product API")
public class AuthorController {
    private final AuthorService authorService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create Author")
    @ApiResponses(value = {@ApiResponse(responseCode = "201",description = "Created")})
    public void createAuthor(@RequestBody AuthorRequest authorRequest) {
        authorService.createAuthor(authorRequest);
    }

    @GetMapping
    @Operation(summary = "Get all Authors")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",description = "OK"),@ApiResponse(responseCode = "204",description = "No Content")})
    public ResponseEntity<List<AuthorResponse>> getAllAuthors() {
        List<AuthorResponse> authors = authorService.getAllAuthors();
        if (authors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

}
