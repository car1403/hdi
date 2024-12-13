package com.hd.v01unit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hd.v01.item.dto.request.ItemRequestDto;
import com.hd.v01.item.entity.ItemEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@Slf4j
//@WebMvcTest(controllers = ItemController.class)
//@SpringBootTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan("com.hd.common.dto")
//@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfiguration
@DisplayName(" Controller Get Test ")
@ActiveProfiles("mysql")
public class ControllerGetHTTPTest2 {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private ObjectMapper objectMapper;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/item/add";
    }

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("단건을 조회합니다2.")
    void selectCodeByCd2() throws Exception {
        log.info("Port: "+port);

        ItemRequestDto requestDto = ItemRequestDto.builder().name("aaaa").price(1000L).build();
        ResponseEntity<?> result1 = testRestTemplate.getForObject(getBaseUrl(),ResponseEntity.class);
        log.info("TEst -------------"+result1.getBody().toString());
        log.info("TEst -------------"+result1.getStatusCode().toString());
        log.info("TEst -------------"+result1.toString());
//        when(itemService.get(any())).thenReturn(ItemEntity.builder().id(10L).name("p1").price(1000L).build());
//
//        ResponseEntity<ItemEntity> createResponse = testRestTemplate.postForEntity("/api/item/get/10", newEntry, GuestbookEntity.class);

       // ResponseEntity<ItemEntity> result2 = testRestTemplate.getForEntity("/api/item/get/"+result1.getBody().getId(), ItemEntity.class);

       // log.info(result2.toString());
//        // given
//        long id = 10L;
//        ItemRequestDto requestDto = ItemRequestDto.builder().id(10L).name("p1").price(1000L).build();
//
//        // stub
//        given(itemService.get(any())).willReturn(ItemEntity.builder().id(100L).name("p1").price(1000L).build());
//
//        // when
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer xxxxxxx");
//
//        // then
//        String result = testRestTemplate.getForObject("/apt/item/get/10", String.class);
//
//        HttpEntity<ItemRequestDto> entity = new HttpEntity<>(requestDto,headers);
//        String url = "/api/item/get/10";
////        ResponseEntity<ItemResponseDto> codeDtoResult = restTemplate.get(url, HttpMethod.GET, entity, ItemResponseDto.class);
//        Assertions.assertEquals(codeDtoResult.getStatusCode(), HttpStatus.OK);
//        System.out.println("codeDtoResult ::" + codeDtoResult);
    }
}

