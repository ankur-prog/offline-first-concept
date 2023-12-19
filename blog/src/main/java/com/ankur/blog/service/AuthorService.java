package com.ankur.blog.service;

import com.ankur.blog.dto.AuthorRequest;
import com.ankur.blog.dto.AuthorResponse;
import com.ankur.blog.model.Author;
import com.ankur.blog.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorService {

    private final AuthorRepository authorRepository;
    public void createAuthor(AuthorRequest authorRequest) {
        Author author = Author.builder().email(authorRequest.getEmail()).name(authorRequest.getName()).build();
        authorRepository.save(author);
        log.info("Author with name {} and  email {} is created successfully.",author.getName(),author.getEmail());


    }

    public List<AuthorResponse> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(this::mapToAuthorResponse).collect(Collectors.toList());
    }

    private AuthorResponse  mapToAuthorResponse(Author author) {
        return  AuthorResponse.builder().id(author.getId()).name(author.getName()).email(author.getEmail()).build();
    }
}
