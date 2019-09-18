/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.dao.impl;

import com.shoppingcart.demo.dao.CartDao;
import com.shoppingcart.demo.dto.CartItemDto;
import com.shoppingcart.demo.mapper.CartItemMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nileshkumar
 */
@Repository
public class CartDaoImpl extends GenericDaoImpl<CartItemDto> implements CartDao {

    @Override
    public List<CartItemDto> getCartItemsOfCustomer(int customerId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT CD.cart_item_id,CD.product_id,PD.product_name,PD.product_price,CD.order_quantity,CD.customer_id ");
        sql.append(" FROM cart_detail CD ");
        sql.append(" INNER JOIN product_detail PD ON PD.product_id=CD.product_id ");
        sql.append(" WHERE CD.customer_id=:customer_id AND CD.order_id IS NULL");
        Map<String, Object> map = new HashMap<>();
        map.put("customer_id", customerId);
        return getAllByParam(sql.toString(), map, new CartItemMapper());
    }

    @Override
    public CartItemDto getCartItem(int cartItemId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT CD.cart_item_id,CD.product_id,PD.product_name,PD.product_price,CD.order_quantity,CD.customer_id ");
        sql.append(" FROM cart_detail CD ");
        sql.append(" INNER JOIN product_detail PD ON PD.product_id=CD.product_id ");
        sql.append(" WHERE CD.cart_item_id=:cart_item_id AND CD.order_id IS NULL ");
        Map<String, Object> map = new HashMap<>();
        map.put("cart_item_id", cartItemId);
        return getById(sql.toString(), map, new CartItemMapper());
    }

    @Override
    public int addCartItem(CartItemDto cartItem) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO cart_detail(product_id,order_quantity,unit_price,customer_id) VALUES(:product_id,:order_quantity,:unit_price,:customer_id)");
        Map<String, Object> map = new HashMap<>();
        map.put("product_id", cartItem.getProductId());
        map.put("order_quantity", cartItem.getQuantity());
        map.put("unit_price", cartItem.getUnitPrice());
        map.put("customer_id", cartItem.getCustomerId());
        return save(sql.toString(), map);
    }

    @Override
    public int updateCartItem(CartItemDto cartItem) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE cart_detail SET order_quantity=:order_quantity,unit_price=:unit_price WHERE cart_item_id=:cart_item_id");
        Map<String, Object> map = new HashMap<>();
        map.put("order_quantity", cartItem.getQuantity());
        map.put("unit_price", cartItem.getUnitPrice());
        map.put("cart_item_id", cartItem.getCartItemId());
        return updateorDelete(sql.toString(), map);
    }

    @Override
    public int removeCartItem(int cartItemId) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM cart_detail WHERE cart_item_id=:cart_item_id");
        Map<String, Object> map = new HashMap<>();
        map.put("cart_item_id", cartItemId);
        return updateorDelete(sql.toString(), map);
    }
}
