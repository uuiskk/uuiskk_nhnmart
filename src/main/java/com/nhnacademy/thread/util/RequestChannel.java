package com.nhnacademy.thread.util;

import java.util.LinkedList;
import java.util.Queue;

public class RequestChannel {

    //Executable type의 Queue
    private final Queue<Executable> requestQueue;
    //기본 Queue Size = 10
    private static final long DEFAULT_QUEUE_SIZE = 10;
    // queue size
    private final long queueSize;

    public RequestChannel(){
        //TODO#8-13 기본생성자 - DEFAULT_QUEUE_SIZE 기반으로 QUEUE를 생성 합니다.
        this(DEFAULT_QUEUE_SIZE);
    }

    public RequestChannel(long queueSize) {
        //TODO#8-14 queueSize<0 이면 IllegalArgumentException 발생 합니다.
        if(queueSize<0){
            throw new IllegalArgumentException();
        }

        //TODO#8-15 queueSize, requestQueue를 초기화 합니다.
        this.queueSize = queueSize;
        this.requestQueue = new LinkedList<>();
    }

    public synchronized void addRequest(Executable executable){
        //TODO#8-16 while 조건을 수정하세요.  requestQueue.size() >= queueSize 대기 합니다.
        while(requestQueue.size() >= queueSize){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        //TODO#8-17 requestQueue에  executable(작업) 추가하고 대기하고 있는 thread를 깨웁니다.
        requestQueue.add(executable);
        notifyAll();
    }

    public synchronized Executable getRequest(){
        //TODO#8-18 requestQueue가 비어 있다면(작업할 것이 없다면) 대기 합니다.
        while(requestQueue.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        //TODO#8-19 requestQueue에서 Executable(작업)을 반환 하고 , 대기하고 있는 thread를 깨웁 니다.
        notifyAll();
        return requestQueue.poll();
    }

}