/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.service;

import com.shoppingcart.demo.model.GenericResponse;
import com.shoppingcart.demo.model.OrderModel;

/**
 *
 * @author nileshkumar
 */
public interface OrderService {

    GenericResponse addCustomerOrder(String customerId);

    GenericResponse updateOrderStatus(OrderModel order);

    GenericResponse cancelCustomerOrder(String customerId);

    GenericResponse getAllOrderDetail();

    GenericResponse getOrderDetailById(String orderId);

    GenericResponse getOrderDetailOfCustomer(String customerId);

}
