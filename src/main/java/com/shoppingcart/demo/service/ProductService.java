/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.service;

import com.shoppingcart.demo.model.GenericResponse;

/**
 *
 * @author nileshkumar
 */
public interface ProductService {

    GenericResponse getAllProducts();

    GenericResponse getAllProductsTransactionList();

    GenericResponse getProductTransactionList(String productId);

    GenericResponse getProductById(String productId);

}
