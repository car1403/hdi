package com.hd.v1.app.item.dto.request;


import com.hd.v1.common.entity.ItemEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
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
                .id(this.id)
                .name(this.name)
                .price(this.price)
                .build();
    }

}
