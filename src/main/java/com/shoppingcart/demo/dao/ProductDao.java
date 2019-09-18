/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.dao;

import com.shoppingcart.demo.dto.ProductDto;
import com.shoppingcart.demo.dto.ProductReportDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nileshkumar
 */
public interface ProductDao {

    List<ProductDto> getAllProducts();
    
    ProductReportDto getProductTransactionList(int productId);
    
    List<ProductReportDto> getAllProductsTransactionList();

    ProductDto getProductById(int productId);

    int updateStock(List<Map<Integer, Integer>> cartItemMap);
}
