package com.sas.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class Response {
    private HttpStatus status;
    private int code;
    private String message;
    private Object responseObject;

    public Response(String message, Object responseObject, HttpStatus status) {
        this.message = message;
        this.status = status;
        this.code = status.value();
        this.responseObject = responseObject;
    }

    public Response(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
        this.code = status.value();
    }

    public Response(String message, HttpStatus status, HttpStatus code) {
        this.message = message;
        this.status = status;
        this.code = code.value();
    }
}
