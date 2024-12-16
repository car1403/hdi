package com.hd.v01integrate.item;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hd.common.exception.ErrorCode;
import com.hd.v01.item.dto.request.ItemRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Slf4j
//@WebMvcTest(controllers = ItemController.class)
//@SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@Import(value = {com.hd.config.JpaAuditingConfig.class})
//@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName(" Controller Get Test ")
@ActiveProfiles("mysql")
public class ControllerTestRestTemplate {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private ObjectMapper objectMapper;

    private String getBaseUrl(String action) {
        return "http://localhost:" + port + "/api/item/"+action;
    }

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }



    @Test
    @DisplayName("Item 등록 정상")
    @Order(1)
    void itemAdd() throws Exception {

        // given
        String name = "p1";
        long price = 1000L;
        ItemRequestDto requestDto = ItemRequestDto.builder().name(name).price(price).build();

        // when
        ResponseEntity<String> responseEntity = testRestTemplate.postForEntity(getBaseUrl("add"),requestDto, String.class);
        log.info("ResponseEntity -------------"+responseEntity.toString());
        log.info("Body -------------"+responseEntity.getBody().toString());

        // verify
        JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());
        log.info(jsonNode.get("data").get("id").toString());
        log.info(jsonNode.get("data").get("name").toString());
        log.info(jsonNode.get("data").get("price").toString());
        log.info(jsonNode.get("data").get("createdAt").toString());
        log.info(jsonNode.get("data").get("updatedAt").toString());


        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(jsonNode.get("data").get("name").asText()).isEqualTo(requestDto.getName());
        assertThat(jsonNode.get("data").get("price").asLong()).isEqualTo(requestDto.getPrice());

    }

    @Test
    @DisplayName("Item Name Duplicated Exception")
    @Order(2)
    void itemNameDuplicated() throws Exception {
        // given
        String name = "p1";
        long price = 1000L;
        ItemRequestDto requestDto = ItemRequestDto.builder().name(name).price(price).build();

        //when
        ResponseEntity<String> responseEntity = testRestTemplate.postForEntity(getBaseUrl("add"),requestDto, String.class);
        log.info("TEst1 -------------"+responseEntity.toString());
        log.info("TEst1 -------------"+responseEntity.getBody().toString());

        // verify
        JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(jsonNode.get("state").asInt()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(jsonNode.get("massage").asText()).isEqualTo(ErrorCode.NAME_DUPLICATED.getErrorMessage());
        assertThat(jsonNode.get("data").get("errorCode").asText()).isEqualTo(ErrorCode.NAME_DUPLICATED.getErrorCode());
        assertThat(jsonNode.get("data").get("errorMessage").asText()).isEqualTo(ErrorCode.NAME_DUPLICATED.getErrorMessage());

    }

    @Test
    @DisplayName("Item 수정 정상")
    @Order(3)
    void itemUpdate() throws Exception {

        // given
        long id = 1L;
        String name = "p2";
        long price = 2000L;
        ItemRequestDto requestDto = ItemRequestDto.builder().id(id).name(name).price(price).build();
        HttpEntity<ItemRequestDto> requestEntity = new HttpEntity<>(requestDto);

        // when

        ResponseEntity<String> responseEntity = testRestTemplate.exchange(getBaseUrl("update"),HttpMethod.PATCH,requestEntity,String.class);
        log.info("Update ResponseEntity -------------"+responseEntity.toString());
        log.info("Update Body -------------"+responseEntity.getBody().toString());

        // verify
        JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());
        log.info(jsonNode.get("data").get("id").toString());
        log.info(jsonNode.get("data").get("name").toString());
        log.info(jsonNode.get("data").get("price").toString());
        log.info(jsonNode.get("data").get("createdAt").toString());
        log.info(jsonNode.get("data").get("updatedAt").toString());


        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(jsonNode.get("data").get("name").asText()).isEqualTo(requestDto.getName());
        assertThat(jsonNode.get("data").get("price").asLong()).isEqualTo(requestDto.getPrice());

    }
    @Test
    @DisplayName("Item 수정 시 오류")
    @Order(4)
    void itemUpdateEx() throws Exception {

        // given
        long id = 10L;
        String name = "p2";
        long price = 2000L;
        ItemRequestDto requestDto = ItemRequestDto.builder().id(id).name(name).price(price).build();
        HttpEntity<ItemRequestDto> requestEntity = new HttpEntity<>(requestDto);

        // when

        ResponseEntity<String> responseEntity = testRestTemplate.exchange(getBaseUrl("update"),HttpMethod.PATCH,requestEntity,String.class);
        log.info("Update ResponseEntity -------------"+responseEntity.toString());
        log.info("Update Body -------------"+responseEntity.getBody().toString());

        // verify
        JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());


        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(jsonNode.get("state").asInt()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(jsonNode.get("massage").asText()).isEqualTo(ErrorCode.ID_NOT_FOUND.getErrorMessage());
        assertThat(jsonNode.get("data").get("errorCode").asText()).isEqualTo(ErrorCode.ID_NOT_FOUND.getErrorCode());
        assertThat(jsonNode.get("data").get("errorMessage").asText()).isEqualTo(ErrorCode.ID_NOT_FOUND.getErrorMessage());

    }
    @Test
    @DisplayName("Item ID조회 정상")
    @Order(5)
    void itemGet() throws Exception {

        // given
        long id = 1L;
//        String name = "p2";
//        long price = 2000L;
//        ItemRequestDto requestDto = ItemRequestDto.builder().id(id).name(name).price(price).build();
//        HttpEntity<Long> requestEntity = new HttpEntity<>(id);

        // when
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
       // testRestTemplate.getForEntity(getBaseUrl("/get"), String.class);
        ResponseEntity<String> responseEntity =         testRestTemplate.getForEntity(getBaseUrl("/get")+"/"+id, String.class);

        // verify
        JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(jsonNode.get("data").get("id").asLong()).isEqualTo(id);
        //assertThat(jsonNode.get("data").get("errorMessage").asText()).isEqualTo(ErrorCode.ID_NOT_FOUND.getErrorMessage());

    }

    @Test
    @DisplayName("Item Id Not Found Exception")
    @Order(6)
    void itemGetIdNotFound() throws Exception {

        // given
        long id = 2L;
//        String name = "p2";
//        long price = 2000L;
//        ItemRequestDto requestDto = ItemRequestDto.builder().id(id).name(name).price(price).build();
//        HttpEntity<Long> requestEntity = new HttpEntity<>(id);

        // when

        ResponseEntity<String> responseEntity =         testRestTemplate.getForEntity(getBaseUrl("/get")+"/"+id, String.class);

        // verify
        JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(jsonNode.get("state").asInt()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(jsonNode.get("massage").asText()).isEqualTo(ErrorCode.ID_NOT_FOUND.getErrorMessage());
        assertThat(jsonNode.get("data").get("errorCode").asText()).isEqualTo(ErrorCode.ID_NOT_FOUND.getErrorCode());
        assertThat(jsonNode.get("data").get("errorMessage").asText()).isEqualTo(ErrorCode.ID_NOT_FOUND.getErrorMessage());

    }

    @Test
    @DisplayName("Item 전체조회 정상")
    @Order(6)
    void itemGetAll() throws Exception {

        // given

        String name = "p3";
        long price = 3000L;
        ItemRequestDto requestDto = ItemRequestDto.builder().name(name).price(price).build();


        // when
        testRestTemplate.postForEntity(getBaseUrl("add"),requestDto, String.class);

        ResponseEntity<String> responseEntity =  testRestTemplate.getForEntity(getBaseUrl("/get"), String.class);
        log.info("Get All Body -------------"+responseEntity.getBody().toString());

        // verify
        JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());
        log.info("Get All Body -------------"+jsonNode.get("data").size());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(jsonNode.get("data").size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Item Delete Id Not Found Exception")
    @Order(7)
    void itemDeleteIdNotFound() throws Exception {

        // given
        String id = "10";

        // when
        ResponseEntity<String> responseEntity
                = testRestTemplate.exchange(
                getBaseUrl(id),
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                String.class
        );
        log.info("delete  Body -------------"+responseEntity.getBody().toString());

        // verify
        JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(jsonNode.get("state").asInt()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(jsonNode.get("massage").asText()).isEqualTo(ErrorCode.ID_NOT_FOUND.getErrorMessage());
        assertThat(jsonNode.get("data").get("errorCode").asText()).isEqualTo(ErrorCode.ID_NOT_FOUND.getErrorCode());
        assertThat(jsonNode.get("data").get("errorMessage").asText()).isEqualTo(ErrorCode.ID_NOT_FOUND.getErrorMessage());

    }

    @Test
    @DisplayName("Item Delete Complete")
    @Order(8)
    void itemDelete() throws Exception {

        // given
        String id = "2";

        // when
        ResponseEntity<String> responseEntity
                = testRestTemplate.exchange(
                getBaseUrl(id),
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                String.class
        );
        log.info("delete  Body -------------"+responseEntity.getBody().toString());

        // verify
        JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(jsonNode.get("data").asText()).isEqualTo(id);

//        assertThat(jsonNode.get("state").asInt()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
//        assertThat(jsonNode.get("massage").asText()).isEqualTo(ErrorCode.ID_NOT_FOUND.getErrorMessage());
//        assertThat(jsonNode.get("data").get("errorCode").asText()).isEqualTo(ErrorCode.ID_NOT_FOUND.getErrorCode());
//        assertThat(jsonNode.get("data").get("errorMessage").asText()).isEqualTo(ErrorCode.ID_NOT_FOUND.getErrorMessage());

    }
}

