package controllerTest;

import com.ankur.blog.controller.AuthorController;
import org.junit.jupiter.api.Test;

import  com.ankur.blog.controller.BlogController;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class BlogControllertest {


    private final BlogController blogController;
    private final AuthorController authorController;


    MockMvc mockMvc;

    public BlogControllertest(BlogController blogController, AuthorController authorController) {
        this.blogController = blogController;
        this.authorController = authorController;
    }

    @Test
    void createBlogTest() throws Exception {
        mockMvc.perform((RequestBuilder) post("/api/v1/blogs"))
                .andExpect(status().isCreated());


    }




}
