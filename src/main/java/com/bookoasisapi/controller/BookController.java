package com.book_oasis_api.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book_oasis_api.model.Book;
import com.book_oasis_api.service.BookService;

import jakarta.persistence.PostRemove;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;


@RestController
@RequestMapping("/books")
public class BookController {
    
private final BookService bookService;
public BookController(BookService bookService) {
    this.bookService = bookService;
}

// Adding Book method
@PostMapping
public ResponseEntity<Book> addBook(@RequestBody Book book) {
    Book createdBook = bookService.addBook(book);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);}

// Fetching Book method
@GetMapping("/{id}")
public ResponseEntity<Book> getBookById(@PathVariable Long id) {
    Book book = bookService.getBookById(id);
    return ResponseEntity.ok(book);
    }

// Update Book method   
@PutMapping("/{id}")
public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
    Book book = bookService.updateBook(id, updatedBook);
    return ResponseEntity.ok(book);

}

// Delete Book method
@DeleteMapping("/{id}")
public ResponseEntity<Book> deleteBook(@PathVariable Long id) {
    Book deletedBook = bookService.deleteBook(id);
    return ResponseEntity.ok(deletedBook);
}

// Get All books method
@GetMapping
public ResponseEntity<Page<Book>> getAllBooks(Pageable pageable) {
    Page<Book> booksPage = bookService.getAllBooks(pageable);
    return ResponseEntity.ok(booksPage);

}

}
