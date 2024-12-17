package com.hd.v1.app.cust.repository;


import com.hd.v1.common.entity.CustEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustRepository extends JpaRepository<CustEntity,String> {
}
