package com.nhnacademy.thread.customer;

import com.nhnacademy.customer.cart.Cart;
import com.nhnacademy.customer.domain.Customer;

import java.util.Objects;

public class CartLocal {
    private static final ThreadLocal<Customer> customerLocal = new ThreadLocal<>();
    private static final ThreadLocal<Cart> cartLocal = ThreadLocal.withInitial(()->new Cart());
    public static void initialize(Customer customer){
        customerLocal.set(customer);
    }

    public static void reset(){
        customerLocal.remove();
        Cart cart = getCart();
        if(Objects.nonNull(cart)) {
            cart.clear();
        }
    }

    public static Customer getCustomer(){
        return customerLocal.get();
    }

    public static Cart getCart(){
        return cartLocal.get();
    }
}
