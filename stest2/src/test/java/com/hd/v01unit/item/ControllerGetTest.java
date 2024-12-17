package com.hd.v01unit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hd.common.dto.Response;
import com.hd.v1.app.item.controller.ItemController;
import com.hd.v1.common.entity.ItemEntity;
import com.hd.v1.app.item.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@WebMvcTest(controllers = ItemController.class)
//@AutoConfigureMockMvc
//@SpringBootTest(classes = ItemController.class)
@Import(value = Response.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName(" Controller Get Test ")
public class ControllerGetTest {
    @Autowired
    private MockMvc mockMvc;

//    @MockBean
//    Response response;

    @MockBean
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

        given(itemService.get(any())).willReturn(ItemEntity.builder().id(10L).name("p1").price(1000L).build());

        //when
        ResultActions resultActions= mockMvc.perform(get("/api/item/get/"+id));

        //verify
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(10L))
                .andExpect(jsonPath("$.data.name").value("p1"))
                .andExpect(jsonPath("$.data.price").value(1000L))

                .andDo(print());
        verify(itemService).get(refEq(id));
    }
}

