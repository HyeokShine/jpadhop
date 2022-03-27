package jpabook.jpashop.service;

import jpabook.jpashop.repository.item.Book;
import jpabook.jpashop.repository.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item); //저장
    }

    @Transactional //변경감지에 의해 데이터 업데이트
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
    }

    public List<Item> findItems() {
        return itemRepository.findAll(); //전체조회
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId); //하나 조회
    }
}
