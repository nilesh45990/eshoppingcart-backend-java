/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.exception;

import com.shoppingcart.demo.model.GenericResponse;
import com.shoppingcart.demo.utils.ResponseBuilder;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author nileshkumar
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private ResponseBuilder responseBuilder;

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<GenericResponse> defaultErrorHandler(HttpServletRequest req, Exception e) {
        logger.error("Exception [URL] : {}", req.getRequestURL(), e);
        return ResponseEntity.ok(responseBuilder.buildErrorResponse("There is some technical issue"));
    }

}
