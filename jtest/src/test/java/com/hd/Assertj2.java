package com.hd;

import com.hd.car.Car;
import com.hd.car.CarStatus;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


//@SpringBootTest
@Slf4j
@DisplayName("Assertions")
class Assertj2 {


    Car car1 = Car.builder().name("Car1").size(1000).status(CarStatus.STOP).build();
    Car car2 = Car.builder().name("Car1").size(1000).status(CarStatus.STOP).build();
    Car car3 = null;

    @Test
    @DisplayName("Test1")
    public void Test1(){
        assertThat(car3).isNull();
        assertThat(car2).isNotNull();
//        car1 = null;
        assertThat(car1).isEqualTo(car2);
        assertThat(car1).isNotSameAs(car2);
    }
    @Test
    @DisplayName("Test2")
    public void Test2(){
        CarStatus status = car2.getStatus();
        assertThat(car2).isNotNull(); // car2 null이 아니어야 하고
        assertThat(CarStatus.GO).as("현재값 %s" , status.name()).isEqualTo(car2.getStatus());
    }
    @Test
    @DisplayName("Test3")
    public void Test3(){

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(car1).isNull();
            softly.assertThat(car2).isNull();
            softly.assertThat(car1).isNotEqualTo(car2);
            // 이 역시 assertAll() 를 호출할 필요가 없다. assertSoftly 가 알아서 해줌.
        });


    }
    @Test
    @DisplayName("Test4")
    void Test4() {
        List<Integer> list = Arrays.asList(1, 2, 3);

        assertThat(list).containsOnly(1, 2, 3);
        assertThat(list).containsOnly(3, 2, 1); // 순서는 고려하지 않는다.
        assertThat(list).containsOnly(1, 2, 3, 3); // 중복은 고려하지 않는다. (1,2,3) 으로 인식
        // (1,2) 나 (1,2,3,4)는 검증에 실패한다.
    }
    @Test
    @DisplayName("Test5")
    void Test5() {
        List<Integer> list = Arrays.asList(1, 2, 3);

        assertThat(list).containsExactly(1, 2, 3);
        // (1,2) (3,2,1) (1,2,3,3) 같은 건 검증 실패
    }
}
