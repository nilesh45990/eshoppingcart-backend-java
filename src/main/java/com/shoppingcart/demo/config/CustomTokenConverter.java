package com.shoppingcart.demo.config;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.shoppingcart.demo.dao.impl.UserDaoImpl;
import com.shoppingcart.demo.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

@Configuration
public class CustomTokenConverter implements TokenEnhancer {

    @Autowired
    UserDaoImpl userDao;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo = new HashMap<>();
        User user = (User) authentication.getPrincipal();
        UserDto userDto = userDao.getUserByLogin(user.getUsername());
        additionalInfo.put("userId", userDto.getId());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}