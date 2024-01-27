package com.ankur.blog.repository;

import com.ankur.blog.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlogPostRepository extends JpaRepository<BlogPost,Long> {
    Optional<BlogPost> findByIdAndTitleIgnoreCase(Long id, String title);

    List<BlogPost> findAllByOrderByModifiedAtDesc();
}
