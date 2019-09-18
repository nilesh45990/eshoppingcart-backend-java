/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.model;

import java.util.List;

/**
 *
 * @author nileshkumar
 * @param <T>
 */
public class GenericResponse<T> {

    int status;
    String message;
    T obj;
    List<T> list;

    public GenericResponse() {
    }

    public GenericResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public GenericResponse(int status, String message, T obj) {
        this.status = status;
        this.message = message;
        this.obj = obj;
        this.list = null;
    }

    public GenericResponse(int status, String message, List<T> list) {
        this.status = status;
        this.message = message;
        this.obj = null;
        this.list = list;
    }

    public GenericResponse(int status, String message, T obj, List<T> list) {
        this.status = status;
        this.message = message;
        this.obj = obj;
        this.list = list;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getObj() {
        return obj;
    }

    public List<T> getList() {
        return list;
    }

}
