/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.dao.impl;

import com.shoppingcart.demo.dao.ProductDao;
import com.shoppingcart.demo.dto.OrderDto;
import com.shoppingcart.demo.dto.ProductDto;
import com.shoppingcart.demo.dto.ProductReportDto;
import com.shoppingcart.demo.mapper.ProductMapper;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nileshkumar
 */
@Repository
public class ProductDaoImpl extends GenericDaoImpl<ProductDto> implements ProductDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
//    
    @Override
    public List<ProductDto> getAllProducts() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM product_detail order by product_name");
        return getAll(sql.toString(), new ProductMapper());
    }

    @Override
    public List<ProductReportDto> getAllProductsTransactionList() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT PD.product_id,PD.product_name,PD.product_stock,OD.order_id,CUD.customer_id,CUD.customer_name,CUD.customer_contact_no, ");
        sql.append(" CUD.customer_email,CD.order_quantity,CD.unit_price,(CD.order_quantity*CD.unit_price)total_amount,DATE_FORMAT(OD.order_date,'%d-%m-%Y')order_date, ");
        sql.append(" OD.order_status ");
        sql.append(" FROM product_detail PD ");
        sql.append(" INNER JOIN cart_detail CD ON CD.product_id=PD.product_id ");
        sql.append(" INNER JOIN customer_detail CUD ON CUD.customer_id = CD.customer_id ");
        sql.append(" INNER JOIN order_detail OD ON OD.order_id=CD.order_id ");
        sql.append(" ORDER BY product_name,order_id desc");
        
        return namedJdbcTemplate.query(sql.toString(), new ResultSetExtractor<List<ProductReportDto>>() {
            List<ProductReportDto> productReportList = new ArrayList<>();

            @Override
            public List<ProductReportDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
                ProductReportDto productReportDto = new ProductReportDto();
                List<OrderDto> orderList = new ArrayList<>();
                while (rs.next()) {
                    if (productReportDto.getProductId() == rs.getInt("product_id")) {
                        OrderDto orderDto = buildOrderDto(rs);
                        orderList.add(orderDto);
                    } else {
                        if (!orderList.isEmpty()) {
                            productReportDto.setOrderList(orderList);
                            orderList = new ArrayList<>();
                        }
                        productReportDto = buildProductReportDto(rs);
                        OrderDto orderDto = buildOrderDto(rs);
                        orderList.add(orderDto);
                        productReportDto.setOrderList(orderList);
                        productReportList.add(productReportDto);
                    }
                }
                return productReportList;
            }
        });
    }

    @Override
    public ProductReportDto getProductTransactionList(int productId) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT PD.product_id,PD.product_name,PD.product_stock,OD.order_id,CUD.customer_id,CUD.customer_name,CUD.customer_contact_no, ");
        sql.append("CUD.customer_email,CD.order_quantity,CD.unit_price,(CD.order_quantity*CD.unit_price)total_amount,DATE_FORMAT(OD.order_date,'%d-%m-%Y')order_date,  ");
        sql.append(" OD.order_status ");
        sql.append("FROM product_detail PD ");
        sql.append("INNER JOIN cart_detail CD ON CD.product_id=PD.product_id ");
        sql.append("INNER JOIN customer_detail CUD ON CUD.customer_id = CD.customer_id ");
        sql.append("INNER JOIN order_detail OD ON OD.order_id=CD.order_id ");
        sql.append("WHERE PD.product_id = :product_id  ORDER BY order_id desc");
        Map map = new HashMap();
        map.put("product_id", productId);
        SqlParameterSource param = new MapSqlParameterSource(map);
        return namedJdbcTemplate.query(sql.toString(),param, new ResultSetExtractor<ProductReportDto>() {
            @Override
            public ProductReportDto extractData(ResultSet rs) throws SQLException, DataAccessException {
                ProductReportDto productReportDto = new ProductReportDto();
                List<OrderDto> orderList = new ArrayList<>();
                while (rs.next()) {
                    if (productReportDto.getProductId() == rs.getInt("product_id")) {
                        OrderDto orderDto = buildOrderDto(rs);
                        orderList.add(orderDto);
                    } else {
                        if (!orderList.isEmpty()) {
                            productReportDto.setOrderList(orderList);
                            orderList = new ArrayList<>();
                        }
                        productReportDto = buildProductReportDto(rs);
                        OrderDto orderDto = buildOrderDto(rs);
                        orderList.add(orderDto);
                        productReportDto.setOrderList(orderList);
                    }
                }
                return productReportDto;
            }
        });
    }

    @Override
    public ProductDto getProductById(int productId) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM product_detail WHERE product_id=:product_id ");
        Map<String, Object> map = new HashMap<>();
        map.put("product_id", productId);
        return getById(sql.toString(), map, new ProductMapper());
    }

    @Override
    public int updateStock(final List<Map<Integer, Integer>> cartItemList) {

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE product_detail SET product_stock=? WHERE product_id=?");
        int[] res = jdbcTemplate.batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Map<Integer, Integer> cartItemMap = cartItemList.get(i);
                for (Map.Entry<Integer, Integer> entry : cartItemMap.entrySet()) {
                    ps.setInt(2, entry.getKey());
                    ps.setInt(1, entry.getValue());
                }
            }

            @Override
            public int getBatchSize() {
                return cartItemList.size();
            }
        });
        return res.length;
    }

    private ProductReportDto buildProductReportDto(ResultSet rs) throws SQLException {
        ProductReportDto productReportDto = new ProductReportDto();
        productReportDto.setProductId(rs.getInt("product_id"));
        productReportDto.setProductName(rs.getString("product_name"));
        productReportDto.setProductStock(rs.getInt("product_stock"));
        return productReportDto;
    }

    private OrderDto buildOrderDto(ResultSet rs) throws SQLException {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(rs.getInt("order_id"));
        orderDto.setCustomerId(rs.getInt("customer_id"));
        orderDto.setCustomerName(rs.getString("customer_name"));
        orderDto.setOrderQuantity(rs.getInt("order_quantity"));
        orderDto.setUnitPrice(rs.getInt("unit_price"));
        orderDto.setTotalAmount(rs.getDouble("total_amount"));
        orderDto.setOrderDate(rs.getString("order_date"));
        orderDto.setOrderStatus(rs.getString("order_status"));
        return orderDto;

    }
}
