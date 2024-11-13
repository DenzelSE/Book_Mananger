package com.project.Book_management_system;

import com.project.Book_management_system.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void bookLifecycleTest() throws Exception {
        // Create a book
        Book book = new Book();
        book.setTitle("Integration Test Book");
        book.setAuthor("Integration Author");
        book.setIsbn("9876543210");
        book.setPublicationYear(2023);

        // POST request to create book
        String bookJson = mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Book createdBook = objectMapper.readValue(bookJson, Book.class);

        // GET request to retrieve book
        mockMvc.perform(get("/api/books/" + createdBook.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(book.getTitle()));

        // PUT request to update book
        book.setTitle("Updated Integration Test Book");
        mockMvc.perform(put("/api/books/" + createdBook.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Integration Test Book"));

        // DELETE request to remove book
        mockMvc.perform(delete("/api/books/" + createdBook.getId()))
                .andExpect(status().isOk());

        // Verify book is deleted
        mockMvc.perform(get("/api/books/" + createdBook.getId()))
                .andExpect(status().isNotFound());
    }
}