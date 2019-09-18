/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.mapper;

import com.shoppingcart.demo.dto.ProductDto;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author nileshkumar
 */
public class ProductMapper implements RowMapper<ProductDto> {

    @Override
    public ProductDto mapRow(ResultSet rs, int i) throws SQLException {
        ProductDto product = new ProductDto();
        product.setProductId(rs.getInt("product_id"));
        product.setProductName(rs.getString("product_name"));
        product.setProductPrice(rs.getDouble("product_price"));
        product.setProductStock(rs.getInt("product_stock"));
        return product;
    }

}
