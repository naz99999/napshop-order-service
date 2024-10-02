package com.napshop.exception.model;


import lombok.Getter;

import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private String errorCode;
    private String message;

    public ErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

}
