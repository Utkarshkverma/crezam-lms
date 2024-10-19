package com.crezam.lms.service;

import com.crezam.lms.dto.BookDto;
import com.crezam.lms.dto.CustomMessageDto;
import com.crezam.lms.dto.CustomPageResponseDto;


public interface IBookService {
     BookDto addBook(BookDto bookDto);
     BookDto updateBook(String isbn,BookDto bookDto);
     BookDto getBook(String isbn);
     CustomMessageDto deleteBook(String isbn);
     CustomPageResponseDto<Object> getAllBooks(int pageNo, int pageSize);
     CustomPageResponseDto<Object> findByCategory(int pageNo, int size, String category);
}
