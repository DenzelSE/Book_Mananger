package com.project.Book_management_system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.Book_management_system.model.Book;
import com.project.Book_management_system.repository.BookRepository;

@Service
public class BookService {
    
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(long id){
        return bookRepository.findById(id);
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }
    
    public Optional<Book> updateBook(Long id, Book bookDetails) {
        Optional<Book> book = bookRepository.findById(id);
        
        if (book.isPresent()) {
            Book existingBook = book.get();
            existingBook.setTitle(bookDetails.getTitle());
            existingBook.setAuthor(bookDetails.getAuthor());
            existingBook.setIsbn(bookDetails.getIsbn());
            existingBook.setPublicationYear(bookDetails.getPublicationYear());
            return Optional.of(bookRepository.save(existingBook));
        }
        
        return Optional.empty();
    }

    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
