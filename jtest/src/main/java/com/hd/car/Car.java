package com.hd.car;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Car{
    private CarStatus status;
    private String name;
    private int size;

    public void setStatus(CarStatus status) {
        this.status = status;
    }
    public CarStatus getStatus(){
        return this.status;
    }
}
