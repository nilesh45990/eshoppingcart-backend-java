/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.service.impl;

import com.google.common.base.Enums;
import com.shoppingcart.demo.dao.CartDao;
import com.shoppingcart.demo.dao.CustomerDao;
import com.shoppingcart.demo.dao.OrderDao;
import com.shoppingcart.demo.dao.ProductDao;
import com.shoppingcart.demo.dto.CartItemDto;
import com.shoppingcart.demo.dto.CustomerDto;
import com.shoppingcart.demo.dto.OrderDto;
import com.shoppingcart.demo.dto.OrderReportDto;
import com.shoppingcart.demo.dto.ProductDto;
import com.shoppingcart.demo.model.GenericResponse;
import com.shoppingcart.demo.model.OrderModel;
import com.shoppingcart.demo.service.MessageService;
import com.shoppingcart.demo.service.OrderService;
import com.shoppingcart.demo.utils.AppEnum.OrderStages;
import com.shoppingcart.demo.utils.ResponseBuilder;
import com.shoppingcart.demo.utils.Validator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nileshkumar
 */
@Transactional
@Service
public class OrderServiceImpl implements OrderService {
    
    @Autowired
    private ResponseBuilder responseBuilder;
    @Autowired
    private MessageService messageService;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private CartDao cartDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private CustomerDao customerDao;
    
