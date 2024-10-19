package com.crezam.lms.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.ISBN;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        name = "Book",
        description = "Schema representing a book with its details."
)
public class BookDto {

    @Schema(
            description = "Unique identifier for the book.",
            example = "f47ac10b-58cc-4372-a567-0e02b2c3d479"
    )
    private String id;

    @ISBN(message = "ISBN is not well formatted")
    @NotEmpty(message = "ISBN is required")
    @Schema(
            description = "International Standard Book Number (ISBN) for the book.",
            example = "978-3-16-148410-0"
    )
    private String isbn;

    @NotEmpty(message = "Title is required")
    @Schema(
            description = "Title of the book.",
            example = "Effective Java"
    )
    private String title;

    @NotEmpty(message = "Author name is required")
    @Schema(
            description = "Author of the book.",
            example = "Joshua Bloch"
    )
    private String author;

    @NotEmpty(message = "Category name is required")
    @Schema(
            description = "Category under which the book is classified.",
            example = "Programming"
    )
    private String category;

    @NotNull(message = "No. of available copies are required")
    @Min(value = 0, message = "No of available copies cannot be less than 0")
    @Schema(
            description = "Number of available copies of the book.",
            example = "5"
    )
    private int availableCopies;

    @NotNull(message = "Published year is required")
    @Min(value = 1900, message = "Published year cannot be less than 1900")
    @Max(value = 2024, message = "Published year cannot be greater than 2024")
    @Schema(
            description = "Year the book was published.",
            example = "2021"
    )
    private int publishedYear;
}
