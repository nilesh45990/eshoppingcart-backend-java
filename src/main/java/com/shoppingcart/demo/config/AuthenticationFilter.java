// /*
//  * To change this license header, choose License Headers in Project Properties.
//  * To change this template file, choose Tools | Templates
//  * and open the template in the editor.
//  */
// package com.shoppingcart.demo.config;

// import io.jsonwebtoken.Jwts;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.shoppingcart.demo.exception.UserNotFoundException;
// import com.shoppingcart.demo.model.ApplicationUser;
// import com.shoppingcart.demo.utils.AppConstants;
// import io.jsonwebtoken.SignatureAlgorithm;
// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.Date;
// import javax.servlet.FilterChain;
// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// /**
//  *
//  * @author nilesh
//  */
// public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    
//     private final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
//     AuthenticationManager authManager;
    
//     public AuthenticationFilter(AuthenticationManager authenticationManager) {
//         this.authManager = authenticationManager;
//     }
    
//     @Override
//     public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
//         try {
//             logger.info("yeah entered in attempt authentication");
//             ApplicationUser user = new ObjectMapper().readValue(req.getInputStream(), ApplicationUser.class);
//             return authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new ArrayList<>()));
//         } catch (Exception e) {
//             logger.error("attempt login :", e);
//             throw new UserNotFoundException("User detail not found");
//         }
// //        return null;
//     }
    
//     @Override
//     protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,
//             FilterChain chain, Authentication auth) throws IOException, ServletException {
//         String token = Jwts.builder()
//                 .setSubject(((User) auth.getPrincipal()).getUsername())
//                 .setExpiration(new Date(System.currentTimeMillis() + AppConstants.EXPIRATION_TIME))
//                 .signWith(SignatureAlgorithm.HS512, AppConstants.SECRET)
//                 .compact();
//         logger.info(" this is tocken:" + token);
//         res.setHeader(AppConstants.HEADER_STRING, AppConstants.TOKEN_PREFIX + token);
//         res.setContentType("application/json");
//         res.setCharacterEncoding("UTF-8");
//         res.getWriter().write("{\"" + AppConstants.HEADER_STRING + "\":\"" + AppConstants.TOKEN_PREFIX + token + "\"}");
//     }
    
// }
