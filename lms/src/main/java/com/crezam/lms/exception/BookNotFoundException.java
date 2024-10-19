package com.crezam.lms.exception;

import com.crezam.lms.dto.BookDto;

public class BookNotFoundException extends RuntimeException {
    private String isbn;
    public BookNotFoundException(String isbn) {
        super(String.format("Book not found with isbn: %s", isbn));
    }
}
