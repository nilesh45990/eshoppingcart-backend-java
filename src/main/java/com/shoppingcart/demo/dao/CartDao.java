/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.dao;

import com.shoppingcart.demo.dto.CartItemDto;
import java.util.List;

/**
 *
 * @author nileshkumar
 */
public interface CartDao {

    List<CartItemDto> getCartItemsOfCustomer(int customerId);

    CartItemDto getCartItem(int cartItemId);

    int addCartItem(CartItemDto cartItem);

    int updateCartItem(CartItemDto cartItem);

    int removeCartItem(int cartItemId);
}
