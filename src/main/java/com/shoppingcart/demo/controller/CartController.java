/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.controller;

import com.shoppingcart.demo.model.CartItemModel;
import com.shoppingcart.demo.model.GenericResponse;
import com.shoppingcart.demo.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@Api(value = "Cart")
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @ApiOperation(value = "View the cart detail of given customer")
    @GetMapping(value = "/get-customer-cart/{customerId}")
    public GenericResponse showCartItem(@PathVariable(value = "customerId") String customerId) {
        System.out.println("called ....#######");
        return cartService.getCartItemsOfCustomer(customerId);
    }

    @ApiOperation(value = "Add item to cart")
    @PostMapping(value = "/add-product", consumes = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse addCartItem(@Valid @RequestBody CartItemModel cartItemModel) {
        return cartService.addCartItem(cartItemModel);
    }

    @ApiOperation(value = "Remove item from cart for given cart item id")
    @DeleteMapping("/remove-product/{cartItemId}")
    public GenericResponse removeCartItem(@PathVariable(value = "cartItemId") String cartItemId) {
        return cartService.removeCartItem(cartItemId);
    }
}
