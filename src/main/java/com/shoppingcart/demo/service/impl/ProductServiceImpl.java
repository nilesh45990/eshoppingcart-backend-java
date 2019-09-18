/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.service.impl;

import com.shoppingcart.demo.dao.ProductDao;
import com.shoppingcart.demo.dto.ProductDto;
import com.shoppingcart.demo.model.GenericResponse;
import com.shoppingcart.demo.service.MessageService;
import com.shoppingcart.demo.service.ProductService;
import com.shoppingcart.demo.utils.ResponseBuilder;
import com.shoppingcart.demo.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nileshkumar
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ResponseBuilder responseBuilder;
    @Autowired
    private MessageService messageService;
    @Autowired
    private ProductDao dao;

    @Override
    public GenericResponse getAllProducts() {
        return responseBuilder.buildSuccessResponse(dao.getAllProducts());
    }

    @Override
    public GenericResponse getAllProductsTransactionList() {
        return responseBuilder.buildSuccessResponse(dao.getAllProductsTransactionList());
    }

    @Override
    public GenericResponse getProductTransactionList(String productId) {
        if (!Validator.isNumeric(productId)) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("product.id.not.valid"));
        }
        ProductDto product = dao.getProductById(Integer.parseInt(productId));
        if (product == null) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("product.not.found", new Object[]{productId}));
        }
        return responseBuilder.buildSuccessResponse(dao.getProductTransactionList(Integer.parseInt(productId)));
    }

    @Override
    public GenericResponse getProductById(String productId) {
        if (!Validator.isNumeric(productId)) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("product.id.not.valid"));
        }
        ProductDto product = dao.getProductById(Integer.parseInt(productId));
        if (product == null) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("product.not.found", new Object[]{productId}));
        }
        return responseBuilder.buildSuccessResponse(product);
    }

}
