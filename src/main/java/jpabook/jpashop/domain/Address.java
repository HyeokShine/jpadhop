package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable //jpa 내장 타입
@Getter
//값 타입은 변경 불가능하게 설계해야한다. (@Setter제거)
public class Address {

    private  String city;
    private  String street;
    private  String zipcode;

    protected Address() {
    } //jpa 스펙상 @Embeddable는 기본 생성자를 만들어줘야한다. (public < protected)

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
