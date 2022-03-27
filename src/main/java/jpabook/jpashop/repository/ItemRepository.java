package jpabook.jpashop.repository;

import jpabook.jpashop.repository.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor //final인거 생성자 만들어주는거
public class ItemRepository {

    private final EntityManager em;

    /**
     * 상품 저장
     */
    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item); //item 저장, 신규 등록, 처음 저장할때 아이디가 없다 새로 생성하는 객체
        } else {
            em.merge(item); //병합
        }
    }

    /**
     * 상품 한개 조회
     */
     public Item findOne(Long id) {
        return em.find(Item.class, id);
     }

    /**
     * 상품 전체 조회
     */
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }


}
