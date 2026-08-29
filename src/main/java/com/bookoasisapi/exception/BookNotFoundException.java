package com.book_oasis_api.exception;

/**
 * BookNotFoundException
 */
public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(Long id) {
        super("Book not found with id: " + id);
    }

}
