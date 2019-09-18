/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shoppingcart.demo.controller;

import com.shoppingcart.demo.dto.UserDto;
import com.shoppingcart.demo.model.ApplicationUser;
import com.shoppingcart.demo.model.GenericResponse;
import com.shoppingcart.demo.service.UserService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author nilesh
 */
@RestController
@RequestMapping("/user")
public class AuthenticationController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "Get all products transaction detail")
    @GetMapping(value = "/get-all")
    public List<UserDto> getAllUser() {
        return userService.getAllUser();
    }

    @ApiOperation(value = "Get all products transaction detail")
    @PostMapping(value = "/")
    public GenericResponse saveUser(@RequestBody ApplicationUser user) {
        return userService.saveUser(user);
    }

}
