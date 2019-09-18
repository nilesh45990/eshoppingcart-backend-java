/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.service;

import com.shoppingcart.demo.dto.UserDto;
import com.shoppingcart.demo.model.ApplicationUser;
import com.shoppingcart.demo.model.GenericResponse;
import java.util.List;

/**
 *
 * @author nilesh
 */
public interface UserService {

    GenericResponse saveUser(ApplicationUser user);

    List<UserDto> getAllUser();

}
