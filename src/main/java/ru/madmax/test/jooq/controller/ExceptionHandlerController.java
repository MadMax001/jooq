package ru.madmax.test.jooq.controller;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.filters.AddDefaultCharsetFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.NoSuchElementException;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerController {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<AddDefaultCharsetFilter.ResponseWrapper>
            handleConstraintValidationException(NoSuchElementException exception) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("message", Collections.singletonList(exception.getMessage()));
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }

}
