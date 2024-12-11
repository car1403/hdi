package com.hd;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;


//@TestMethodOrder(MethodOrderer.MethodName.class)

//@TestMethodOrder(MethodOrderer.DisplayName.class)
//-> @DisplayName(””) 값에 적힌 문자열 순서
//
//@TestMethodOrder(MethodOrderer.MethodName.class)
//-> 메서드 이름의 문자열 순서
//
//@TestMethodOrder(MethodOrderer.Random.class)
//-> 랜덤 순서
//
//-> 실행 순서 직접 입력
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("순서테스트")
@Slf4j
class Junit_Order {

    @Test
    @Order(1)
    void 테스트C() {
        log.info("Test C");
    }
    @Test
    @Order(4)
    void 테스트D() {
        log.info("Test D");
    }
    @Test
    @Order(3)
    void 테스트E() {
        log.info("Test E");
    }
    @Test
    @Order(5)
    void 테스트A() {
        log.info("Test A");
    }
    @Test
    @Order(2)
    void 테스트B() { log.info("Test B"); }
}
