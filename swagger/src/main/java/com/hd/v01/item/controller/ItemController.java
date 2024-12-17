package com.hd.v01.item.controller;


import com.hd.common.dto.Response;
import com.hd.common.dto.ResponseData;
import com.hd.common.util.Helper;
import com.hd.v01.item.dto.request.ItemRequestDto;
import com.hd.v01.item.dto.response.ItemResponseDto;
import com.hd.v01.item.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Item V0.1", description = "Item 관련 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final Response response;

    @Operation(summary = "상품등록", description = "name, price를 입력받아 상품등록을 진행한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = "400", description = "name 또는 price 입력 오류", content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = "500", description = "name 중복 오류 ", content = @Content(schema = @Schema(implementation = ResponseData.class))),
    })
    @PostMapping("/add")
    public ResponseEntity<?> add(@Validated @RequestBody ItemRequestDto dto, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            log.info("Service  ex: -------");

            return response.invalidFields(Helper.refineErrors(errors));
        }
        return response.successCreate(new ItemResponseDto(itemService.save(dto.toEntity())));
    }

    @GetMapping("/get")
    public ResponseEntity<?> get() {
        log.info("get: -------");

        return response.success(itemService.getall().stream().map(ItemResponseDto::new).toList());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get( @PathVariable("id") Long id)  {
        return response.success(new ItemResponseDto(itemService.get(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        log.info("Delete -------------------------------"+id);
        return response.success(itemService.remove(id));
    }

    @PatchMapping("/update")
    public ResponseEntity<?> update(@Validated @RequestBody ItemRequestDto dto, Errors errors) {
        log.info("Service  ex: -------update "+dto.toString());
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        log.info("update cont: -------");
        return response.success(new ItemResponseDto(itemService.modify(dto.toEntity())));
    }

}


