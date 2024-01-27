package com.ankur.blog;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import com.ankur.blog.model.Author;


import static org.junit.jupiter.api.Assertions.assertEquals;
@Getter
@Setter
@Data
public class AutorTest {

    @Test
    public void testAuthor() {
        Author author = new Author();
        author.setName("Ankur");
        author.setEmail("test@gmail.com");
        assertEquals("Ankur",author.getName());
        assertEquals("test@gmail.com",author.getEmail());
    }




}
