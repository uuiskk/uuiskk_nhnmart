package com.nhnacademy.thread.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
class RequestHandlerTest {

    @Test
    @DisplayName("RequestChannel = null")
    void constructorTest(){
        Assertions.assertThrows(IllegalArgumentException.class,()->{
           new RequestHandler(null);
        });
    }

    @Test
    @DisplayName("producer & consumer")
    void run(){
        RequestChannel requestChannel = new RequestChannel();
        RequestHandler requestHandler = new RequestHandler(requestChannel);

        AtomicInteger couter = new AtomicInteger();
        Executable countExecutable = new Executable() {
            @Override
            public void execute() throws InterruptedException {
                int count = couter.incrementAndGet();
                log.debug("count:{}",count);
            }
        };
        //생산자 requestChannel 실행할 작업을 1초에 한 번씩 총 5회 추가 합니다.
        Thread producer = new Thread(()->{
            for(int i=1; i<=5; i++){
                try {
                    Thread.sleep(1000);
                    requestChannel.addRequest(countExecutable);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        producer.start();

        Thread consumer= new Thread(requestHandler);
        consumer.start();

        //생산자의 작업이 끝나지 않았다면 테스트를 싱행하는 main Thread는 대기 합니다.
        while (producer.isAlive()){
            Thread.yield();
        }

        log.debug("counter:{}", couter.get());

        Assertions.assertEquals(5,couter.get());
    }

}