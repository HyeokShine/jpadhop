package jpabook.jpashop.domain;

import jpabook.jpashop.repository.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)// 오토인크리먼트 db에 위임
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = LAZY) //기본 EAGER , LAZY로 바꿔줘야한다. n+1문제방지, 어떤 SQL 실행될지 추적이 힘듬
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //연관관계 편의 메서드
    //https://www.inflearn.com/questions/245796
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}
