package com.crezam.lms.service.impl;

import com.crezam.lms.repository.BookRepository;
import com.crezam.lms.dto.BookDto;
import com.crezam.lms.dto.CustomMessageDto;
import com.crezam.lms.dto.CustomPageResponseDto;
import com.crezam.lms.entity.Books;
import com.crezam.lms.exception.BookNotFoundException;
import com.crezam.lms.mapper.BookMapper;
import com.crezam.lms.service.IBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements IBookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;


    @Override
    public BookDto addBook(BookDto bookDto) {

        Books books = bookMapper.dtoToEntity(bookDto);
        books.setId(UUID.randomUUID().toString());
        return bookMapper.entityToDto(bookRepository.save(books));
    }

    @Override
    public BookDto updateBook(String isbn, BookDto bookDto) {
        Books books = bookRepository
                .findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
        books.setTitle(bookDto.getTitle());
        books.setAuthor(bookDto.getAuthor());
        books.setIsbn(bookDto.getIsbn());
        books.setAvailableCopies(bookDto.getAvailableCopies());
        books.setCategory(bookDto.getCategory());
        books.setPublishedYear(bookDto.getPublishedYear());

        return bookMapper.entityToDto(bookRepository.save(books));

    }

    @Override
    public BookDto getBook(String isbn) {
        Books books = bookRepository
                .findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
        return bookMapper.entityToDto(books);

    }

    @Override
    public CustomMessageDto deleteBook(String isbn) {
        bookRepository
                .findByIsbn(isbn)
                .ifPresentOrElse(bookRepository::delete,
                        ()-> {
                    throw new BookNotFoundException(isbn);
                });

        return CustomMessageDto
                .builder()
                .message("Book has been removed successfully")
                .success(true)
                .httpStatus(HttpStatus.NO_CONTENT)
                .build();

    }

    @Override
    public CustomPageResponseDto<Object> getAllBooks(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Books> books = bookRepository.findAll(pageable);
        List<BookDto> allBooks = books
                .stream()
                .map(bookMapper::entityToDto)
                .toList();
        return CustomPageResponseDto
                .builder()
                .pageNumber(books.getNumber())
                .pageSize(books.getSize())
                .isLast(books.isLast())
                .totalElements(books.getTotalElements())
                .totalPages(books.getTotalPages())
                .content(Collections.singletonList(allBooks))
                .build();
    }



    @Override
    public CustomPageResponseDto<Object> findByCategory(int pageNo,
                                                        int size,
                                                        String category) {
        Pageable pageable = PageRequest.of(pageNo, size);
        Page<Books> books = bookRepository.findByCategoryIgnoreCase(pageable, category);
        List<BookDto> booksByCategory = books
                .stream()
                .map(bookMapper::entityToDto)
                .toList();

         return CustomPageResponseDto
                      .builder()
                      .pageNumber(books.getNumber())
                      .pageSize(books.getSize())
                      .isLast(books.isLast())
                      .totalElements(books.getTotalElements())
                      .totalPages(books.getTotalPages())
                      .content(Collections.singletonList(booksByCategory))
                 .build();
    }


}
