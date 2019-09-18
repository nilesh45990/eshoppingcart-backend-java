/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.exception;

import org.springframework.security.core.AuthenticationException;

/**
 *
 * @author nilesh
 */
public class UserNotFoundException extends AuthenticationException {

    public UserNotFoundException(String msg) {
        super(msg);
        System.out.println("yeah method jandled");
    }

}
