package com.bookoasisapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bookoasisapi.exception.BookNotFoundException;
import com.bookoasisapi.model.Book;
import com.bookoasisapi.repository.BookRepository;

@Service
public class BookService {
    
private final BookRepository bookRepository;
public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
}

public Book addBook(Book book) {
    return bookRepository.save(book);
}

public Book getBookById(Long id) {
    return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
}

public Book updateBook(Long id, Book updatedBook) {
    return bookRepository.findById(id)
            .map(book -> {
                book.setTitle(updatedBook.getTitle());
                book.setAuthor(updatedBook.getAuthor());
                book.setPublicationYear(updatedBook.getPublicationYear());
                return bookRepository.save(book);
            })
            .orElseThrow(() -> new BookNotFoundException(id));
        }

public void deleteBook(Long id) {
  Book book = bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException(id));
    bookRepository.delete(book);
}

public Page<Book> getAllBooks(Pageable pageable) {
    return bookRepository.findAll(pageable);

}
}
