package com.unir.ms_books_payments.controller;

import com.unir.ms_books_payments.controller.model.PurchaseDto;
import com.unir.ms_books_payments.data.model.Purchase;
import com.unir.ms_books_payments.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Purchase Controller", description = "Microservicio encargado de exponer operaciones CRUD sobre las compras realizadas en una base de datos en memoria.")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping("/purchases")
    @Operation(
            operationId = "Insertar una compra",
            description = "Operacion de escritura",
            summary = "Se crea una compra a partir de sus datos.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la compra a crear.",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PurchaseDto.class))))
    @ApiResponse(
            responseCode = "201",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Purchase.class)))
    @ApiResponse(
            responseCode = "400",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "Datos incorrectos introducidos.")
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "No se ha encontrado el producto con el identificador indicado.")
    public ResponseEntity<Purchase> createPurchase(@RequestBody PurchaseDto purchaseDto) {
        Purchase saved = purchaseService.createPurchase(purchaseDto);
        if (saved != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/purchases")
    @Operation(
            operationId = "Obtener compras",
            description = "Operacion de lectura",
            summary = "Se devuelve una lista de todas las compras almacenadas en la base de datos."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Purchase.class)))
    public ResponseEntity<List<Purchase>> getPurchases(@RequestHeader Map<String, String> headers) {
        log.info("headers: {}", headers);
        List<Purchase> purchases = purchaseService.getPurchases();
        if (purchases != null) {
            return ResponseEntity.ok(purchases);
        } else {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/purchases/{id}")
    @Operation(
            operationId = "Obtener una compra",
            description = "Operacion de lectura",
            summary = "Se devuelve una compra a partir de su identificador.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Purchase.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "No se ha encontrado la compra con el identificador indicado.")
    public ResponseEntity<Purchase> getPurchaseById(@PathVariable String id) {
        log.info("Request received for product {}", id);
        Purchase purchase = purchaseService.getPurchase(id);
        if (purchase != null) {
            return ResponseEntity.ok(purchase);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/purchases/{id}")
    @Operation(
            operationId = "Eliminar una compra",
            description = "Operacion de escritura",
            summary = "Se elimina una compra a partir de su identificador.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "No se ha encontrado la compra con el identificador indicado.")
    public ResponseEntity<Void> deletePurchase(@PathVariable String id) {
        Boolean removed = purchaseService.removePurchase(id);
        if (Boolean.TRUE.equals(removed)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/purchases/{id}")
    @Operation(
            operationId = "Modificar totalmente una compra",
            description = "Operacion de escritura",
            summary = "Se modifica totalmente una compra.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la compra a actualizar.",
                    required = true,
                    content = @Content(mediaType = "application/merge-patch+json", schema = @Schema(implementation = PurchaseDto.class))))
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PurchaseDto.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "Compra no encontrado.")
    public ResponseEntity<Purchase> updatePurchase(@PathVariable Long id, @RequestBody PurchaseDto body) {
        Purchase update = purchaseService.updatePurchase(id, body);
        if (update != null) {
            return ResponseEntity.ok(update);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
