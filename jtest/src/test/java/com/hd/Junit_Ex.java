package com.hd;

import com.hd.ex.AClass;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//@SpringBootTest
@Slf4j
@DisplayName("Junit 예외처리")
class Junit_Ex {

    @Test
    public void Teest1() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            AClass.func();
        });
    }

    @Test
    public void Test2() {
        try {
            AClass.func();
        } catch (RuntimeException e) {
            Assertions.assertEquals("some exception message...", e.getMessage());
        }
    }
}
