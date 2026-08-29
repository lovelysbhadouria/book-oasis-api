package com.book_oasis_api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.book_oasis_api.exception.BookNotFoundException;
import com.book_oasis_api.model.Book;
import com.book_oasis_api.repository.BookRepository;

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

public Book deleteBook(Long id) {
    return bookRepository.findById(id)
            .map(book -> {
                bookRepository.delete(book);
                return book;
            })
            .orElseThrow(() -> new BookNotFoundException(id));
    }

public Page<Book> getAllBooks(Pageable pageable) {
    return bookRepository.findAll(pageable);

}
}
