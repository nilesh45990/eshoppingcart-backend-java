/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.mapper;

import com.shoppingcart.demo.dto.UserDto;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author nilesh
 */
public class UserMapper implements RowMapper<UserDto> {

    @Override
    public UserDto mapRow(ResultSet rs, int i) throws SQLException {
        System.out.println("thi sis result :");
        System.out.println(rs.getString("id"));
        UserDto user = new UserDto();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setRole(rs.getString("role"));
        return user;
    }

}
