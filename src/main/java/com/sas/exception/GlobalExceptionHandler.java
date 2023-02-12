package com.sas.exception;

import com.sas.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Response> handlerException(BadRequestException ex) {
        Response res = new Response();
        res.setCode(ex.getCode());
        res.setStatus(ex.getHttpStatus());
        res.setMessage(ex.getMessage());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Response> handlerIllegalException(ObjectNotFoundException ex) {
        log.error("Object not found exception occured-:{}", ex);
        Response res = new Response();
        res.setCode(HttpStatus.NOT_FOUND.value());
        res.setStatus(HttpStatus.NOT_FOUND);
        res.setMessage(ex.getMessage());
        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseBody
    public ResponseEntity<Object> handleFileNotFoundException(MissingServletRequestParameterException ex) {
        log.info("MissingServletRequestParameterException Exception occurs => {}", ex);
        return new ResponseEntity<>(new Response(ex.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<Object> handleFileNotFoundException(HttpRequestMethodNotSupportedException ex) {
        log.info("HttpRequestMethodNotSupportedException Exception occurs => {}", ex);
        return new ResponseEntity<>(new Response(ex.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handlerException(Exception ex) {
        Response res = new Response();
        res.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        res.setMessage("Something went wrong, Please try Later!");
        log.error("Exception:{}", ex);
        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
