package com.hd.v1.app.cart.service;

import com.hd.common.exception.ErrorCode;
import com.hd.common.exception.IdNotFoundException;
import com.hd.common.frame.HDService;
import com.hd.v1.app.cart.repository.CartRepository;
import com.hd.v1.common.entity.CartEntity;
import com.hd.v1.common.entity.ItemEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService implements HDService<CartEntity, Long> {

    private final CartRepository cartRepository;

    @Override
    public CartEntity get(Long aLong) {
        return cartRepository.findById(aLong).orElseThrow(()-> new IdNotFoundException(ErrorCode.ID_NOT_FOUND.getErrorMessage(), ErrorCode.ID_NOT_FOUND));
    }

    @Override
    public CartEntity save(CartEntity cartEntity) {
        return null;
    }

    @Override
    public CartEntity modify(CartEntity cartEntity) throws Exception {
        return null;
    }

    @Override
    public Long remove(Long aLong) throws Exception {
        return 0L;
    }

    @Override
    public List<CartEntity> getall() {
        return List.of();
    }
}
