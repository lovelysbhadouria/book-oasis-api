package com.bookoasisapi.controller;

import com.bookoasisapi.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {
    
@Autowired
private MockMvc mockMvc;

private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void addBook_thenGetBook_worksEndToEnd() throws Exception {
        Book newBook = new Book(null, "Peace at Last", "Jill Murphy", 2018);

        String response = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBook)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Book created = objectMapper.readValue(response, Book.class);

        mockMvc.perform(get("/books/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("Jill Murphy"));
    }

    @Test
    void getBook_whenNotFound_returns404() throws Exception {
        mockMvc.perform(get("/books/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addBook_withBlankTitle_returns400() throws Exception {
        Book invalidBook = new Book(null, "", "Some Author", 2000);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBook)))
                .andExpect(status().isBadRequest());
    }
}
