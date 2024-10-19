package com.crezam.lms.controller;

import com.crezam.lms.dto.BookDto;
import com.crezam.lms.dto.CustomMessageDto;
import com.crezam.lms.dto.CustomPageResponseDto;
import com.crezam.lms.service.IBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/books",produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class BooksController {

    private final IBookService bookService;

    @GetMapping()
    public ResponseEntity<CustomPageResponseDto<Object>> findAll(
            @RequestParam(name = "page",defaultValue = "0",required = false) int page,
            @RequestParam(name = "size",defaultValue = "10",required = false) int size
    ){
        return  ResponseEntity
                .status(HttpStatus.FOUND)
                .body(bookService.getAllBooks(page,size));
    }

    @PostMapping()
    public ResponseEntity<BookDto> addBook(@Valid @RequestBody BookDto bookDto){
        BookDto bookDto1 = bookService.addBook(bookDto);
        return new ResponseEntity<>(bookDto1,HttpStatus.CREATED);
    }

    @GetMapping("/{ibsn}")
    public ResponseEntity<BookDto> getBook(@PathVariable String ibsn){
        BookDto bookDto = bookService.getBook(ibsn);
        return new ResponseEntity<>(bookDto,HttpStatus.FOUND);
    }

    @GetMapping("/search")
    public ResponseEntity<CustomPageResponseDto<Object>> searchBook(
            @RequestParam String keyword,
            @RequestParam(name = "page",defaultValue = "0",required = false) int page,
            @RequestParam(name = "size",defaultValue = "10",required = false) int size
    )
    {
        CustomPageResponseDto<Object> byCategory = bookService
                .findByCategory(page, size, keyword);
        return new ResponseEntity<>(byCategory,HttpStatus.FOUND);
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<CustomMessageDto> deleteBook(@PathVariable String isbn){
        CustomMessageDto customMessageDto = bookService.deleteBook(isbn);
        return new ResponseEntity<>(customMessageDto,HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<BookDto> updateBook(
            @PathVariable String isbn,
            @Valid @RequestBody BookDto bookDto){
        BookDto bookDto1 = bookService.updateBook(isbn, bookDto);
        return new ResponseEntity<>(bookDto1,HttpStatus.OK);
    }
}
