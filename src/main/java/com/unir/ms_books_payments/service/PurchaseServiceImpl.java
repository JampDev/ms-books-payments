package com.unir.ms_books_payments.service;

import com.unir.ms_books_payments.client.BookCatalogueClient;
import com.unir.ms_books_payments.controller.model.BookResponse;
import com.unir.ms_books_payments.controller.model.PurchaseDto;
import com.unir.ms_books_payments.controller.model.PurchaseItemDto;
import com.unir.ms_books_payments.data.PurchaseRepository;
import com.unir.ms_books_payments.data.model.Purchase;
import com.unir.ms_books_payments.data.model.PurchaseItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseRepository repository;

    @Autowired
    private BookCatalogueClient bookClient;

    @Override
    public List<Purchase> getPurchases() {
        List<Purchase> purchases = repository.getPurchases();
        return purchases.isEmpty() ? null : purchases;
    }

    @Override
    public Purchase getPurchase(String purchaseId) {
        return repository.getById(Long.valueOf(purchaseId));
    }

    @Override
    public Boolean removePurchase(String purchaseId) {
        Purchase purchase = repository.getById(Long.valueOf(purchaseId));
        if (purchase != null) {
            repository.delete(purchase);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    @Override
    public Purchase createPurchase(PurchaseDto request) {
        Purchase purchase = new Purchase();
        purchase.setBuyer(request.getBuyer());
        purchase.setPurchaseDate(LocalDateTime.now());

        List<PurchaseItem> validItems = new ArrayList<>();

        for (PurchaseItemDto itemDto : request.getItems()) {
            BookResponse book = bookClient.getBookById(itemDto.getBookId());

            if (book == null || !book.getVisibility() || book.getStock() < itemDto.getQuantity()) {
                throw new RuntimeException("Libro no válido o sin stock.");
            }

            int currentStock = book.getStock();
            int newStock = currentStock - itemDto.getQuantity();

            if (newStock < 0) {
                throw new IllegalArgumentException("Stock insuficiente para el libro: " + itemDto.getBookId());
            }

            bookClient.updateBookStock(itemDto.getBookId(), newStock);

            PurchaseItem item = new PurchaseItem();
            item.setBookId(book.getId());
            item.setTitle(book.getTitle());
            item.setPrice(book.getPrice());
            item.setIsbn(book.getIsbn());
            item.setQuantity(itemDto.getQuantity());
            item.setTotal(book.getPrice() * itemDto.getQuantity());

            item.setPurchase(purchase);
            validItems.add(item);
        }

        purchase.setItems(validItems);

        return repository.save(purchase);
    }

    @Override
    public Purchase updatePurchase(Long purchaseId, PurchaseDto updateRequest) {
        Purchase purchase = repository.getById(purchaseId);

        // 1. Revertir stock anterior
        for (PurchaseItem oldItem : purchase.getItems()) {
            BookResponse book = bookClient.getBookById(String.valueOf(oldItem.getBookId()));

            if (book != null) {
                int revertedStock = book.getStock() + oldItem.getQuantity(); // restaurar el stock original
                bookClient.updateBookStock(String.valueOf(oldItem.getBookId()), revertedStock);
            }
        }

        List<PurchaseItem> validItems = new ArrayList<>();

        // 2. Validar nuevos libros y stock
        for (PurchaseItemDto itemDto : updateRequest.getItems()) {
            BookResponse book = bookClient.getBookById(itemDto.getBookId());

            if (book == null || book.getStock() < itemDto.getQuantity() || !book.getVisibility()) {
                throw new RuntimeException("Libro inválido o no disponible: " + itemDto.getBookId());
            }

            int newStock = book.getStock() - itemDto.getQuantity(); // stock final
            bookClient.updateBookStock(String.valueOf(book.getId()), newStock); // se actualiza el valor absoluto

            validItems.add(PurchaseItem.builder()
                    .bookId(book.getId())
                    .title(book.getTitle())
                    .price(book.getPrice())
                    .quantity(itemDto.getQuantity())
                    .total(book.getPrice() * itemDto.getQuantity())
                    .purchase(purchase)
                    .build());
        }

        // 4. Actualiza el comprador e ítems
        purchase.setBuyer(updateRequest.getBuyer());
        purchase.getItems().clear();
        purchase.getItems().addAll(validItems);

        // 5. Recalcula el total
        purchase.recalculateTotal();

        return repository.save(purchase);
    }


}
