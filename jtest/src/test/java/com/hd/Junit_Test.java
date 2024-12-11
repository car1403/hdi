package com.hd;

import com.hd.car.Car;
import com.hd.car.CarStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//@SpringBootTest
@Slf4j
@DisplayName("Assertions")
class Junit_Test {


    Car car1 = Car.builder().name("Car1").size(1000).status(CarStatus.STOP).build();
    Car car2 = Car.builder().name("Car1").size(1000).status(CarStatus.STOP).build();


    @Test
    @DisplayName("Test1")
    public void Test1(){
        Assertions.assertNotNull(car1, "Null");
        Assertions.assertNotNull(car2, "Null");
        car1 = null;
        Assertions.assertNotEquals(car1, car2, "다르다.");

    }
    @Test
    @DisplayName("Test2")
    public void Test2(){
        Assertions.assertNotNull(car2); // car2 null이 아니어야 하고
        Assertions.assertEquals(CarStatus.STOP, car2.getStatus(), "CAR를 처음 만들면 상태값은 STOP여야 합니다.");
    }
    @Test
    @DisplayName("Test3")
    public void Test3(){
        Assertions.assertAll(
                ()-> Assertions.assertNull(car1, "Not Null"),
                ()-> Assertions.assertNull(car2, "Not Null"),
                ()-> Assertions.assertNotEquals(car1, car2, "같다")

        );
    }

    @Test
    @DisplayName("Test4")
    public void Test4(){
        Assertions.assertEquals(car1, car2, "다르다.");
        Assertions.assertSame(car1, car1, "다르다.");
//        Assertions.assertSame(car1, car2, "다르다.");
    }
}
