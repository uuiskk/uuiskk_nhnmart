package com.nhnacademy.nhnmart.product.parser;

import com.nhnacademy.nhnmart.product.domain.Product;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/*
     ProductParser 인터페이스 입니다.
     - /src/main/resources/product_data.csv 파일을 파싱 합니다.
*/
public interface ProductParser extends Closeable {
    String PRODUCTS_DATA= "product_data.csv";
    List<Product> parse();
    default InputStream getProductsStream(){
        return this.getClass()
                .getClassLoader()
                .getResourceAsStream(PRODUCTS_DATA);
    }
}
