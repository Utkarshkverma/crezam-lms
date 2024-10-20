package com.crezam.lms.repository;

import com.crezam.lms.entity.Books;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Books, String> {

    Optional<Books> findByIsbn(String isbn);
    Page<Books> findByCategoryIgnoreCase(Pageable pageable, String category);

}
