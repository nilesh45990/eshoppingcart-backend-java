/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.dao.impl;

import com.shoppingcart.demo.dto.UserDto;
import com.shoppingcart.demo.mapper.UserMapper;
import com.shoppingcart.demo.model.ApplicationUser;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nilesh
 */
@Repository
@Transactional
public class UserDaoImpl extends GenericDaoImpl<UserDto> {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public int savePerson(ApplicationUser user) {
        Serializable result = getSession().save(user);
        return ((Integer) result);
    }

    public UserDto getUserByLogin(String userName) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ul.username,ul.password,ul.id,r.role_name role\n");
        sql.append(" FROM user_login ul \n");
        sql.append("inner join app_role r on r.id=ul.role_id \n");
        sql.append("WHERE ul.username=:username ");
        Map<String, Object> map = new HashMap<>();
        map.put("username", userName);
        return getById(sql.toString(), map, new UserMapper());
    }

    public List<UserDto> getAllUser() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ul.username,ul.password,ul.id,r.role_name role\n");
        sql.append(" FROM user_login ul \n");
        sql.append("inner join app_role r on r.id=ul.role_id \n");
        return getAll(sql.toString(), new UserMapper());
    }

}
