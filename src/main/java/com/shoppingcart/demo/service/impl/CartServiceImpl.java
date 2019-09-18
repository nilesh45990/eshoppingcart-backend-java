/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.service.impl;

import com.shoppingcart.demo.dao.CartDao;
import com.shoppingcart.demo.dao.CustomerDao;
import com.shoppingcart.demo.dao.ProductDao;
import com.shoppingcart.demo.dto.CartItemDto;
import com.shoppingcart.demo.dto.CustomerDto;
import com.shoppingcart.demo.dto.ProductDto;
import com.shoppingcart.demo.model.CartItemModel;
import com.shoppingcart.demo.model.GenericResponse;
import com.shoppingcart.demo.service.CartService;
import com.shoppingcart.demo.service.MessageService;
import com.shoppingcart.demo.utils.ResponseBuilder;
import com.shoppingcart.demo.utils.Validator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * s
 *
 * @author nileshkumar
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ResponseBuilder responseBuilder;
    @Autowired
    private MessageService messageService;
    @Autowired
    private CartDao cartDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private ProductDao productDao;

    @Override
    public GenericResponse getCartItemsOfCustomer(String customerId) {
        if (!Validator.isNumeric(customerId)) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("customer.id.not.valid"));
        }
        CustomerDto customer = customerDao.getCustomerById(Integer.parseInt(customerId));
        if (customer == null) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("customer.not.found", new Object[]{customerId}));
        }
        return responseBuilder.buildSuccessResponse(cartDao.getCartItemsOfCustomer(customer.getCustomerId()));
    }

    @Override
    public GenericResponse getCartItem(String cartItemId) {
        if (!Validator.isNumeric(cartItemId)) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("cart.id.not.valid"));
        }
        CartItemDto cartItem = cartDao.getCartItem(Integer.parseInt(cartItemId));
        if (cartItem == null) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("cart.item.not.found", new Object[]{cartItemId}));
        }
        return responseBuilder.buildSuccessResponse(cartItem);
    }

    @Override
    public GenericResponse addCartItem(CartItemModel cartItemModel) {
        if (!Validator.isNumeric(cartItemModel.getQuantity())) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("order.qty.not.valid"));
        } else if (Integer.parseInt(cartItemModel.getQuantity()) <= 0) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("order.qty.not.valid"));
        } else if (!Validator.isNumeric(cartItemModel.getCustomerId())) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("customer.id.not.valid"));
        }
        CustomerDto customer = customerDao.getCustomerById(Integer.parseInt(cartItemModel.getCustomerId()));
        if (customer == null) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("customer.not.found", new Object[]{cartItemModel.getCustomerId()}));
        } else if (!Validator.isNumeric(cartItemModel.getProductId())) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("product.id.not.valid"));
        }
        ProductDto productDetail = productDao.getProductById(Integer.parseInt(cartItemModel.getProductId()));
        if (productDetail == null) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("product.not.found", new Object[]{cartItemModel.getProductId()}));
        }
        List<CartItemDto> cartItemList = cartDao.getCartItemsOfCustomer(customer.getCustomerId());
        if (productDetail.getProductStock() < Integer.parseInt(cartItemModel.getQuantity())) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("stock.not.available", new Object[]{productDetail.getProductName()}));
        }
        for (CartItemDto cartItem : cartItemList) {
            if (cartItem != null && cartItem.getProductId() == Integer.parseInt(cartItemModel.getProductId())) {
                cartItem.setUnitPrice(productDetail.getProductPrice());
                cartItem.setQuantity(Integer.parseInt(cartItemModel.getQuantity()));
                int result = cartDao.updateCartItem(cartItem);
                if (result <= 0) {
                    return responseBuilder.buildErrorResponse(messageService.getMessage("cart.add.failure", new Object[]{productDetail.getProductName()}));
                }
                return responseBuilder.buildSuccessResponse(messageService.getMessage("cart.add.success", new Object[]{productDetail.getProductName()}));
            }
        }

        CartItemDto cartItem = new CartItemDto();
        cartItem.setProductId(Integer.parseInt(cartItemModel.getProductId()));
        cartItem.setQuantity(Integer.parseInt(cartItemModel.getQuantity()));
        cartItem.setUnitPrice(productDetail.getProductPrice());
        cartItem.setCustomerId(Integer.parseInt(cartItemModel.getCustomerId()));
        int result = cartDao.addCartItem(cartItem);
        if (result <= 0) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("cart.add.failure", new Object[]{productDetail.getProductName()}));
        }
        return responseBuilder.buildSuccessResponse(messageService.getMessage("cart.add.success", new Object[]{productDetail.getProductName()}));

    }

    @Override
    public GenericResponse removeCartItem(String cartItemId) {
        if (!Validator.isNumeric(cartItemId)) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("cart.id.not.valid"));
        }
        CartItemDto cartItem = cartDao.getCartItem(Integer.parseInt(cartItemId));
        if (cartItem == null) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("cart.item.not.found", new Object[]{cartItemId}));
        }
        if (cartDao.removeCartItem(cartItem.getCartItemId()) <= 0) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("cart.remove.failure"));
        }
        return responseBuilder.buildSuccessResponse(messageService.getMessage("cart.remove.success"));
    }

}
