/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.mapper;

import com.shoppingcart.demo.dto.CustomerDto;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author nileshkumar
 */
public class CustomerMapper implements RowMapper<CustomerDto> {

    @Override
    public CustomerDto mapRow(ResultSet rs, int i) throws SQLException {
        CustomerDto customer = new CustomerDto();
        customer.setCustomerId(rs.getInt("customer_id"));
        customer.setName(rs.getString("customer_name"));
        customer.setContactNo(rs.getString("customer_contact_no"));
        customer.setEmailId(rs.getString("customer_email"));
        return customer;
    }
}
