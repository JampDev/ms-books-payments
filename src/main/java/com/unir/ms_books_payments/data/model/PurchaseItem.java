package com.unir.ms_books_payments.data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.unir.ms_books_payments.data.utils.Consts;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "purchase_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = Consts.BOOK_ID)
    private Long bookId;

    @Column(name = Consts.ISBN)
    private String isbn;

    @Column(name = Consts.TITLE)
    private String title;

    @Column(name = Consts.PRICE)
    private Double price;

    @Column(name = Consts.QUANTITY)
    private Integer quantity;

    @Column(name = Consts.TOTAL)
    private Double total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id")
    @JsonBackReference
    private Purchase purchase;
}
