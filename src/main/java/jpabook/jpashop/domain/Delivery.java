package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// 오토인크리먼트 db에 위임
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = LAZY) //읽기전용, 기본 EAGER , LAZY로 바꿔줘야한다. n+1문제방지, 어떤 SQL 실행될지 추적이 힘듬
    private  Order order;

    @Embedded //내장타입
    private Address address;

    @Enumerated(EnumType.STRING) //스트링으로 써야 중간에 이넘 데이터 삽입되어도 밀리지 않는다.
    private DeliveryStatus status; //READY, COMP

}
