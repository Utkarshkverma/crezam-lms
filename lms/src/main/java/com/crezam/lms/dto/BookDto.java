package com.crezam.lms.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.ISBN;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {
    private String id;
    @ISBN(message = "ISBN is not well formatted")
    @NotEmpty(message = "ISBN is required")
    private String isbn;
    @NotEmpty(message = "Title is required")
    private String title;
    @NotEmpty(message = "Author name is required")
    private String author;
    @NotEmpty(message = "Category name is required")
    private String category;
    @NotNull(message = "No. of available copies are required")
    @Min(value = 0, message = "No of available copies cannot be less then 0")
    private int availableCopies;
    @NotNull(message = "Published year is required")
    @Min(value = 1900, message = "Published year cannot be less than 1900")
    @Max(value = 2024, message = "Published year cannot be greater than 2024")
    private int publishedYear;
}
