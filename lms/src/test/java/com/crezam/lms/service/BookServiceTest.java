package com.crezam.lms.service;

import com.crezam.lms.Repository.BookRepository;
import com.crezam.lms.dto.BookDto;
import com.crezam.lms.dto.CustomMessageDto;
import com.crezam.lms.dto.CustomPageResponseDto;
import com.crezam.lms.entity.Books;
import com.crezam.lms.exception.BookNotFoundException;
import com.crezam.lms.mapper.BookMapper;
import com.crezam.lms.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    private Books book;
    private BookDto bookDto;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        String id = UUID.randomUUID().toString();
        book = new Books(id, "1234567890", "Book", "Author", "Category", 5, 2024);
        bookDto = new BookDto(id,"1234567890", "Book", "Author", "Category", 5, 2024);
    }

    @Test
    void addBook() {
        when(bookMapper.dtoToEntity(bookDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.entityToDto(book)).thenReturn(bookDto);

        BookDto result = bookService.addBook(bookDto);

        assertEquals(bookDto, result);
        verify(bookRepository).save(any(Books.class));
    }

    @Test
    void updateBook() {
        when(bookRepository.findById(book.getIsbn())).thenReturn(Optional.of(book));
        when(bookMapper.entityToDto(book)).thenReturn(bookDto);

        BookDto updatedBookDto = BookDto.builder()
                .isbn("1234567890")
                .title("Book2")
                .author("Utkarsh")
                .category("Category")
                .availableCopies(5)
                .publishedYear(2021)
                .build();


        book.setTitle(updatedBookDto.getTitle());
        book.setAuthor(updatedBookDto.getAuthor());
        book.setAvailableCopies(updatedBookDto.getAvailableCopies());
        book.setCategory(updatedBookDto.getCategory());
        book.setPublishedYear(updatedBookDto.getPublishedYear());

        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.entityToDto(book)).thenReturn(updatedBookDto);

        BookDto result = bookService.updateBook(book.getIsbn(), updatedBookDto);

        assertEquals("Book2", result.getTitle());
        assertEquals("Utkarsh", result.getAuthor());
        assertEquals("Category", result.getCategory());
        assertEquals(5, result.getAvailableCopies());
        assertEquals(2021, result.getPublishedYear());
        verify(bookRepository).save(book);
    }


    @Test
    void updateBookNotFound() {
        when(bookRepository.findById(book.getIsbn())).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> {
            bookService.updateBook(book.getIsbn(), bookDto);
        });
        verify(bookRepository).findById(book.getIsbn());
    }

    @Test
    void findByCategory() {
        Page<Books> bookPage = new PageImpl<>(Collections.singletonList(book));
        when(bookRepository.findByCategoryIgnoreCase(any(PageRequest.class), anyString())).thenReturn(bookPage);
        when(bookMapper.entityToDto(book)).thenReturn(bookDto);
        CustomPageResponseDto<Object> result = bookService.findByCategory(0, 10, "Category");
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }

    @Test
    void getAllBooks() {
        Page<Books> bookPage = new PageImpl<>(Collections.singletonList(book));
        when(bookRepository.findAll(any(PageRequest.class))).thenReturn(bookPage);
        when(bookMapper.entityToDto(book)).thenReturn(bookDto);

        CustomPageResponseDto<Object> result = bookService.getAllBooks(0, 10);

        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }

    @Test
    void deleteBook() {
        when(bookRepository.findById(book.getIsbn())).thenReturn(Optional.of(book));

        CustomMessageDto result = bookService.deleteBook(book.getIsbn());

        assertTrue(result.isSuccess());
        assertEquals("Book has been removed successfully", result.getMessage());
        verify(bookRepository).delete(book);
    }


    @Test
    void deleteBookNotFound() {
        when(bookRepository.findById(book.getIsbn()))
                .thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class,
                () -> bookService.deleteBook(book.getIsbn()));
    }

    @Test
    void getBookSuccess() {
        when(bookRepository.findById(book.getIsbn())).thenReturn(Optional.of(book));
        when(bookMapper.entityToDto(book)).thenReturn(bookDto);
        BookDto result = bookService.getBook(book.getIsbn());
        assertEquals(bookDto, result);
    }

    @Test
    void getBookNotFound() {
        when(bookRepository.findById(book.getIsbn()))
                .thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class,
                () -> bookService.getBook(book.getIsbn()));
    }


}
