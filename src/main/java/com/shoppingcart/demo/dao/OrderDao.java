/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.dao;

import com.shoppingcart.demo.dto.OrderReportDto;
import com.shoppingcart.demo.dto.OrderDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nileshkumar
 */
public interface OrderDao {

    int addCustomerOrder(OrderDto orderDto);

    int updateOrderId(final List<Map<Integer, Integer>> cartItemLists);

    int updateOrderStatus(OrderDto orderDto );

    List<OrderReportDto> getAllOrderDetail();

    List<OrderReportDto> getOrderDetailById(int orderId);

    List<OrderReportDto> getOrderDetailOfCustomer(int customerId);
}
