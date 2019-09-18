/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.mapper;

import com.shoppingcart.demo.dto.CartItemDto;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author nileshkumar
 */
public class CartItemMapper implements RowMapper<CartItemDto> {

    @Override
    public CartItemDto mapRow(ResultSet rs, int i) throws SQLException {
        CartItemDto cartItem = new CartItemDto();
        cartItem.setCartItemId(rs.getInt("cart_item_id"));
        cartItem.setProductId(rs.getInt("product_id"));
        cartItem.setProductName(rs.getString("product_name"));
        cartItem.setUnitPrice(rs.getDouble("product_price"));
        cartItem.setQuantity(rs.getInt("order_quantity"));
        cartItem.setCustomerId(rs.getInt("customer_id"));
        return cartItem;
    }
}
