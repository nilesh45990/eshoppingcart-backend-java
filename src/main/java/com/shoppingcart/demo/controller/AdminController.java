/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.controller;

import com.shoppingcart.demo.model.GenericResponse;
import com.shoppingcart.demo.model.OrderModel;
import com.shoppingcart.demo.service.OrderService;
import com.shoppingcart.demo.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author nileshkumar
 */
@Api("User")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductService ProductService;
    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "Get all products transaction detail")
    @GetMapping(value = "/get-all-productstransaction")
    public GenericResponse getAllProductsTransactionList() {
        return ProductService.getAllProductsTransactionList();
    }
    
    @ApiOperation(value = "Get product transaction detail for given product id")
    @GetMapping(value = "/get-product-transaction/{productId}")
    public GenericResponse getProductTransactionList(@PathVariable(value = "productId") String productId) {
        return ProductService.getProductTransactionList(productId);
    }

    @ApiOperation(value = "Get all orders detail")
    @GetMapping(value = "/get-all-orders-detail")
    public GenericResponse getAllOrderDetail() {
        return orderService.getAllOrderDetail();
    }

    @ApiOperation(value = "Update status of order by admin user")
    @PostMapping("/update-order-status")
    public GenericResponse updateOrderStatus(@RequestBody OrderModel order) {
        return orderService.updateOrderStatus(order);
    }
}
