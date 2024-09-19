/*
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * + Copyright 2024. NHN Academy Corp. All rights reserved.
 * + * While every precaution has been taken in the preparation of this resource,  assumes no
 * + responsibility for errors or omissions, or for damages resulting from the use of the information
 * + contained herein
 * + No part of this resource may be reproduced, stored in a retrieval system, or transmitted, in any
 * + form or by any means, electronic, mechanical, photocopying, recording, or otherwise, without the
 * + prior written permission.
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */

package com.nhnacademy.nhnmart.product.domain;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * mart에서 판매되는 제품
 */
public class Product {

    //product-id
    private final long id;

    //품목
    private final String item;

    //메이커
    private final String maker;

    //스펙
    private final String specification;

    //단위
    private final String unit;

    //가격
    private final int price;

    //수량
    private int quantity;

    public Product(long id, String item, String maker, String specification, String unit, int price, int quantity) {
        //TODO#6-1-1 product 생성자의 parameter 검증을 통과하지 못한다면 IllegalArgumentException이 발생 됩니다.
        if(id < 0 || item.isEmpty() || item == null || maker.isEmpty() || maker == null
                || specification.isEmpty() || specification == null
                || unit.isEmpty() || unit == null || price < 0 || quantity <0) {
            throw new IllegalArgumentException();
        }

        //TODO#6-1-2 product attribute를 초기화 합니다.
        this.id = id;
        this.item = item;
        this.maker = maker;
        this.specification = specification;
        this.unit = unit;
        this.price = price;
        this.quantity = quantity;
    }

    public long getId() {
        //TODO#6-1-3 product id 반환
        return id;
    }

    public String getItem() {
        //TODO#6-1-4 item 반환
        return item;
    }

    public String getMaker() {
        //TODO#6-1-5 maker 반환
        return maker;
    }

    public String getSpecification() {
        //TODO#6-1-6 specification 반환
        return specification;
    }

    public String getUnit() {
        //TODO#6-1-7 unit 반환
        return unit;
    }

    public int getPrice() {
        //TODO#6-1-8 price 반환
        return price;
    }

    public int getQuantity() {
        //TODO#6-1-9 quantity 반환
        return quantity;
    }

    public void setQuantity(int quantity) {
        //TODO#6-1-10 qunatity 수정, quantity < 0 이면 IllegalArgumentException 발생
        if(quantity < 0) {
            throw new IllegalArgumentException();
        }
        this.quantity = quantity;
    }

    //TODO#6-1-11 equals를 구현 합니다.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;
        return id == product.id && price == product.price && quantity == product.quantity && Objects.equals(item, product.item) && Objects.equals(maker, product.maker) && Objects.equals(specification, product.specification) && Objects.equals(unit, product.unit);
    }

    //TODO#6-1-12 hashCode를 구현합니다.
    @Override
    public int hashCode() {
        return Objects.hash(id, item, maker, specification, unit, price);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", item='" + item + '\'' +
                ", maker='" + maker + '\'' +
                ", specification='" + specification + '\'' +
                ", unit='" + unit + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
