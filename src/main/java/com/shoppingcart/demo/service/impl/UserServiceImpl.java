/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.service.impl;

import com.shoppingcart.demo.dao.impl.UserDaoImpl;
import com.shoppingcart.demo.dto.UserDto;
import com.shoppingcart.demo.model.ApplicationUser;
import com.shoppingcart.demo.model.GenericResponse;
import com.shoppingcart.demo.service.UserService;
import com.shoppingcart.demo.utils.ResponseBuilder;
import com.shoppingcart.demo.utils.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author nilesh
 */
@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    UserDaoImpl userDao;
    @Autowired
    private ResponseBuilder responseBuilder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserDto user = userDao.getUserByLogin(userName);
        if (user == null) {
            throw new UsernameNotFoundException(userName);
        }
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ name ###################");
        System.out.println(user);
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
        return new User(user.getUsername(), user.getPassword(), Collections.singletonList(authority));
    }

    @Override
    public List<UserDto> getAllUser() {
        return userDao.getAllUser();
    }

    @Override
    public GenericResponse saveUser(ApplicationUser user) {
        user.setPassword(Utils.getEncodedString(user.getPassword()));
        int isSuccess = userDao.savePerson(user);
        if (isSuccess >= 1) {
            return responseBuilder.buildSuccessResponse("user is created successfully");
        } else {
            return responseBuilder.buildErrorResponse("user is not created ");
        }
    }

}
