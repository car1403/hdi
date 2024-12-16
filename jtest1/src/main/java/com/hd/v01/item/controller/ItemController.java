package com.hd.v01.item.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hd.common.dto.Response;
import com.hd.common.util.Helper;
import com.hd.v01.item.dto.request.ItemRequestDto;
import com.hd.v01.item.dto.response.ItemResponseDto;
import com.hd.v01.item.entity.ItemEntity;
import com.hd.v01.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final Response response;


    @PostMapping("/add")
    public ResponseEntity<?> add(@Validated @RequestBody ItemRequestDto dto, Errors errors) {
        // validation check
        log.info("Service: -------"+dto.toString());

        if (errors.hasErrors()) {
            log.info("Service  ex: -------");

            return response.invalidFields(Helper.refineErrors(errors));
        }
        return response.successCreate(itemService.save(dto.toEntity()));
    }

    @GetMapping("/get")
    public ResponseEntity<?> get() {
        log.info("get: -------");

        return response.success(itemService.getall().stream().map(ItemResponseDto::new).toList());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get( @PathVariable("id") Long id) throws JsonProcessingException {
        log.info("-----------------------------------------------------"+id);
//        ItemEntity itemEntity = ItemEntity.builder().id(100L).name("p1").price(1000L).build();
//        Response.Body body = Response.Body.builder()
//                .state(200)
//                .data(itemEntity)
//                .result("success")
//                .massage("aa")
//                .error(Collections.emptyList())
//                .build();
//        ObjectMapper objectMapper;
//        objectMapper = new ObjectMapper();
//
//
//        ResponseEntity r= ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(body));
//        return r;
        return response.success(new ItemResponseDto().toDto(itemService.get(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return response.success(itemService.remove(id));
    }

    @PatchMapping("/update")
    public ResponseEntity<?> update(@Validated @RequestBody ItemRequestDto dto, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return response.success(itemService.modify(dto.toEntity()));
    }

}


