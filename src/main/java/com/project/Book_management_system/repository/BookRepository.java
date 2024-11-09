package com.project.Book_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.Book_management_system.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
    
}
