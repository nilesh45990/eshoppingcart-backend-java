/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.dao.impl;

import com.shoppingcart.demo.dao.CustomerDao;
import com.shoppingcart.demo.dto.CustomerDto;
import com.shoppingcart.demo.mapper.CustomerMapper;
import java.util.HashMap;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nileshkumar
 */
@Repository
public class CustomerDaoImpl extends GenericDaoImpl<CustomerDto> implements CustomerDao {

    @Override
    public CustomerDto getCustomerById(int customerId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT CD.customer_id,CD.customer_name,CD.customer_contact_no,CD.customer_email");
        sql.append(" FROM customer_detail CD");
        sql.append(" WHERE CD.customer_id=:customer_id ");
        Map<String, Object> map = new HashMap<>();
        map.put("customer_id", customerId);
        return getById(sql.toString(), map, new CustomerMapper());
    }

}
