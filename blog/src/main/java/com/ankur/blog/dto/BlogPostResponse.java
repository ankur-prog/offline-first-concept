package com.ankur.blog.dto;

import com.ankur.blog.model.Author;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class BlogPostResponse {
    private Long id;
    private String title;

    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private Double version;

    private Author author;
}