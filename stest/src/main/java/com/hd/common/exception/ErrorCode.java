package com.hd.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public enum ErrorCode {
//  HttpStatus 의 기본 Error코드 이외의 사용자 정의 Error 코드 입력
    ALL_Exception("EX100","INTERNAL EXCEPTION"),
//    ACCESS_DENIED("E202","ACCESS DENIED"),
//    ACCESS_ERROR("E201","ACCESS ERROR"),
//    REFRESH_TOKEN_NOT_AVAILABLE("T100","REFRESH TOKEN NOT AVAILABLE"),
//    NOT_FOUND("E401","PAGE NOT FOUND"),
//    EMAIL_NOT_FOUND("E501","EMAIL PAGE NOT FOUND"),
//    EMAIL_DUPLICATION("E601","EMAIL DUPLICATED"),

    NAME_DUPLICATED("E701","NAME DUPLICATED"),
    ID_NOT_FOUND("E801","ID NOT FOUND"),
    DATA_DOSE_NOT_EXIST("E901","DATA DOSE NOT EXIST"),
    ;

    private String errorCode;
    private String errorMessage;

    @JsonValue
    public ErrorDetails toJson() {
        return new ErrorDetails(errorCode, errorMessage);
    }

    @AllArgsConstructor
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ErrorDetails {
        @JsonProperty("errorCode")
        private final String errorCode;
        @JsonProperty("errorMessage")
        private final String message;
    }


}
