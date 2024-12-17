package com.hd.v01.item.repository;


import com.hd.v01.item.entity.CustEntity;
import com.hd.v01.item.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustRepository extends JpaRepository<CustEntity,String> {
}
