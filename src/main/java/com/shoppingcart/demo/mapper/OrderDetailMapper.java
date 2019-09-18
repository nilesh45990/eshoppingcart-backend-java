/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.mapper;

import com.shoppingcart.demo.dto.OrderDto;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author nileshkumar
 */
public class OrderDetailMapper implements RowMapper<OrderDto> {
    
    @Override
    public OrderDto mapRow(ResultSet rs, int i) throws SQLException {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(rs.getInt("order_id"));
        orderDto.setProductName(rs.getString("product_name"));
        orderDto.setCustomerId(rs.getInt("customer_id"));
        orderDto.setCustomerName(rs.getString("customer_name"));
        orderDto.setOrderQuantity(rs.getInt("order_quantity"));
        orderDto.setUnitPrice(rs.getDouble("unit_price"));
        orderDto.setTotalAmount(rs.getDouble("total_amount"));
        orderDto.setOrderDate(rs.getString("order_date"));
        orderDto.setOrderStatus(rs.getString("order_status"));
        return orderDto;
    }
    
}
