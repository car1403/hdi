package com.hd.v01unit.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hd.common.dto.Response;
import com.hd.v1.app.item.controller.ItemController;
import com.hd.v1.app.item.dto.request.ItemRequestDto;
import com.hd.v1.app.item.service.ItemService;
import com.hd.v1.common.entity.ItemEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@WebMvcTest(controllers = ItemController.class)
// Spring MVC 웹 계층의 테스트에 사용됩니다. 이 어노테이션을 사용하면 웹 계층에 관련된 컴포넌트만을 로드하여 빠르게 테스트를 수행할 수 있습니다.
// @Controller 또는 @RestController 아래에 메서드에 대한 테스트를 작성한다.의존 관계에 있는 다른 레이어를 사용할 때(예를들어, 리포지토리)는 Mock를 이용해야한다.
//@AutoConfigureMockMvc
// @Service나 @Repository가 붙은 객체들도 모두 메모리에 올린다는 것이다.
//@SpringBootTest
@Import(value = Response.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName(" Controller save Test ")
public class ControllerSaveTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ItemService itemService;

    // Java <-> JSON 변환해주는 역할
    //@Autowired로 주입하게 되면 Spring 컨텍스트가 ObjectMapper를 관리하기 때문 다른 테스트와 자원을 공유하게 되기 때문
    //테스트간 독립성이 중요한 경우 @BeforeEach로 초기화 하는 것이 좋다.
    private ObjectMapper objectMapper;



    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @Order(1)
    @DisplayName("새로운 Item Add 정상")
    void success1() throws Exception {

        //given
        ItemRequestDto reqDto = ItemRequestDto.builder().name("p1").price(1000L).build();
        String body = objectMapper.writeValueAsString(reqDto); // json 으로 변경한것

        //stub
        given(itemService.save(any())).willReturn(ItemEntity.builder().id(100L).name("p1").price(1000L).build());

        //when
        ResultActions resultActions= mockMvc.perform(post("/api/item/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(body));


        //verify
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(100L))
                .andExpect(jsonPath("$.data.name").value("p1"))
                .andExpect(jsonPath("$.data.price").value(1000L))
                .andDo(print());
        verify(itemService).save(refEq(reqDto.toEntity()));
    }
    @Test
    @Order(2)
    @DisplayName("Validated Name Test")
    void validtedName() throws Exception {
        //given
        ItemRequestDto reqDto = ItemRequestDto.builder().name(null).price(100L).build();

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/api/item/add")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(reqDto))
        );

        // then
        resultActions.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.error.[0].message").value("Name cannot be empty"));
    }
    @Test
    @Order(3)
    @DisplayName("Validated Price Test")
    void validatedPrice() throws Exception {
        //given
        ItemRequestDto reqDto = ItemRequestDto.builder().name("Pants").price(5L).build();

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/api/item/add")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(reqDto))
        );

        // then
        resultActions.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.error.[0].message").value("10이상이어야 함"));
    }

}

