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

package com.nhnacademy.nhnmart.product.parser.impl;

import com.nhnacademy.nhnmart.product.domain.Product;
import com.nhnacademy.nhnmart.product.exception.CsvParsingException;
import com.nhnacademy.nhnmart.product.parser.ProductParser;
import com.nhnacademy.nhnmart.product.util.ProductIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class CsvProductParser implements ProductParser {

    //제품의 가본 수량  = 100개
    private final int DEFAULT_QUANTITY=100;
    private final InputStream inputStream;

    public CsvProductParser() {
        //TODO#6-2-1 기본생성자 구현 , getProductsStream()을 이용해서 inputStream을 초기화 합니다.
        inputStream = getProductsStream();
    }

    public CsvProductParser(InputStream inputStream){
        //TODO#6-2-2 inputStream prameter로 전달 됩니다. 초기화 합니다.
        this.inputStream = inputStream;
    }

    @Override
    public List<Product> parse() {
        /* TODO#6-2-3 parse() method를 구현하세요
            [CSV Parser]
            - https://github.com/nhnacademy-bootcamp/java-dev-settings/blob/main/docs/06.maven/02.Maven/06.pom.xml.adoc 참고 합니다.
            - ProductParser interface의 getProductsStream()를 이용해서 구현 합니다.
         */
        List<Product> products = new ArrayList<>();

        try {

            CSVParser parser = CSVParser.parse(inputStream, StandardCharsets.UTF_8, CSVFormat.EXCEL);
            List<CSVRecord> csvRecords = parser.getRecords();
            for(int i=1; i<csvRecords.size(); i++){

                CSVRecord csvRecord = csvRecords.get(i);
                String item = csvRecord.get(0);
                String maker = csvRecord.get(1);
                String specification = csvRecord.get(2);
                String unit = csvRecord.get(3);
                int price = 0;
                String tempPrice = csvRecord.get(4);
                tempPrice = tempPrice.replaceAll(",","");
                if(!StringUtils.isEmpty(tempPrice) && StringUtils.isNumeric(tempPrice) ){
                    price = Integer.parseInt(tempPrice);
                }

                long id = ProductIdGenerator.getNewId();

                Product product = new Product(
                        id,
                        item,
                        maker,
                        specification,
                        unit,
                        price,
                        DEFAULT_QUANTITY
                );
                products.add(product);
            }
        }catch (Exception e){
            //CsvParsingException 예외가 발생 하도록 구현 합니다.
            log.error("{}{}",e.getMessage(),e);
            throw new CsvParsingException();
        }


        return products;
    }

    @Override
    public void close() throws IOException {
        //TODO#6-2-5 inputStream 객체가 존재하면 close() method를 호출해서 자원을 해지 합니다.
        if(inputStream != null){
            inputStream.close();
        }
    }
}
