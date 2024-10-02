package com.napshop.exception;

public class OutOfStockException extends BaseException {
    private static final String ERROR_CODE = "OUT_OF_STOCK";

    public OutOfStockException(String message) {
        super(message, ERROR_CODE);
    }
}
