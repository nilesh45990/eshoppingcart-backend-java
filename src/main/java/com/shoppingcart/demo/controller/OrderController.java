/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.controller;

import com.shoppingcart.demo.model.GenericResponse;
import com.shoppingcart.demo.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author nileshkumar
 */
@Api("Order")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "Get all orders of customer for given customer id")
    @GetMapping(value = "/get-customer-orders/{customerId}")
    public GenericResponse getOrderDetailOfCustomer(@PathVariable(value = "customerId") String customerId) {
        return orderService.getOrderDetailOfCustomer(customerId);
    }

    @ApiOperation(value = "Get order detail of given order id")
    @GetMapping(value = "/get-order/{orderId}")
    public GenericResponse getOrderDetailById(@PathVariable(value = "orderId") String orderId) {
        return orderService.getOrderDetailById(orderId);
    }

    @ApiOperation(value = "Add order of customer")
    @GetMapping("/create/{customerId}")
    public GenericResponse addCustomerOrder(@PathVariable(value = "customerId") String customerId) {
        return orderService.addCustomerOrder(customerId);
    }

    @ApiOperation(value = "Cancel order of customer for given customer id")
    @GetMapping("/cancel/{customerId}")
    public GenericResponse cancelCustomerOrder(@PathVariable(value = "customerId") String customerId) {
        return orderService.cancelCustomerOrder(customerId);
    }

}
