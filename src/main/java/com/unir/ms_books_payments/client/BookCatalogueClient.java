package com.unir.ms_books_payments.client;

import com.unir.ms_books_payments.controller.model.BookResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookCatalogueClient {

    @Autowired
    private RestTemplate restTemplate;

    public BookResponse getBookById(String id) {
        try {
            String url = UriComponentsBuilder
                    .fromHttpUrl("http://ms-books-catalogue/books/" + id)
                    .build()
                    .toUriString();

            ResponseEntity<EResponse<BookResponse>> response = restTemplate
                    .exchange(url,
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<EResponse<BookResponse>>() {
                            });

            return response.getBody() != null ? response.getBody().getBody() : null;
        } catch (Exception e) {
            log.warn("No se pudo consultar el libro con ID {}: {}", id, e.getMessage());
            return null;
        }
    }

    public void updateBookStock(String bookId, Integer newStock) {
        String url = "http://ms-books-catalogue/books/" + bookId;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/merge-patch+json"));

        String patchBody = "{ \"stock\": " + newStock + " }";

        HttpEntity<String> entity = new HttpEntity<>(patchBody, headers);

        try {
            restTemplate.exchange(url, HttpMethod.PATCH, entity, BookResponse.class);
        } catch (HttpClientErrorException e) {
            log.error("Error actualizando stock del libro {}: {}", bookId, e.getMessage());
            throw new RuntimeException("Fallo al actualizar el stock del libro " + bookId);
        }
    }


}
