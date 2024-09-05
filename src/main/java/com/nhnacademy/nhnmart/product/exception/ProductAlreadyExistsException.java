package com.nhnacademy.nhnmart.product.exception;

public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException(long productId) {
        super(String.format("product already exists : {}",productId));
    }
}
