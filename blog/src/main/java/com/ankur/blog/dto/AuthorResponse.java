package com.ankur.blog.dto;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class AuthorResponse {
    private Long id;
    private String name;
    private String email;

}
