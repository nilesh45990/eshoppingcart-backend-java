/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.service.impl;

import com.shoppingcart.demo.dao.CustomerDao;
import com.shoppingcart.demo.dto.CustomerDto;
import com.shoppingcart.demo.model.GenericResponse;
import com.shoppingcart.demo.service.CustomerService;
import com.shoppingcart.demo.service.MessageService;
import com.shoppingcart.demo.utils.ResponseBuilder;
import com.shoppingcart.demo.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nileshkumar
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private ResponseBuilder responseBuilder;
    @Autowired
    private MessageService messageService;
    @Autowired
    private CustomerDao dao;

    @Override
    public GenericResponse getCustomerById(String customerId) {
        if (!Validator.isNumeric(customerId)) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("customer.id.not.valid"));
        }
        CustomerDto customer = dao.getCustomerById(Integer.parseInt(customerId));
        if (customer == null) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("customer.not.found", new Object[]{customerId}));
        }
        return responseBuilder.buildSuccessResponse(customer);
    }

}
