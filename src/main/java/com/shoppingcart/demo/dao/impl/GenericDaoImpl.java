/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 *
 * @author nileshkumar
 * @param <T>
 */
public abstract class GenericDaoImpl<T> {

    @Autowired
    protected NamedParameterJdbcTemplate namedJdbcTemplate;

    List<T> getAll(String sql, RowMapper<T> mapper) {
        return (List<T>) namedJdbcTemplate.query(sql, mapper);
    }

    List<T> getAllByParam(String sql, Map map, RowMapper<T> mapper) {
        SqlParameterSource param = new MapSqlParameterSource(map);
        return (List<T>) namedJdbcTemplate.query(sql, param, mapper);
    }

    T getById(String sql, Map map, RowMapper<T> mapper) {
        SqlParameterSource param = new MapSqlParameterSource(map);
        List<T> list = namedJdbcTemplate.query(sql, param, mapper);
        System.out.println("########");
        System.out.println(list);
        return list.isEmpty() ? null : (T) list.get(0);
    }

    int save(String sql, Map map) {
        SqlParameterSource param = new MapSqlParameterSource(map);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedJdbcTemplate.update(sql, param, keyHolder);
        return keyHolder.getKey().intValue();
    }

    int updateorDelete(String sql, Map map) {
        SqlParameterSource param = new MapSqlParameterSource(map);
        return namedJdbcTemplate.update(sql, param);
    }

}
