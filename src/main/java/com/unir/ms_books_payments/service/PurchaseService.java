package com.unir.ms_books_payments.service;

import com.unir.ms_books_payments.controller.model.PurchaseDto;
import com.unir.ms_books_payments.data.model.Purchase;

import java.util.List;

public interface PurchaseService {
    List<Purchase> getPurchases();
    Purchase getPurchase(String purchaseId);
    Boolean removePurchase(String purchaseId);
    Purchase createPurchase(PurchaseDto purchaseDto);
    Purchase updatePurchase(Long purchaseId, PurchaseDto updateRequest);
}
