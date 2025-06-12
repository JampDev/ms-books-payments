package com.unir.ms_books_payments.data;

import com.unir.ms_books_payments.data.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PurchaseJpaRepository extends JpaRepository<Purchase, Long>, JpaSpecificationExecutor<Purchase> {
}
