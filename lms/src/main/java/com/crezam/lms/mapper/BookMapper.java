package com.crezam.lms.mapper;

import com.crezam.lms.dto.BookDto;
import com.crezam.lms.entity.Books;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public Books dtoToEntity(BookDto bookDto)
    {
        Books books = new Books();
        books.setTitle(bookDto.getTitle());
        books.setAuthor(bookDto.getAuthor());
        books.setIsbn(bookDto.getIsbn());
        books.setAvailableCopies(bookDto.getAvailableCopies());
        books.setCategory(bookDto.getCategory());
        books.setPublishedYear(bookDto.getPublishedYear());
        return books;
    }

    public BookDto entityToDto(Books books)
    {
        BookDto bookDto = new BookDto();
        bookDto.setId(books.getId());
        bookDto.setTitle(books.getTitle());
        bookDto.setAuthor(books.getAuthor());
        bookDto.setIsbn(books.getIsbn());
        bookDto.setAvailableCopies(books.getAvailableCopies());
        bookDto.setCategory(books.getCategory());
        bookDto.setPublishedYear(books.getPublishedYear());
        return bookDto;

    }
}
