package com.nhnacademy.customer.domain;

import com.nhnacademy.customer.exception.InsufficientFundsException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@Slf4j
public class Customer {

    private final long id;
    private final String name;

    private int money;

    public Customer(long id, String name, int money) {
        //TODO#1-1 id < 1 or name null or ""  or money <0 이면 IllegalArgumentException 이 발생 합니다.
        if(id<1 || StringUtils.isEmpty(name) || money < 0 ){
            throw new IllegalArgumentException();
        }

        //TODO#1-2 id,name, money를 초기화 합니다.
        this.id = id;
        this.name = name;
        this.money= money;
    }

    public long getId() {
        //TODO#1-3 method를 구현하세요, id를 반환 합니다.
        return id;
    }

    public String getName() {
        //TODO#1-4 method를 구현하세요, name을 반환 합니다.
        return name;
    }

    public int getMoney() {
        //TODO#1-5 method를 구현하세요, money를 반환 합니다.
        return money;
    }

    public void pay(int amount) throws InsufficientFundsException {
        //TODO#1-6 money(회원 보유금액) < amount(결제할 금액) IllegalArgumentException이 발생 합니다.
        if(amount <0){
            throw new IllegalArgumentException(String.format("amount:%d", amount));
        }
        //TODO#1-7  money(회원 보유금액) < amount(결제할 금액)이면 InsufficientFundsException 이 발생 합니다.
        if(money < amount){
            throw new InsufficientFundsException();
        }
        //TODO#1-8 method를 구현 합니다. money에서 amount 만큼 차감 합니다.
        this.money = money - amount;
        log.debug("customer: {}, pay : {}", this, amount);
    }

    @Override
    public String toString() {
        //TODO#1-9 id, name, money 반환될 수 있도록 구현 합니다.
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", money=" + money +
                '}';
    }

    //TODO#1-10 customer객체 비교를 위해서(비교 기준은 id, name, money 일치) equals, hashCode method를 구현 합니다.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id && money == customer.money && Objects.equals(name, customer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, money);
    }
}
