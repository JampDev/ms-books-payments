package com.unir.ms_books_payments.controller.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseDto {
    private Long id;
    private String buyer;
    private LocalDateTime purchaseDate;
    private List<PurchaseItemDto> items;
}
