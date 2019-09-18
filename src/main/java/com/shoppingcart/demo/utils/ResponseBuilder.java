/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.utils;

import com.shoppingcart.demo.model.GenericResponse;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author nileshkumar
 */
@Component
public class ResponseBuilder<T> {

    public GenericResponse buildSuccessResponse(String message) {
        return new GenericResponse(1, message);
    }

    public GenericResponse buildSuccessResponse(T obj) {
        return new GenericResponse(1, "success", obj);
    }

    public GenericResponse buildSuccessResponse(List<T> list) {
        return new GenericResponse(1, "success", list);
    }

    public GenericResponse buildErrorResponse(String message) {
        return new GenericResponse(0, message, null, null);
    }

}
