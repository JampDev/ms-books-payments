package com.unir.ms_books_payments.data;

import com.unir.ms_books_payments.data.model.Purchase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PurchaseRepository {

    private final PurchaseJpaRepository repository;

    public List<Purchase> getPurchases() {
        return repository.findAll();
    }

    public Purchase getById(Long id){
        return repository.findById(id).orElse(null);
    }

    public Purchase save(Purchase purchase) {
        return repository.save(purchase);
    }

    public void delete(Purchase purchase) {
        repository.delete(purchase);
    }
}
