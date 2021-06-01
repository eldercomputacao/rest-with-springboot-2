package com.elderpereira.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elderpereira.rest.data.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
