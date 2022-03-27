package jpabook.jpashop.domain;

import jpabook.jpashop.repository.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자 제한 유지보수 용의 만들어 놓은 생성자만 사용하게끔
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // 오토인크리먼트 db에 위임
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY) //기본 EAGER , LAZY로 바꿔줘야한다. n+1문제방지, 어떤 SQL 실행될지 추적이 힘듬
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private  Order order;

    private  int orderPrice; //주문 가격
    private  int count; //주문 수량

    //생성 매서드
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }


    //비즈니스 로직
    public void cancel() {
        getItem().addStock(count);
    }

    //조회 로직

    /**
     * 주문상품 전체 가격 조회
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
