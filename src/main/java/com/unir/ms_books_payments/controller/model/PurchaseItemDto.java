package com.unir.ms_books_payments.controller.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseItemDto {
    private Long id;
    private String bookId;
    private String title;
    private Double price;
    private Integer quantity;
    private Double total;
}
