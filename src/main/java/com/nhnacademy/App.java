package com.nhnacademy;

import com.nhnacademy.customer.generator.CustomerGenerator;
import com.nhnacademy.nhnmart.entring.EnteringQueue;
public class App
{
    public static void main( String[] args )
    {

        //capacity를 100으로 enteringQueue를 초기화 합니다.
        EnteringQueue enteringQueue = new EnteringQueue(100);

        //customerGenerator를 이용해서 thread를 생성 합니다.
        CustomerGenerator customerGenerator = new CustomerGenerator(enteringQueue);
        Thread enteringThread = new Thread(customerGenerator);

        //enteringThread의 이름을 'entering-thread'로 설정, enteringThread를 시작 합니다.
        enteringThread.setName("entering-thread");
        enteringThread.start();

    }
}
