package com.hd.v01.item.controller;


import com.hd.common.dto.Response;
import com.hd.common.util.Helper;
import com.hd.v01.item.dto.request.ItemRequestDto;
import com.hd.v01.item.dto.response.ItemResponseDto;
import com.hd.v01.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
public class ItemController {

    private final ItemService itemService;


    @PostMapping("/add")
    public ResponseEntity<?> add(@Validated @RequestBody ItemRequestDto dto, Errors errors) {
        // validation check

        if (errors.hasErrors()) {
            return Response.invalidFields(Helper.refineErrors(errors));
        }
        return Response.success(itemService.save(dto.toEntity()));
    }

    @GetMapping("/get")
    public ResponseEntity<?> get() {
        return Response.success(itemService.getall().stream().map(ItemResponseDto::new).toList());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get( @RequestParam("id") Long id) {
        return Response.success(new ItemResponseDto(itemService.get(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@RequestParam("id") Long id) {
        return Response.success(itemService.remove(id));
    }

    @PatchMapping("/update")
    public ResponseEntity<?> update(@Validated @RequestBody ItemRequestDto dto, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return Response.invalidFields(Helper.refineErrors(errors));
        }
        return Response.success(itemService.modify(dto.toEntity()));
    }

}


