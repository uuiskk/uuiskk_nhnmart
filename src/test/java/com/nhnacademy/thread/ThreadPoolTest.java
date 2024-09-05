package com.nhnacademy.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.platform.commons.function.Try;
import org.junit.platform.commons.util.ReflectionUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ThreadPoolTest {

    static ThreadPool threadPool;
    @BeforeAll
    static void beforeAllSetUp() {
        threadPool = new ThreadPool(10,()->{
            log.debug("{} is running", Thread.currentThread().getName());

            while (!Thread.currentThread().isInterrupted()){
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.debug("{} is stop",Thread.currentThread().getName());
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Test
    @Order(1)
    @DisplayName("poolsize < 0")
    void constructorTest1(){
        Assertions.assertThrows(IllegalArgumentException.class,()->{
           new ThreadPool(-1,()->{});
        });
    }

    @Test
    @Order(2)
    @DisplayName("runnable  parameter check ")
    void constructorTest2(){
        Assertions.assertThrows(IllegalArgumentException.class,()->{
            new ThreadPool(1,null);
        });
    }
    @Test
    @Order(3)
    @DisplayName("thread-pool size")
    void constructorTest3() throws Exception {
        Try<Object> readFieldValue = ReflectionUtils.tryToReadFieldValue(ThreadPool.class, "threadList",threadPool);
        List<Thread> threadList = (List<Thread>) readFieldValue.get();

        Assertions.assertEquals(10,threadList.size());
    }

    @Test
    @Order(4)
    @DisplayName("thread start, thread Status check : alive")
    void start() throws Exception {
        threadPool.start();

        Try<Object> readFieldValue = ReflectionUtils.tryToReadFieldValue(ThreadPool.class, "threadList",threadPool);
        List<Thread> threadList = (List<Thread>) readFieldValue.get();
        int aliveCount = 0;
        for(Thread thread : threadList){
            if(thread.isAlive()){
                aliveCount ++;
            }
        }
        log.debug("aliveCount:{}",aliveCount);
        Assertions.assertEquals(10,aliveCount);
    }

    @Test
    @Order(5)
    @DisplayName("thread stop, thread Status : TERMINATED")
    void stop() throws Exception {
        threadPool.stop();

        Try<Object> readFieldValue = ReflectionUtils.tryToReadFieldValue(ThreadPool.class, "threadList",threadPool);
        List<Thread> threadList = (List<Thread>) readFieldValue.get();

        int terminatedCount = 0;
        for(Thread thread : threadList){
            if(thread.getState().equals(Thread.State.TERMINATED)){
                terminatedCount ++;
            }
        }

        log.debug("terminatedCount:{}",terminatedCount);
        Assertions.assertEquals(10,terminatedCount);
    }
}