    @Override
    public GenericResponse addCustomerOrder(String customerId) {
        if (!Validator.isNumeric(customerId)) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("customer.id.not.valid"));
        }
        CustomerDto customer = customerDao.getCustomerById(Integer.parseInt(customerId));
        if (customer == null) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("customer.not.found", new Object[]{customerId}));
        }
        List<CartItemDto> cartItemList = cartDao.getCartItemsOfCustomer(customer.getCustomerId());
        if (cartItemList.isEmpty()) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("cart.empty"));
        }
        // build product list available in cart for updating its stock
        double totalAmount = 0;
        List<ProductDto> productList = new ArrayList<>();
        for (CartItemDto cartItem : cartItemList) {
            int prodId = cartItem.getProductId();
            ProductDto product = productDao.getProductById(prodId);
            if (product.getProductStock() < cartItem.getQuantity()) {
                return responseBuilder.buildErrorResponse(messageService.getMessage("stock.not.available", new Object[]{product.getProductName()}));
            }
            totalAmount += product.getProductPrice() * cartItem.getQuantity();
            productList.add(product);
        }
        // insert order detail
        String orderStatus = OrderStages.valueOf("REQUESTED").getStage();
        OrderDto orderDto = new OrderDto();
        orderDto.setCustomerId(customer.getCustomerId());
        orderDto.setTotalAmount(totalAmount);
        orderDto.setOrderStatus(orderStatus);
        int orderId = orderDao.addCustomerOrder(orderDto);
        // update cart detail with order id
        List<Map<Integer, Integer>> cartItemsList = new ArrayList<>();
        for (CartItemDto dto : cartItemList) {
            Map<Integer, Integer> cartItemMap = new HashMap<>();
            cartItemMap.put(orderId, dto.getCartItemId());
            cartItemsList.add(cartItemMap);
        }
        orderDao.updateOrderId(cartItemsList);
        // update stock of product on successful order
        int productStock;
        int orderQuantity;
        int availableStock;
        int i = 0;
        List<Map<Integer, Integer>> productsList = new ArrayList<>();
        for (CartItemDto cartItem : cartItemList) {
            ProductDto product = productList.get(i);
            productStock = product.getProductStock();
            orderQuantity = cartItem.getQuantity();
            availableStock = productStock - orderQuantity;
            Map<Integer, Integer> cartItemMap = new HashMap<>();
            cartItemMap.put(product.getProductId(), availableStock);
            productsList.add(cartItemMap);
            i++;
        }
        if (productDao.updateStock(productsList) <= 0) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("order.add.failure"));
        }
        return responseBuilder.buildSuccessResponse(messageService.getMessage("order.add.success", new Object[]{orderId}));
    }
    
    @Override
    public GenericResponse updateOrderStatus(OrderModel order) {
        List<OrderReportDto> orderList = orderDao.getOrderDetailById(order.getOrderId());
        if (orderList.isEmpty()) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("order.not.found", new Object[]{order.getOrderId()}));
        }
        String previouseStatus = orderList.get(0).getStatus();
        if (!Enums.getIfPresent(OrderStages.class, order.getOrderStatus()).isPresent()) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("order.stutus.error"));
        } else if (OrderStages.valueOf(order.getOrderStatus()).getStage().equalsIgnoreCase(OrderStages.valueOf("CANCELED").getStage())) {
            
            if (previouseStatus.equalsIgnoreCase(OrderStages.valueOf("DISPATCHED").getStage())
                    || previouseStatus.equalsIgnoreCase(OrderStages.valueOf("DELIVERED").getStage())) {
                return responseBuilder.buildErrorResponse(messageService.getMessage("order.not.cancel", new Object[]{previouseStatus}));
            } else if (previouseStatus.equalsIgnoreCase(OrderStages.valueOf("CANCELED").getStage())) {
                return responseBuilder.buildErrorResponse(messageService.getMessage("order.already.cancel"));
            }
            
        }
        String orderStatus = OrderStages.valueOf(order.getOrderStatus()).getStage();
        if (orderStatus.equalsIgnoreCase(OrderStages.valueOf("CANCELED").getStage())) {
            int productStock;
            int orderQuantity;
            int availableStock;
            List<CartItemDto> cartItemList = orderList.get(0).getCartItemList();
            List<Map<Integer, Integer>> productsList = new ArrayList<>();
            for (CartItemDto cartItem : cartItemList) {
                ProductDto product = productDao.getProductById(cartItem.getProductId());
                productStock = product.getProductStock();
                orderQuantity = cartItem.getQuantity();
                availableStock = productStock + orderQuantity;
                Map<Integer, Integer> cartItemMap = new HashMap<>();
                cartItemMap.put(product.getProductId(), availableStock);
                productsList.add(cartItemMap);
            }
            if (productDao.updateStock(productsList) <= 0) {
                return responseBuilder.buildErrorResponse(messageService.getMessage("order.add.failure"));
            }
        }
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(order.getOrderId());
        orderDto.setOrderStatus(orderStatus);
        if (orderDao.updateOrderStatus(orderDto) <= 0) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("order.status.failure"));
        }
        return responseBuilder.buildSuccessResponse(messageService.getMessage("order.status.success", new Object[]{previouseStatus, orderStatus}));
    }
    
    @Override
    public GenericResponse cancelCustomerOrder(String customerId) {
        if (!Validator.isNumeric(customerId)) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("customer.id.not.valid"));
        }
        CustomerDto customer = customerDao.getCustomerById(Integer.parseInt(customerId));
        List<CartItemDto> cartItemList = cartDao.getCartItemsOfCustomer(customer.getCustomerId());
        if (cartItemList.isEmpty()) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("cart.empty"));
        }
        // build product list available in cart for updating its stock
        double totalAmount = 0;
        for (CartItemDto cartItem : cartItemList) {
            int prodId = cartItem.getProductId();
            ProductDto product = productDao.getProductById(prodId);
            totalAmount += product.getProductPrice() * cartItem.getQuantity();
            
        }
        // insert order detail for cart item 
        String orderStatus = OrderStages.valueOf("CANCELED").getStage();
        OrderDto orderDto = new OrderDto();
        orderDto.setCustomerId(customer.getCustomerId());
        orderDto.setTotalAmount(totalAmount);
        orderDto.setOrderStatus(orderStatus);
        int orderId = orderDao.addCustomerOrder(orderDto);
        // update order id in cart item detail  
        List<Map<Integer, Integer>> cartItems = new ArrayList<>();
        for (CartItemDto dto : cartItemList) {
            Map<Integer, Integer> cartItemMap = new HashMap<>();
            cartItemMap.put(orderId, dto.getCartItemId());
            cartItems.add(cartItemMap);
        }
        if (orderDao.updateOrderId(cartItems) <= 0) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("order.cancel.failure"));
        }
        return responseBuilder.buildSuccessResponse(messageService.getMessage("order.cancel.success"));
    }
    
    @Override
    public GenericResponse getAllOrderDetail() {
        return responseBuilder.buildSuccessResponse(orderDao.getAllOrderDetail());
    }
    
    @Override
    public GenericResponse getOrderDetailById(String orderId) {
        if (!Validator.isNumeric(orderId)) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("order.id.not.valid"));
        }
        List<OrderReportDto> orderList = orderDao.getOrderDetailById(Integer.parseInt(orderId));
        if (orderList.isEmpty()) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("order.not.found", new Object[]{orderId}));
        }
        return responseBuilder.buildSuccessResponse(orderDao.getOrderDetailById(Integer.parseInt(orderId)));
    }
    
    @Override
    public GenericResponse getOrderDetailOfCustomer(String customerId) {
        if (!Validator.isNumeric(customerId)) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("customer.id.not.valid"));
        }
        CustomerDto customer = customerDao.getCustomerById(Integer.parseInt(customerId));
        if (customer == null) {
            return responseBuilder.buildErrorResponse(messageService.getMessage("customer.not.found", new Object[]{customerId}));
        }
        return responseBuilder.buildSuccessResponse(orderDao.getOrderDetailOfCustomer(customer.getCustomerId()));
    }
    
}
