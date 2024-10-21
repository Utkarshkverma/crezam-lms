package com.crezam.lms.controller;

import com.crezam.lms.constants.AppConstant;
import com.crezam.lms.dto.BookDto;
import com.crezam.lms.dto.CustomMessageDto;
import com.crezam.lms.dto.CustomPageResponseDto;
import com.crezam.lms.dto.ErrorResponseDto;
import com.crezam.lms.service.IBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/books",produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Tag(
        name = "REST API for managing books of the library",
        description = "REST API to add books, find books, update and delete books"
)
public class BooksController {

    private final IBookService bookService;

    @Operation(
            summary = "Find all books",
            description = "This endpoint will allow you to find all books of the library"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "302",
                    content = @Content(
                            schema = @Schema(implementation = CustomPageResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping
    public ResponseEntity<CustomPageResponseDto<Object>> findAll(
            @Parameter(description = "Page number",required = false,example = "0")
            @RequestParam(name = "page",defaultValue = AppConstant.PAGE_NO,required = false) int page,
            @Parameter(description = "No of entities per page",required = false,example = "0")
            @RequestParam(name = "size",defaultValue = AppConstant.PAGE_SIZE,required = false) int size
    ){
        return  ResponseEntity
                .status(HttpStatus.FOUND)
                .body(bookService.getAllBooks(page,size));
    }


    @Operation(
            summary = "Add a book (ADMIN only)",
            description = "This endpoint will allow you to add a book in database"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    content = @Content(
                            schema = @Schema(implementation = BookDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation errors occurred",
                    content = @Content(
                            schema = @Schema(implementation = Map.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping
    public ResponseEntity<BookDto> addBook(@Valid @RequestBody BookDto bookDto){
        BookDto bookDto1 = bookService.addBook(bookDto);
        return new ResponseEntity<>(bookDto1,HttpStatus.CREATED);
    }


    @Operation(
            summary = "Find the book by isbn number",
            description = "This endpoint will allow you to find the book with the help of isbn number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "302",
                    description = "Book found successfully",
                    content = @Content(
                            schema = @Schema(implementation = BookDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/{isbn}")
    public ResponseEntity<BookDto> getBook(
            @Parameter(description = "The ISBN of the book to retrieve", required = true, example = "978-3-16-148410-0")
            @PathVariable String isbn){
        BookDto bookDto = bookService.getBook(isbn);
        return new ResponseEntity<>(bookDto,HttpStatus.FOUND);
    }


    @Operation(
            summary = "Find all books of a category",
            description = "This endpoint will allow you to find all books of a specific category"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "302",
                    content = @Content(
                            schema = @Schema(implementation = CustomPageResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/search")
    public ResponseEntity<CustomPageResponseDto<Object>> searchBook(
            @Parameter(description = "category name",required = true,example = "computer")
            @RequestParam String keyword,
            @Parameter(description = "Page number",required = false,example = "0")
            @RequestParam(name = "page",defaultValue = AppConstant.PAGE_NO,required = false) int page,
            @Parameter(description = "No of entities per page",required = false,example = "10")
            @RequestParam(name = "size",defaultValue = AppConstant.PAGE_SIZE,required = false) int size
    )
    {
        CustomPageResponseDto<Object> byCategory = bookService
                .findByCategory(page, size, keyword);
        return new ResponseEntity<>(byCategory,HttpStatus.FOUND);
    }


    @Operation(
            summary = "Remove the book (Admin only)",
            description = "This endpoint will allow you to find a book by category and delete it"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Book was found and was removed successfully",
                    content = @Content(
                            schema = @Schema(implementation = CustomMessageDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found with the given ISBN number",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )

    }
    )
    @DeleteMapping("/{isbn}")
    public ResponseEntity<CustomMessageDto> deleteBook(
            @Parameter(description = "The ISBN of the book to delete", required = true, example = "978-3-16-148410-0")
            @PathVariable String isbn){
        CustomMessageDto customMessageDto = bookService.deleteBook(isbn);
        return new ResponseEntity<>(customMessageDto,HttpStatus.OK);
    }
    @Operation(
            summary = "Update the book (ADMIN only)",
            description = "This endpoint will allow you to find a book by category and update it"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Book was found and was updated successfully",
                    content = @Content(
                            schema = @Schema(implementation = BookDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found with the given ISBN number",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation errors occurred",
                    content = @Content(
                            schema = @Schema(implementation = Map.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )

    }
    )
    @PutMapping("/{isbn}")
    public ResponseEntity<BookDto> updateBook(
            @Parameter(description = "The ISBN of the book to update (ADMIN only)", required = true, example = "978-3-16-148410-0")
            @PathVariable String isbn,
            @Valid @RequestBody BookDto bookDto){
        BookDto bookDto1 = bookService.updateBook(isbn, bookDto);
        return new ResponseEntity<>(bookDto1,HttpStatus.OK);
    }
}
