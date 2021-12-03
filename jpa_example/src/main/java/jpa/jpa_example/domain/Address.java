package jpa.jpa_example.domain;

import javax.persistence.Embeddable;

import lombok.Getter;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {
        //임베디드 타입( @Embeddable )은 자바 기본 생성자(default constructor)를 public 또는 protected 로 설정해야 한다.
        //public 으로 두는 것 보다는 protected 로 설정하는 것이 그나마 더 안전하다
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
