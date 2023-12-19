package com.ankur.blog.dto;

import com.ankur.blog.model.Author;

import lombok.*;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class BlogPostRequest {

    private String title;

    private String content;

    private Author author;
}
