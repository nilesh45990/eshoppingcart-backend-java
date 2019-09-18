/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.utils;

import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Mishti
 */
public class Validator {

    public static boolean isNumeric(String val) {
        return StringUtils.isNumeric(val);
    }

}
