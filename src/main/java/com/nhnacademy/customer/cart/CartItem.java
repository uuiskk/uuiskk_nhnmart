package com.nhnacademy.customer.cart;

import java.io.Serializable;
import java.util.Objects;

public class CartItem implements Serializable {

    //제품 아이디
    private final long productId;
    //제품 수량
    private final int quantity;

    public CartItem(long productId, int quantity) {
        //TODO#2-6 productId < 0 or quantity<0 IllegalArgumentException이 발생 합니다.


        //TODO#2-7 productId, quantity 를 초기화 합니다.
        this.productId = 0l;
        this.quantity = 0;
    }

    public long getProductId() {
        //TODO#2-8 productId 반환 합니다.
        return 0l;
    }

    public int getQuantity() {
        //TODO#2-9 quantity를 반환 합니다.
        return 0;
    }

    //TODO#2-10  (projectId, quantity)를 기준으로 객체 비교를 하기 위해서 equals() 구현

    @Override
    public boolean equals(Object o) {
        return false;
    }

    //TODO#2-11 (projectId, quantity)를 기준으로 hashCode() 구현
    @Override
    public int hashCode() {
        return 0;
    }
}
