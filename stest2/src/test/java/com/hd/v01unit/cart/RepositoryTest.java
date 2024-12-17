package com.hd.v01unit.cart;

import com.hd.v01.item.entity.CartEntity;
import com.hd.v01.item.entity.CustEntity;
import com.hd.v01.item.entity.ItemEntity;
import com.hd.v01.item.repository.CartRepository;
import com.hd.v01.item.repository.CustRepository;
import com.hd.v01.item.repository.ItemRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//@SpringBootTest

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 메모리 DB 사용 안함
@Import(value = com.hd.config.JpaAuditingConfig.class) // JPA Auditing
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
@DisplayName(" Repository Tests ")
@ActiveProfiles("h2")
class RepositoryTest {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    CustRepository custRepository;
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    EntityManager entityManager;

    CustEntity custEntity;
    ItemEntity itemEntity1, itemEntity2;
    CartEntity cartEntity1, cartEntity2;

    @BeforeEach
    public void db_init() {
        // MySQL
//        entityManager
//                .createNativeQuery("ALTER TABLE db_item AUTO_INCREMENT = 1;") // auto increment 초기화
//                .executeUpdate();

//         H2DB
        entityManager
                .createNativeQuery("ALTER TABLE db_cart ALTER COLUMN cart_id RESTART WITH 1") // auto increment 초기화
                .executeUpdate();


        custEntity = custRepository.save(CustEntity.builder().id("id01").pwd("pwd01").name("name01").build());
        itemEntity1 = itemRepository.save(ItemEntity.builder().name("item01").price(1000L).build());
        itemEntity2 = itemRepository.save(ItemEntity.builder().name("item02").price(2000L).build());
        cartEntity1 = CartEntity.builder().cnt(1)
                .custEntity(custEntity)
                .itemEntity(itemEntity1)
                .build();
        cartEntity2 = CartEntity.builder().cnt(3)
                .custEntity(custEntity)
                .itemEntity(itemEntity2)
                .build();
    }

    @Test
    @DisplayName(" 1. Insert Test ")
    @Order(1)
    public void save_test() { // 괄호안에 뭐 넣으면 안돼

        //given
        cartRepository.save(cartEntity1);
        cartRepository.save(cartEntity2);

        //when

        Optional<CartEntity> cart = cartRepository.findById(1L);

        log.info(cart.get().toString());
        List<CartEntity> carts = cartRepository.findByCustId("id01");
        log.info(carts.toString());

        //verify
        assertThat(cart.get().getCnt()).isEqualTo(1);
        assertThat(cart.get().getCust().getName()).isEqualTo(custEntity.getName());
        assertThat(carts.size()).isEqualTo(2);

    }

    @Test
    @Order(2)
    @DisplayName(" 2. FindById Test ")
    public void findById_test() {

        //given
        cartRepository.save(cartEntity1);
        cartRepository.save(cartEntity2);
        //when
        cartRepository.deleteById(1L);
        List<CartEntity> carts = cartRepository.findByCustId("id01");
        log.info(carts.toString());

        //verify
        assertThat(carts.size()).isEqualTo(1);
    }
//
//    @Test
//    @Order(3)
//    @DisplayName(" 3. Find All ")
//    public void findAll_test() {
//        // given
//        String id = "id01";
//        String pwd = "pwd01";
//        String name = "name01";
//
//
//        // when
//        CustEntity custEntity =  repository.save(CustEntity.builder().id(id).pwd(pwd).name(name).build());
//
//        List<CustEntity> custs = repository.findAll();
//
//        // then
//        assertThat(custs.size()).isEqualTo(1);
//
//        assertThat(custs.get(0).getId()).isEqualTo(id);
//        assertThat(custs.get(0).getPwd()).isEqualTo(pwd);
//        assertThat(custs.get(0).getName()).isEqualTo(name);
//
//    }
//
//    @Test
//    @Order(4)
//    @DisplayName(" 4. Update Test ")
//    public void update_test() {
//
//        //given
//        String id = "id01";
//        String pwd = "pwd01";
//        String name = "name01";
//
//        String upwd = "pwd11";
//        String uname = "name11";
//        //when
//        repository.save(CustEntity.builder().id(id).name(name).pwd(pwd).build());
//        CustEntity custEntity = repository.save(CustEntity.builder().id(id).name(uname).pwd(upwd).build());
//        //then
//        assertThat(id).isEqualTo(custEntity.getId());
//        assertThat(upwd).isEqualTo(custEntity.getPwd());
//        assertThat(uname).isEqualTo(custEntity.getName());
//
//    }
//
//    @Test
//    @Order(5)
//    @DisplayName(" 5. Delete Test ")
//    public void delete_test() {
//
//        //given
//        String id = "id01";
//        String pwd = "pwd01";
//        String name = "name01";
//
//        repository.save(CustEntity.builder().id(id).name(name).pwd(pwd).build());
//
//        //when
//        Optional<CustEntity> cust = repository.findById(id);
//        repository.delete(cust.get());
//
//
//        //then
//        assertThat(repository.findById(id)).isNotPresent();
//
//    }
}
