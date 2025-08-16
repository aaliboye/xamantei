package com.example.xamantei.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 404 : Ressource non trouvée
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEntityNotFound(EntityNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // 400 : Erreurs de validation (DTO avec @Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Erreur de validation");

        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            fieldErrors.put(error.getField(), error.getDefaultMessage())
        );

        response.put("details", fieldErrors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 400 : Argument non valide dans les paramètres (ex: @RequestParam, @PathVariable)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = "Paramètre invalide : " + ex.getName();
        return buildErrorResponse(message, HttpStatus.BAD_REQUEST);
    }

    // 405 : Méthode HTTP non autorisée
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, String>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        String message = "Méthode HTTP non supportée : " + ex.getMethod();
        return buildErrorResponse(message, HttpStatus.METHOD_NOT_ALLOWED);
    }

    // 415 : Type de contenu non supporté
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Map<String, String>> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        String message = "Type de contenu non supporté : " + ex.getContentType();
        return buildErrorResponse(message, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    // 500 : Exception générale
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAll(Exception ex) {
        return buildErrorResponse("Erreur serveur : " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Méthode utilitaire pour formater les erreurs
    private ResponseEntity<Map<String, String>> buildErrorResponse(String message, HttpStatus status) {
        Map<String, String> response = new HashMap<>();
        response.put("error", message);
        response.put("status", String.valueOf(status.value()));
        return new ResponseEntity<>(response, status);
    }

        @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String message = "Un champ unique est en conflit. Peut-être que le nom d'utilisateur ou l'email est déjà utilisé.";

        // Optionnel : Tu peux parser le message si tu veux le rendre plus précis
        if (ex.getMessage() != null && ex.getMessage().contains("UK3k4cplvh82srueuttfkwnylq0")) {
            message = "Le nom d'utilisateur est déjà pris.";
        }

        Map<String, String> response = new HashMap<>();
        response.put("error", message);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}
