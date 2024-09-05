package com.nhnacademy.nhnmart.product.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(long id) {
        super(String.format("product-id:%d",id));
    }
}
