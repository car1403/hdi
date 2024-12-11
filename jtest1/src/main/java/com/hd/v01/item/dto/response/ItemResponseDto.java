package com.hd.v01.item.dto.response;


import com.hd.v01.item.entity.ItemEntity;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class ItemResponseDto {
    Long id;
    String name;
    Long price;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    // dto로 변환하는 메소드
    /*
    public ItemResponseDto toDto(ItemEntity itemEntity) {
        this.id = itemEntity.getId();
        this.name = itemEntity.getName();
        this.price = itemEntity.getPrice();
        this.createdAt = itemEntity.getCreatedAt();
        this.updatedAt = itemEntity.getUpdatedAt();
        return this;
    }
    */

    public ItemResponseDto(ItemEntity entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }

}
