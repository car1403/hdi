package com.hd.v01unit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hd.common.dto.Response;
import com.hd.v01.item.controller.ItemController;
import com.hd.v01.item.dto.request.ItemRequestDto;
import com.hd.v01.item.entity.ItemEntity;
import com.hd.v01.item.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@WebMvcTest(controllers = ItemController.class)
//@ComponentScan("com.hd.common.dto")
//@AutoConfigureMockMvc
//@SpringBootTest(classes = ItemController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName(" Controller Get Test ")
public class ControllerGetTest {
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
    @DisplayName("Item Get 정상")
    void success1() throws Exception {

        //given
        long id = 10L;
        ItemEntity itemEntity = ItemEntity.builder().id(100L).name("p1").price(1000L).build();
        Response.Body body = Response.Body.builder()
                .state(200)
                .data(itemEntity)
                .result("success")
                .massage("aa")
                .error(Collections.emptyList())
                .build();


        ResponseEntity r= ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(body));
        log.info(r.getBody().toString());

        given(itemService.get(any())).willReturn(ItemEntity.builder().id(100L).name("p1").price(1000L).build());
        //given(response.success(any())).willReturn(r);

        //when
        ResultActions resultActions= mockMvc.perform(get("/api/item/get/"+id));

        //verify
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(100L))
                .andExpect(jsonPath("$.data.name").value("p1"))
                .andExpect(jsonPath("$.data.price").value(1000L))

                .andDo(print());
        verify(itemService).get(refEq(id));
    }
}

