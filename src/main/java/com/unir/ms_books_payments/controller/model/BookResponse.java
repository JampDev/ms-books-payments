package com.unir.ms_books_payments.controller.model;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponse {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String description;
    private Double price;
    private Integer stock;
    private String rating;
    private String image;
    private LocalDate publicationDate;
    private String category;
    private Boolean visibility;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
