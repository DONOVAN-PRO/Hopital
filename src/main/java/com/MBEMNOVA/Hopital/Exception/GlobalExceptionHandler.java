package com.MBEMNOVA.Hopital.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CapaciteAtteinteException.class)
    public ResponseEntity<Map<String, String>> handleCapacite(CapaciteAtteinteException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erreur", ex.getMessage()));
    }

    @ExceptionHandler(ConflitHoraireException.class)
    public ResponseEntity<Map<String, String>> handleConflit(ConflitHoraireException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erreur", ex.getMessage()));
    }

    @ExceptionHandler(RessourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(RessourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erreur", ex.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleIllegalState(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erreur", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> erreurs = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                erreurs.put(err.getField(), err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(erreurs);
    }
}