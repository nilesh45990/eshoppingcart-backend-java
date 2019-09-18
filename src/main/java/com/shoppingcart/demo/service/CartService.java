/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.service;

import com.shoppingcart.demo.model.CartItemModel;
import com.shoppingcart.demo.model.GenericResponse;

/**
 *
 * @author nileshkumar
 */
public interface CartService {

    GenericResponse getCartItemsOfCustomer(String customerId);

    GenericResponse getCartItem(String cartItemId);

    GenericResponse addCartItem(CartItemModel cartItem);

    GenericResponse removeCartItem(String cartItemId);
}
