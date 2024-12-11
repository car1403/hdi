package com.hd;

import com.hd.car.Car;
import com.hd.car.CarStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

//@SpringBootTest
@Slf4j
@DisplayName("Assertions2")
class Assertj3 {

    @Test
    @DisplayName("Test1")
    public void Test1(){
        assertThat("Hello, world! Nice to meet you.") // 주어진 "Hello, world! Nice to meet you."라는 문자열은
                .isNotEmpty() // 비어있지 않고
                .contains("Nice") // "Nice"를 포함하고
                .contains("world") // "world"도 포함하고
                .doesNotContain("ZZZ") // "ZZZ"는 포함하지 않으며
                .startsWith("Hell") // "Hell"로 시작하고
                .endsWith("u.") // "u."로 끝나며
                .isEqualTo("Hello, world! Nice to meet you."); // "Hello, world! Nice to meet you."과 일치합니다.
    }

    @Test
    @DisplayName("Test2")
    public void Test2(){
        assertThat(3.14d) // 주어진 3.14라는 숫자는
                .isPositive() // 양수이고
                .isGreaterThan(3) // 3보다 크며
                .isLessThan(4) // 4보다 작습니다
                .isEqualTo(3, offset(1d)) // 오프셋 1 기준으로 3과 같고
                .isEqualTo(3.1, offset(0.1d)) // 오프셋 0.1 기준으로 3.1과 같으며
                .isEqualTo(3.14); // 오프셋 없이는 3.14와 같습니다
    }

    @Test
    @DisplayName("Test3")
    public void Test3(){
        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add("world");
        assertThat(list).contains("hello","world");
    }

    @Test
    @DisplayName("Test4")
    public void Test4(){
        Car car1 = Car.builder().name("Car1").size(1000).status(CarStatus.STOP).build();
        Car car2 = Car.builder().name("Car2").size(1000).status(CarStatus.STOP).build();

        assertThat(car1)
                .usingComparator((a, b) -> a.getName().compareTo(b.getName()))
                .isEqualTo(car2);

    }

    @Test
    @DisplayName("Test5")
    void Test5() throws Exception {
        Car car1 = Car.builder().name(null).size(1000).status(CarStatus.STOP).build();
        Car car2 = Car.builder().name("Car2").size(1000).status(CarStatus.STOP).build();

        // 테스트 성공. 검사대상에 null 필드 있음
        assertThat(car1).usingRecursiveComparison()
                .ignoringActualNullFields()
                .isEqualTo(car2);

        // 테스트 실패. 검증대상에는 null 필드가 없음.
        assertThat(car2).usingRecursiveComparison()
                .ignoringActualNullFields()
                .isEqualTo(car1); // AssertionError
    }
}
