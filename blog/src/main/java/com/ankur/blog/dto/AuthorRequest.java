package com.ankur.blog.dto;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Data
public class AuthorRequest {
    private String name;
    private String email;
}
