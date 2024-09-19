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

package com.nhnacademy.nhnmart.product.service.impl;

import com.nhnacademy.nhnmart.product.domain.Product;
import com.nhnacademy.nhnmart.product.exception.OutOfStockException;
import com.nhnacademy.nhnmart.product.exception.ProductAlreadyExistsException;
import com.nhnacademy.nhnmart.product.exception.ProductNotFoundException;
import com.nhnacademy.nhnmart.product.parser.ProductParser;
import com.nhnacademy.nhnmart.product.repository.ProductRepository;
import com.nhnacademy.nhnmart.product.service.ProductService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/*
    - productServiceImpl은 ProductService의 구현체
    - productRepository에 직접 호출해서 수량을 변경하기 보다는 Service -> Repository에 접근 합니다.
    - service에서는 repository로 부터 데이터의 CRUD 작업을 수행 합니다. 수행하는 과정에서 발생할 수 있는 로직 및 예외를 처리하는 역할을 합니다.

*/
public class ProductServiceImpl implements ProductService {

    //product 저장소
    private final ProductRepository productRepository;

    //product 파서
    private final ProductParser productParser;

    public ProductServiceImpl(ProductRepository productRepository, ProductParser productParser) {
        //TODO#6-5-1 productRepository or productParser null이면 IllegalArgumentException 발생 됩니다.
        if(Objects.isNull(productRepository) || Objects.isNull(productParser)) {
            throw new IllegalArgumentException();
        }

        //TODO#6-5-2 productRepository, productParser 초기화 합니다.
        this.productRepository = productRepository;
        this.productParser = productParser;

        //TODO#6-5-3 init() method를 호출하여 초기화 합니다.
        init();
    }

    private void init(){
        //TODO#6-5-4 productParser.parse()를 호출하고 반환된 List<Product> products를 productRepository를 통해서 memory 저장소에 저장 합니다.
        List<Product> products = productParser.parse();
        for(Product product : products){
            //save
            productRepository.save(product);
        }
    }

    @Override
    public Product getProduct(long id) {
        /* TODO#6-5-5 id에 해당되는 product를 반환 합니다.
            - product가 존재하지 않다면 ProductNotFoundException이 발생 합니다.
        */
        Optional<Product> optionalProduct = productRepository.findById(id);

        if(optionalProduct.isEmpty()) {
            throw new ProductNotFoundException(id);
        }
        return optionalProduct.get();
    }

    @Override
    public void saveProduct(Product product) {
        /* TODO#6-5-6 product를 저장 합니다.
           - product-id에 해당되는 제품이 이미 존재 한다면  AreadyExistProductException이 발생 합니다.
        */

        if(productRepository.existById(product.getId())){
            throw new ProductAlreadyExistsException(product.getId());
        }

        productRepository.save(product);
    }

    @Override
    public void deleteProduct(long id) {
        /* TODO#6-5-7 id에 해당되는 product를 삭제 합니다.
            - id에 해당되는 제품이 존재하지 않다면 ProductNotFoundException 발생 합니다.
        */
        if(productRepository.existById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);

    }

    @Override
    public long getTotalCount() {
        //TODO#6-5-8 전체 product의 수를 반환 합니다.
        return productRepository.count();
    }

    @Override
    public void updateQuantity(long id, int quantity) {
        /*TODO#6-5-9 id에 해당되는 제품의 수량을 수정 합니다.
            - id에 해당되는 제품이 존재하지 않다면 ProductNotFoundException 발생 합니다.
        */
        if(!productRepository.existById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.updateQuantityById(id, quantity);

    }

    @Override
    public void pickProduct(long id, int quantity) {
        /*TODO#6-5-10 제품을 장바구니에 담습니다.
            - 제품의 수량이 parameter로 전달된 quantity 작다면 OutOfStockException 발생 합니다.
            - updateQuantity method를 호출해서 quantity만큼 차감한 수량으로 변경 합니다.
            - 조회 : getProduct(id)
            - 수량 변경 : updateQuantity(id, product.getQuantity()-quantity)
         */
        Product product = getProduct(id);
        if(product.getQuantity() < quantity){
            throw new OutOfStockException(id);
        }

        updateQuantity(id, product.getQuantity() - quantity);
    }

    @Override
    public int returnProduct(long id, int quantity) {
        /*TODO#6-5-11 장바구니에 담았던 quantity(수량) 만큼  제품 저장소에 반납 합니다.
            - updateQuantity method를 호출해서 quantity만큼 증가한 수량으로 변경 합니다.
            - 합산된 수량을 반환 합니다.
            - 조회 : getProduct(id)
            - 수량 변경 : updateQuantity(id, product.getQuantity()-quantity)
         */
        Product product = getProduct(id);
        int updateQuantity = product.getQuantity() + quantity;
        updateQuantity(id, updateQuantity);

        return updateQuantity;
    }
}