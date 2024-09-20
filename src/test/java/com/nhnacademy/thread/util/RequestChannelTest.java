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

package com.nhnacademy.thread.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.function.Try;
import org.junit.platform.commons.util.ReflectionUtils;

import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class RequestChannelTest {

    @Test
    @DisplayName("default queueSize : 10")
    void constructorTest1() throws Exception {
        RequestChannel requestChannel = new RequestChannel();

        Try<Object> readFieldValue = ReflectionUtils.tryToReadFieldValue(RequestChannel.class, "queueSize", requestChannel);
        long queueSize = (long) readFieldValue.get();

        //TODO#8-2-8 기본 생성자를 이용해서 생성된 requestChannel의 queueSize가 10인지 검증 합니다.
        Assertions.assertEquals(10, queueSize);
    }

    @Test
    @DisplayName("queueSize=-5")
    void constructorTest2(){
        //TODO#8-2-9 RequestChannel 객체 생성시 queueSize -5 이면 IllegalArgumentException 발생하는지 검증 합니다.
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            new RequestChannel(-5);
        });
    }

    @Test
    @DisplayName("addRequest : 5 times")
    void addRequest_5_times() throws Exception {
        RequestChannel requestChannel = new RequestChannel();
        //TODO#8-2-10 requestChannel에 5개의 아무것도 실행하지 않는 작업을(Executable) 등록 합니다. Executable : ()->{} 사용합니다.
        for(int i=1; i<=5; i ++) {
            requestChannel.addRequest(() -> {});
        }

        Try<Object> readFieldValue = ReflectionUtils.tryToReadFieldValue(RequestChannel.class, "requestQueue", requestChannel);
        Queue queue = (Queue) readFieldValue.get();

        Assertions.assertEquals(5,queue.size());
    }
    @Test
    @DisplayName("addRequest : 11 times, waiting")
    void addRequest_11tiems() throws Exception {

        RequestChannel requestChannel = new RequestChannel(10);


        Thread thread = new Thread(()->{
            //TODO#8-2-11 requestChannel에 11개의 빈 작업을 등록하는 thread를 구현 하세요. 빈 작업: ()->{}
            for(int i=1; i<=11; i++){
                requestChannel.addRequest(()->{});
            }
        });

        thread.start();

        Try<Object> readFieldValue = ReflectionUtils.tryToReadFieldValue(RequestChannel.class, "requestQueue", requestChannel);
        Queue queue = (Queue) readFieldValue.get();

        //requestChannel의 queueSize =10, 11번재 executable 객체를 추가할 수 없어 대기 함니다.
        log.debug("queueSize:{}",queue.size());
        Assertions.assertEquals(10,queue.size());

        thread.interrupt();
    }

    @Test
    @DisplayName("getRequest, from queue(size:5)")
    void getRequest() throws Exception {
        RequestChannel requestChannel = new RequestChannel(10);
        for(int i=1; i<=5; i++){
            requestChannel.addRequest(()->{});
        }
        //TODO#8-2-12 requestChannel 작업을 할당 받아 실행 하세요.
        Executable executable = requestChannel.getRequest();
        executable.execute();

        Try<Object> readFieldValue = ReflectionUtils.tryToReadFieldValue(RequestChannel.class, "requestQueue", requestChannel);
        Queue queue = (Queue) readFieldValue.get();

        log.debug("queue-size:{}",queue.size());

        Assertions.assertEquals(4,queue.size());
    }

    @Test
    @DisplayName("getRequest from empty queue")
    void getRequest_from_empty_queue() throws InterruptedException {
        RequestChannel requestChannel = new RequestChannel(10);

        Thread thread = new Thread(()->{
            requestChannel.getRequest();
        });
        thread.setName("my-thread");
        thread.start();

        Thread.sleep(2000);

        log.debug("{} : {}", thread.getName(),thread.getState());

        //TODO#8-2-13 thread의 상태가 WAITING 상태인지 검증 합니다.
        Assertions.assertEquals(Thread.State.WAITING, thread.getState());

        thread.interrupt();
    }
}