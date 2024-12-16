package com.hd.v01.item.service;

import com.hd.common.exception.ErrorCode;
import com.hd.common.exception.IdNotFoundException;
import com.hd.common.exception.NameDuplicateException;
import com.hd.common.frame.HDService;
import com.hd.v01.item.entity.ItemEntity;
import com.hd.v01.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService implements HDService<ItemEntity,Long> {

    final ItemRepository itemRepository;
    //private final Response response;


    @Override
    public ItemEntity get(Long aLong) {
        ItemEntity itemEntity = itemRepository.findById(aLong).orElseThrow(()-> new IdNotFoundException(ErrorCode.ID_NOT_FOUND.getErrorMessage(), ErrorCode.ID_NOT_FOUND));
        return itemEntity;
    }

    @Override
    public ItemEntity save(ItemEntity itemEntity) {

        Optional<ItemEntity> item =  itemRepository.findByName(itemEntity.getName());
        // 사용자 정의 예외 발생
        if( item.isPresent()){
            throw new NameDuplicateException(ErrorCode.NAME_DUPLICATED.getErrorMessage(), ErrorCode.NAME_DUPLICATED);
        }
        return itemRepository.save(itemEntity);

    }

    @Override
    public ItemEntity modify(ItemEntity itemEntity) {
        itemRepository.findById(itemEntity.getId()).orElseThrow(()-> new IdNotFoundException(ErrorCode.ID_NOT_FOUND.getErrorMessage(), ErrorCode.ID_NOT_FOUND));
        return itemRepository.save(itemEntity);
    }


    @Override
    public Long remove(Long aLong)  {
        ItemEntity item = itemRepository.findById(aLong).orElseThrow(()-> new IdNotFoundException(ErrorCode.ID_NOT_FOUND.getErrorMessage(), ErrorCode.ID_NOT_FOUND));
        itemRepository.delete(item);
        return aLong;
    }

    @Override
    public List<ItemEntity> getall() {
        List<ItemEntity> items = itemRepository.findAll();
        if(items.isEmpty()){
            throw new IllegalArgumentException(ErrorCode.DATA_DOSE_NOT_EXIST.getErrorMessage());
        }
        return items;
    }

}
