package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // 오토인크리먼트 db에 위임
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded // jpa 내장타입을 포함했다.
    private  Address address;

    @OneToMany(mappedBy = "member") //일대다 //order 테이블에있는 member필드에 매핑, 읽기전용
    private List<Order> orders = new ArrayList<>();
    //컬렉션 필드는 바로 초기화 하는것이 안전하고 코드도 간결, null문제에서 안전, 하이버네이트는 엔티티를 영속화 할 때, 컬랙션을 감싸서 하이버네이트가 제공하는 내장 컬렉션으로 변경한다. 임의의 메서드에서 컬력션을 잘못 생성하면 하이버네이트 내부 메커니즘에 문제가 발생할 수 있다.


}
