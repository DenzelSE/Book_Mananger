package com.project.Book_management_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.Book_management_system.model.Book;
import com.project.Book_management_system.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> Books = bookService.getAllBooks();
        return ResponseEntity.ok(Books);
    }

    @GetMapping("/{id}")
    public ResponseEntity <Book> getBookById(@PathVariable long id){
            return bookService.getBookById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());

    }
    
    
}
