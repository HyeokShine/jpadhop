package jpabook.jpashop.repository.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;
//import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //strategy 전략
@DiscriminatorColumn(name = "dtype") //구분자 사용위해
@Getter @Setter
//@Setter //Setter 사용 가급적 x, 비즈니스 로직 만들어서 사용
public abstract class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// 오토인크리먼트 db에 위임
    @Column(name = "item_id")
    private Long id;

    private String name;
    private  int price;
    private  int stockQuantity;

   @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

   //비즈니스 로직

    /**
     * 재고수량 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * 재고수량 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;

    }


}
