package com.example.attractions.exception;

/**
 * Исключение, выбрасываемое при отсутствии запрашиваемого ресурса.
 */

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}