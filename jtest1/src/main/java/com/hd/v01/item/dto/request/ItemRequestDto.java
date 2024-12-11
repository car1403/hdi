package com.hd.v01.item.dto.request;


import com.hd.v01.item.entity.ItemEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDto {

    Long id;
    @NotEmpty(message = "Name cannot be empty")
    String name;
    @Min(value = 10, message = "10이상이어야 함")
    Long price;

    public ItemEntity toEntity() {
        return ItemEntity.builder()
                .name(this.name)
                .price(this.price)
                .build();
    }

}