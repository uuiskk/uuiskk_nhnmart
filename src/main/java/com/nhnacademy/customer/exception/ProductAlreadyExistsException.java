package com.nhnacademy.customer.exception;

public class ProductAlreadyExistsException extends Exception {
    public ProductAlreadyExistsException(long productId) {
        super(String.format("product already exists : {}",productId));
    }
}
