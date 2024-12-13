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
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@WebMvcTest(controllers = ItemController.class)
//@AutoConfigureMockMvc
//@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName(" Controller Delete Test ")
public class ControllerDeleteTest {
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
    @DisplayName("Item Update 정상")
    void success1() throws Exception {

        //given
        long id = 10L;


        //stub
        given(itemService.remove(any())).willReturn(10L);

        //when
        ResultActions resultActions= mockMvc.perform(delete("/api/item/"+id));

        //verify
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(10L))
                .andDo(print());
        verify(itemService).remove(id);
    }

}
