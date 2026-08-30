package com.bookoasisapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.bookoasisapi.exception.BookNotFoundException;
import com.bookoasisapi.model.Book;
import com.bookoasisapi.repository.BookRepository;
import org.mockito.MockitoAnnotations;

class BookServiceTest {

@Mock
private BookRepository bookRepository;

private BookService bookService;

@BeforeEach
void setup(){
MockitoAnnotations.openMocks(this);
bookService = new BookService(bookRepository);
}

@Test
void testAddBook() {
    Book book = new Book(null,"Robinhood","Howard Pyle",1883);
    Book savedBook = new Book(1L,"Robinhood","Howard Pyle",1883);

    when(bookRepository.save(book)).thenReturn(savedBook);

        Book result = bookService.addBook(book);

        assertEquals(1L, result.getId());
        assertEquals("Robinhood", result.getTitle());
    }

@Test
void testGetBookById() {
    Long bookId = 1L;
    Book book = new Book(bookId,"Robinhood","Howard Pyle",1883);

    when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        Book result = bookService.getBookById(bookId);

        assertEquals(bookId, result.getId());
        assertEquals("Robinhood", result.getTitle());
    }

@Test
void getBook_whenNotFound_throwsException() {
    when(bookRepository.findById(99L)).thenReturn(Optional.empty());
    assertThrows(BookNotFoundException.class, () -> bookService.getBookById(99L));
    }

@Test
void updateBook_updatesExistingFields() {
Book existing = new Book(1L, "Robinhood", "Howard Pyle", 1883);
        Book updatedData = new Book(null, "Robinhood2", "Howard Pyle", 1889);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(bookRepository.save(existing)).thenReturn(existing);

        Book result = bookService.updateBook(1L, updatedData);

        assertEquals("Robinhood2", result.getTitle());
        assertEquals(1889, result.getPublicationYear());
}

@Test
    void deleteBook_whenExists_deletesSuccessfully() {
        Book book = new Book(1L, "Robinhood", "Howard Pyle", 1883);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).delete(book);
    }

}