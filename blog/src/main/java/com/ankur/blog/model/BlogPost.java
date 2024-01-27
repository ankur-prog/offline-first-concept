package com.ankur.blog.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Table(name = "t_blog_post")
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;

    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private Double version;

    @ManyToOne
    @JoinColumn(name = "t_author_id",referencedColumnName ="id", nullable = true)
    private Author author;
}
