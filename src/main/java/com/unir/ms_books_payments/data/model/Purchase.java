package com.unir.ms_books_payments.data.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.unir.ms_books_payments.controller.model.PurchaseDto;
import com.unir.ms_books_payments.data.utils.Consts;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "purchase")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = Consts.BUYER)
    private String buyer;

    @Column(name = Consts.PURCHASE_DATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime purchaseDate;

    @Column(name = Consts.TOTAL)
    private Double total;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PurchaseItem> items;

    @PrePersist
    public void prePersist() {
        purchaseDate = LocalDateTime.now();
    }

    public void recalculateTotal() {
        if (items != null) {
            this.total = items.stream()
                    .mapToDouble(PurchaseItem::getTotal)
                    .sum();
        } else {
            this.total = 0.0;
        }
    }
}
