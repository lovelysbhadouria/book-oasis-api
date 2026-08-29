package com.book_oasis_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book_oasis_api.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
}
