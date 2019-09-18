/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.service;

/**
 *
 * @author Mishti
 */
public interface MessageService {

    String getMessage(String key);

    String getMessage(String key, Object[] param);
}
