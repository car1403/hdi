package com.hd.v1.item.entity;


import com.hd.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
@Table(name="item_db")
public class ItemEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private Long price;

    @Builder
    public ItemEntity(Long id, String name, Long price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }


}