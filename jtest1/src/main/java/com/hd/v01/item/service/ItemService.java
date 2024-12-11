package com.hd.v01.item.service;

import com.hd.common.exception.ErrorCode;
import com.hd.common.exception.NameDuplicateException;
import com.hd.common.frame.HDService;
import com.hd.v01.item.entity.ItemEntity;
import com.hd.v01.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService implements HDService<ItemEntity,Long> {

    final ItemRepository itemRepository;
    //private final Response response;


    @Override
    public ItemEntity get(Long aLong) {
        ItemEntity itemEntity = itemRepository.findById(aLong).orElseThrow(()->new IllegalArgumentException("id를 찾을 수 없습니다."));
        return itemEntity;
    }

    @Override
    public ItemEntity save(ItemEntity itemEntity) {
       // itemRe/pository.findByName(itemEntity.getName()).ifPresentOrElse();

        Optional<ItemEntity> item =  itemRepository.findByName(itemEntity.getName());
        // 사용자 정의 예외 발생
        if( item.isPresent()){
            throw new NameDuplicateException("Name duplicated", ErrorCode.NAME_DUPLICATION);
        }

        return itemRepository.save(itemEntity);

    }

    @Override
    public ItemEntity modify(ItemEntity itemEntity) {
        itemRepository.findById(itemEntity.getId()).orElseThrow(()->new IllegalArgumentException("id를 찾을 수 없습니다."));
        return itemRepository.save(itemEntity);
    }


    @Override
    public Long remove(Long aLong)  {
        ItemEntity item = itemRepository.findById(aLong).orElseThrow(()->new IllegalArgumentException("id를 찾을 수 없습니다."));
        itemRepository.delete(item);
        return aLong;
    }

    @Override
    public List<ItemEntity> getall() {
        List<ItemEntity> items = itemRepository.findAll();
        if(items.isEmpty()){
            throw new IllegalArgumentException("데이터가 좋재하지 않습니다.");
        }
        return items;
    }

}
