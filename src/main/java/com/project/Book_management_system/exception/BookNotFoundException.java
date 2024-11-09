package com.project.Book_management_system.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(long id){
        super("Could not find book with id: " + id);
    }
}
