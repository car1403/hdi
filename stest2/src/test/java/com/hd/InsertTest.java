package com.hd;


import com.hd.v01.item.entity.CartEntity;
import com.hd.v01.item.entity.CustEntity;
import com.hd.v01.item.entity.ItemEntity;
import com.hd.v01.item.repository.CartRepository;
import com.hd.v01.item.repository.CustRepository;
import com.hd.v01.item.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Slf4j
class InsertTests {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CustRepository custRepository;
    @Autowired
    ItemRepository itemRepository;

    @Test
    void contextLoads() {
        CustEntity custEntity = custRepository.save(CustEntity.builder().id("id02").pwd("pwd01").name("name01").build());
        ItemEntity itemEntity = itemRepository.save(ItemEntity.builder().name("item0a1").price(1000L).build());
        log.info(itemEntity.toString());
        log.info(custEntity.toString());
        //when
//        CartEntity inputCartEntity = CartEntity.builder().cnt(1)
//                .custEntity(CustEntity.builder().id(custEntity.getId()).build())
//                .itemEntity(ItemEntity.builder().id(itemEntity.getId()).build())
//                .build();
        cartRepository.save(CartEntity.builder().cnt(1)
                .custEntity(CustEntity.builder().id(custEntity.getId()).build())
                .itemEntity(ItemEntity.builder().id(itemEntity.getId()).build())
                .build());
        Optional<CartEntity> cart = cartRepository.findById(1L);
        log.info(cart.get().toString());
        List<CartEntity> carts = cartRepository.findByCustId("id01");
        log.info(carts.toString());


    }

}