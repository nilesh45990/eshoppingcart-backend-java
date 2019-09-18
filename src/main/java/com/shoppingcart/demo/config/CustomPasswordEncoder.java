/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.config;

import com.shoppingcart.demo.utils.Utils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author nilesh
 */
@Service
public class CustomPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence cs) {
        return Utils.getEncodedString(cs.toString());
    }

    @Override
    public boolean matches(CharSequence cs, String encodedPassword) {
        return this.encode(cs).equalsIgnoreCase(encodedPassword);
    }

}
