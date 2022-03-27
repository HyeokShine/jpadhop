package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자 제한 유지보수 용의 만들어 놓은 생성자만 사용하게끔
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)// 오토인크리먼트 db에 위임
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY) // 다대일, 기본 EAGER , LAZY로 바꿔줘야한다. n+1문제방지, 어떤 SQL 실행될지 추적이 힘듬
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) //cascade 부모 엔티티의 동작을 따라한다?
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL) //일대일 관계일때는 아무대나 연관관계 주인을 설정해도 되지만 서비스 구현시 많이 엑세스 되는곳에 하는걸 권장, 기본 EAGER , LAZY로 바꿔줘야한다. n+1문제방지, 어떤 SQL 실행될지 추적이 힘듬
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]

    //연관관계 편의 메서드 양방향일때 사용하면 좋다 핵심적으로 컨트롤 하는쪽에 배치? 실질적으로 많이사용되는곳?
    //https://www.inflearn.com/questions/245796
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

//    public static void main(String[] args) {
//        Member member = new Member();
//        Order order = new Order();
//
//        member.getOrders().add(order);
//        order.setMember(member);
//    }

    public void  addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void  setDelivery(Delivery delivery) {
        this.delivery = (delivery);
        delivery.setOrder(this);
    }

    //생성 메서드
    /**
     * 주문생성
     */
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //비즈니스 로직
    /**
     * 주문취소
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    //조회 로직직
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
//        return orderItems.stream()
//                .mapToInt(OrderItem::getTotalPrice)
//                .sum(); //대채 가능
    }

}



