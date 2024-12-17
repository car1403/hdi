package com.hd.v1.app.item.dto.response;


import com.hd.v1.common.entity.ItemEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponseDto {
    Long id;
    String name;
    Long price;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    // dto로 변환하는 메소드

//    public ItemResponseDto toDto(ItemEntity itemEntity) {
//        this.id = itemEntity.getId();
//        this.name = itemEntity.getName();
//        this.price = itemEntity.getPrice();
//        this.createdAt = itemEntity.getCreatedAt();
//        this.updatedAt = itemEntity.getUpdatedAt();
//        return this;
//    }


    public ItemResponseDto(ItemEntity entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }

}
