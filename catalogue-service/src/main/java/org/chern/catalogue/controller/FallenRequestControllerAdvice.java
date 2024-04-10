package org.chern.catalogue.controller;

import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;
import java.util.Objects;

/*
    Вынос логики обработки ошибок наружу, в советы. В рамках АОП.

    создается совет-контроллер, который отлавливает исключение,
    Иными словами, когда в целевых контроллерах вылетает исключение BindException,
    этот контроллер вызывается и обрабатывает ошибку..
*/
@ControllerAdvice
@RequiredArgsConstructor
public class FallenRequestControllerAdvice {

    private final MessageSource messageSource;

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ProblemDetail> handleException(BindException exception, Locale locale) {
        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST,
                        Objects.requireNonNull(this.messageSource.getMessage("errors.400.title",
                                new Object[0], "errors.400.title", locale)));
        problemDetail.setProperty("errors", exception.getAllErrors()
                .stream().map(ObjectError::getDefaultMessage)
                .toList());
        //>>>>>>>>>>>>>>>>>>>
        return ResponseEntity.badRequest().body(problemDetail);
    }
}
