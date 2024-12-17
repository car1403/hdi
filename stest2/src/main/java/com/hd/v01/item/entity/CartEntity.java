package com.hd.v01.item.entity;

import com.hd.common.entity.BaseEntity;
import com.hd.v01.item.repository.CartRepository;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="db_cart")
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자를 통해서 값 변경 목적으로 접근하는 메시지들 차단
@ToString(exclude = "cust")
@Getter
public class CartEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private int id;
    private int cnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cust_id")
    private CustEntity cust;
    @ManyToOne
    @JoinColumn(name="item_id")
    private ItemEntity item;

    @Builder
    public CartEntity(int id, int cnt, ItemEntity itemEntity, CustEntity custEntity) {
        this.id = id;
        this.cnt = cnt;
        this.item = itemEntity;
        this.cust = custEntity;
    }

}