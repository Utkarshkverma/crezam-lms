package com.crezam.lms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.NaturalId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Books {

    @Id
    private String id;
    @Column(unique=true,nullable=false)
    private String isbn;
    @Column(unique=true,nullable=false)
    private String title;
    @Column(nullable=false)
    private String author;
    @Column(nullable=false)
    private String category;
    @Column(nullable=false)
    private int availableCopies;
    @Column(nullable=false)
    private int publishedYear;

    /*
    * I didn't keep ISBN as id because when a book is revised, updated, or
    * released in a different format, ISBN no. will be changed
    */

}
