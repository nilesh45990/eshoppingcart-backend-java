/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.controller;

import com.shoppingcart.demo.model.GenericResponse;
import com.shoppingcart.demo.service.ProductService;
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
@Api("Product")
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "Get all products")
    @GetMapping("/get-all-products")
    public GenericResponse getAllProducts() {
        return productService.getAllProducts();
    }

    @ApiOperation(value = "Get product detail of product for given product id")
    @GetMapping("/get-product/{productId}")
    public GenericResponse getProductById(@PathVariable(value = "productId") String productId) {
        return productService.getProductById(productId);
    }
}
