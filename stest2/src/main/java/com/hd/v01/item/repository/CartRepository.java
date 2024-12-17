package com.hd.v01.item.repository;


import com.hd.v01.item.entity.CartEntity;
import com.hd.v01.item.entity.CustEntity;
import com.hd.v01.item.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity,Long> {
    List<CartEntity> findByCustId(String id);
}
