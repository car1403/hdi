package com.hd.v01unit.item;

import com.hd.common.exception.ErrorCode;
import com.hd.common.exception.NameDuplicateException;
import com.hd.v1.common.entity.ItemEntity;
import com.hd.v1.app.item.repository.ItemRepository;
import com.hd.v1.app.item.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//@SpringBootTest

@Slf4j
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName(" Service Add Test ")
@ExtendWith(MockitoExtension.class) //스프링 컨텍스트를 로드하지 않고, mock 객체만 이용해 테스트하는 단위 테스트.
class ServiceAddTest {
    @Mock
    ItemRepository itemRepository;

    @InjectMocks
    ItemService itemService;

    private long id;
    private String name;
    private long price;

    private long newId;
    private String newName;
    private long newPrice;

    @BeforeEach
    void setup() {
        id = 10L;
        name = "pants1";
        price = 50;

        newId = 11L;
        newName = "pants1";
        newPrice = 100;
    }

    @Test
    @DisplayName("새로운 Item 생성")
    void saveTest() {
        // given
        ItemEntity itemEntity = ItemEntity.builder().id(id).name(name).price(price).build();

        //stub
        when(itemRepository.save(any(ItemEntity.class))).thenReturn(itemEntity);

        //when
        ItemEntity result = itemService.save(itemEntity);

        //verify
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getPrice()).isEqualTo(price);
    }

    @Test
    @DisplayName("반환된 게시물이 NULL인 경우")
    void returnNull() {
        // given
        ItemEntity itemEntity = ItemEntity.builder().name(name).price(price).build();

        //stub
        when(itemRepository.save(any(ItemEntity.class))).thenReturn(null);

        //when
        ItemEntity result = itemService.save(itemEntity);

        //verify
        assertThat(result).isNull();
    }
    @Test
    @DisplayName("예외인 경우")
    void throwEx() {
        //given
        ItemEntity itemEntity = ItemEntity.builder().id(id).name(name).price(price).build();

        // stub
        when(itemRepository.save(any(ItemEntity.class))).thenThrow(new NameDuplicateException(ErrorCode.NAME_DUPLICATED.getErrorMessage(), ErrorCode.NAME_DUPLICATED));
        //when(itemRepository.save(itemEntity)).thenThrow(
        //        new NameDuplicateException("Name duplicated", ErrorCode.NAME_DUPLICATION));

        // verify
        assertThatThrownBy(() -> itemService.save(itemEntity))
                .isInstanceOf(NameDuplicateException.class)
                .hasMessage(ErrorCode.NAME_DUPLICATED.getErrorMessage()); // Exception 객체가 가지고있는 메시지 검증

    }
    @Test
    @DisplayName("이름 중복 예외인 경우")
    void duplicatedName() {
        //given
        ItemEntity itemEntity = ItemEntity.builder().id(id).name(name).price(price).build();
        ItemEntity newItemEntity = ItemEntity.builder().id(newId).name(newName).price(newPrice).build();

        // stub
        when(itemRepository.findByName(newItemEntity.getName())).thenReturn(Optional.of(itemEntity));

        // verify
        assertThat(newItemEntity.getName()).isEqualTo(itemEntity.getName());
        assertThatThrownBy(() -> itemService.save(newItemEntity))
                .isInstanceOf(NameDuplicateException.class)
                .hasMessage(ErrorCode.NAME_DUPLICATED.getErrorMessage()); // Exception 객체가 가지고있는 메시지 검증

    }




}
