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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@WebMvcTest(controllers = ItemController.class)
//@AutoConfigureMockMvc
//@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName(" Controller Get ALl Test ")
public class ControllerGetAllTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    Response response;

    @MockitoBean
    ItemService itemService;

    private ObjectMapper objectMapper;



    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @Order(1)
    @DisplayName("Item Get All 정상")
    void success1() throws Exception {

        //given
        List<ItemEntity> items = new ArrayList<>();
        items.add(ItemRequestDto.builder().id(10L).name("p1").price(100L).build().toEntity());
        items.add(ItemRequestDto.builder().id(11L).name("p2").price(200L).build().toEntity());


        //stub
        given(itemService.getall()).willReturn(items);

        //when
        ResultActions resultActions= mockMvc.perform(get("/api/item/get"));

        //verify
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(10L))
                .andExpect(jsonPath("$.data[0].name").value("p1"))
                .andExpect(jsonPath("$.data[0].price").value(100L))
                .andExpect(jsonPath("$.data[1].id").value(11L))
                .andExpect(jsonPath("$.data[1].name").value("p2"))
                .andExpect(jsonPath("$.data[1].price").value(200L))
                .andDo(print());
        verify(itemService).getall();
    }
}

