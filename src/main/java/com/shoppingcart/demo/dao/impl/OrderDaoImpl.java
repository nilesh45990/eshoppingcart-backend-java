/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.dao.impl;

import com.shoppingcart.demo.dao.OrderDao;
import com.shoppingcart.demo.dto.CartItemDto;
import com.shoppingcart.demo.dto.OrderReportDto;
import com.shoppingcart.demo.dto.OrderDto;
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
public class OrderDaoImpl extends GenericDaoImpl<OrderDto> implements OrderDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public int addCustomerOrder(OrderDto orderDto) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO order_detail(customer_id,order_status,total_amount) VALUES(:customer_id,:order_status,:total_amount)");
        Map<String, Object> map = new HashMap<>();
        map.put("customer_id", orderDto.getCustomerId());
        map.put("order_status", orderDto.getOrderStatus());
        map.put("total_amount", orderDto.getTotalAmount());
        return save(sql.toString(), map);
    }
    
    @Override
    public int updateOrderId(final List<Map<Integer, Integer>> cartItemList) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE cart_detail SET order_id=? WHERE cart_item_id=?");
        int[] res = jdbcTemplate.batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Map<Integer, Integer> cartItemMap = cartItemList.get(i);
                for (Map.Entry<Integer, Integer> entry : cartItemMap.entrySet()) {
                    ps.setInt(1, entry.getKey());
                    ps.setInt(2, entry.getValue());
                }
            }
            
            @Override
            public int getBatchSize() {
                return cartItemList.size();
            }
        });
        return res.length;
    }
    
    @Override
    public int updateOrderStatus(OrderDto orderDto) {
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE order_detail SET order_status=:order_status WHERE order_id=:order_id ");
        Map map = new HashMap();
        map.put("order_id", orderDto.getOrderId());
        map.put("order_status", orderDto.getOrderStatus());
        return updateorDelete(sql.toString(), map);
    }
    
    @Override
    public List<OrderReportDto> getAllOrderDetail() {
        StringBuilder sql = new StringBuilder();
        sql.setLength(0);
        sql.append(" SELECT OD.order_id,PD.product_id,PD.product_name,CUD.customer_id,CUD.customer_name,CD.cart_item_id,CD.order_quantity,CD.unit_price, ");
        sql.append(" OD.total_amount ,DATE_FORMAT(OD.order_date,'%d-%m-%Y')order_date, ");
        sql.append(" OD.order_status ");
        sql.append(" FROM order_detail OD ");
        sql.append(" INNER JOIN cart_detail CD ON CD.order_id=OD.order_id ");
        sql.append(" INNER JOIN product_detail PD ON PD.product_id = CD.product_id ");
        sql.append(" INNER JOIN customer_detail CUD ON CUD.customer_id= CD.customer_id ");
        sql.append(" ORDER BY product_name,order_id desc");
        return processOrderList(sql.toString(), null);
    }
    
    @Override
    public List<OrderReportDto> getOrderDetailById(int orderId) {
        StringBuilder sql = new StringBuilder();
        sql.setLength(0);
        sql.append(" SELECT OD.order_id,PD.product_id,PD.product_name,CUD.customer_id,CUD.customer_name,CD.cart_item_id,CD.order_quantity,CD.unit_price, ");
        sql.append(" OD.total_amount ,DATE_FORMAT(OD.order_date,'%d-%m-%Y')order_date, ");
        sql.append(" OD.order_status ");
        sql.append(" FROM order_detail OD ");
        sql.append(" INNER JOIN cart_detail CD ON CD.order_id=OD.order_id ");
        sql.append(" INNER JOIN product_detail PD ON PD.product_id = CD.product_id ");
        sql.append(" INNER JOIN customer_detail CUD ON CUD.customer_id= CD.customer_id ");
        sql.append(" WHERE OD.order_id=:order_id ");
        Map map = new HashMap();
        map.put("order_id", orderId);
        return processOrderList(sql.toString(), map);
    }
    
    @Override
    public List<OrderReportDto> getOrderDetailOfCustomer(int customerId) {
        StringBuilder sql = new StringBuilder();
        sql.setLength(0);
        sql.append(" SELECT OD.order_id,PD.product_id,PD.product_name,CUD.customer_id,CUD.customer_name,CD.cart_item_id,CD.order_quantity,CD.unit_price, ");
        sql.append(" OD.total_amount ,DATE_FORMAT(OD.order_date,'%d-%m-%Y')order_date, ");
        sql.append(" OD.order_status ");
        sql.append(" FROM order_detail OD ");
        sql.append(" INNER JOIN cart_detail CD ON CD.order_id=OD.order_id ");
        sql.append(" INNER JOIN product_detail PD ON PD.product_id = CD.product_id ");
        sql.append(" INNER JOIN customer_detail CUD ON CUD.customer_id= CD.customer_id ");
        sql.append(" WHERE CUD.customer_id=:customer_id ");
        sql.append(" ORDER BY order_id desc ");
        Map map = new HashMap();
        map.put("customer_id", customerId);
        return processOrderList(sql.toString(), map);
    }
    
    private List<OrderReportDto> processOrderList(String sql, Map map) {
        SqlParameterSource param = new MapSqlParameterSource(map);
        return namedJdbcTemplate.query(sql, param, new ResultSetExtractor<List<OrderReportDto>>() {
            List<OrderReportDto> orderDetailList = new ArrayList<>();
            
            @Override
            public List<OrderReportDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
                OrderReportDto orderDetail = new OrderReportDto();
                List<CartItemDto> cartItemList = new ArrayList<>();
                while (rs.next()) {
                    if (orderDetail.getOrderId() == rs.getInt("order_id")) {
                        CartItemDto cartItemDto = builCartItemDto(rs);
                        cartItemList.add(cartItemDto);
                    } else {
                        if (!cartItemList.isEmpty()) {
                            orderDetail.setCartItemList(cartItemList);
                            cartItemList = new ArrayList<>();
                        }
                        orderDetail = buildOrderReportDto(rs);
                        CartItemDto cartItemDto = builCartItemDto(rs);
                        cartItemList.add(cartItemDto);
                        orderDetail.setCartItemList(cartItemList);
                        orderDetailList.add(orderDetail);
                    }
                }
                return orderDetailList;
            }
        });
    }
    
    private OrderReportDto buildOrderReportDto(ResultSet rs) throws SQLException {
        OrderReportDto orderDetail = new OrderReportDto();
        orderDetail.setOrderId(rs.getInt("order_id"));
        orderDetail.setCustomerName(rs.getString("customer_name"));
        orderDetail.setTotalAmount(rs.getDouble("total_amount"));
        orderDetail.setOrderDate(rs.getString("order_date"));
        orderDetail.setStatus(rs.getString("order_status"));
        return orderDetail;
    }
    
    private CartItemDto builCartItemDto(ResultSet rs) throws SQLException {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setCartItemId(rs.getInt("cart_item_id"));
        cartItemDto.setCustomerId(rs.getInt("customer_id"));
        cartItemDto.setQuantity(rs.getInt("order_quantity"));
        cartItemDto.setProductId(rs.getInt("product_id"));
        cartItemDto.setProductName(rs.getString("product_name"));
        cartItemDto.setUnitPrice(rs.getDouble("unit_price"));
        cartItemDto.setTotalAmount(rs.getInt("order_quantity") * rs.getDouble("unit_price"));
        return cartItemDto;
    }
    
}
