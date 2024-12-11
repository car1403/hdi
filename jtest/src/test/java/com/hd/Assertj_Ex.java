package com.hd;

import com.hd.ex.AClass;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@SpringBootTest
@Slf4j
@DisplayName("Assertj Exception Test")
class Assertj_Ex {
    @Test
    public void Test1() {
        assertThatThrownBy(() -> AClass.func())
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void Test2() {
        Throwable exception = assertThrows(RuntimeException.class, () -> {
            AClass.func();
        });
        assertEquals("some exception message...", exception.getMessage());
    }

    @Test
    void exception() throws Exception {
        assertThatThrownBy(() -> AClass.func())
                .isInstanceOf(RuntimeException.class)
                .hasMessage("some exception message...") // Exception 객체가 가지고있는 메시지 검증
                .hasMessageEndingWith("...")
                .hasStackTraceContaining("some");
    }
}
