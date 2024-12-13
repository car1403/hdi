package com.hd.v01unit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hd.common.dto.Response;
import com.hd.v01.item.controller.ItemController;
import com.hd.v01.item.dto.request.ItemRequestDto;
import com.hd.v01.item.entity.ItemEntity;
import com.hd.v01.item.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@WebMvcTest(controllers = ItemController.class)
//@AutoConfigureMockMvc
//@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName(" Controller save Test ")
public class ControllerSaveTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    Response response;

    @MockitoBean
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

