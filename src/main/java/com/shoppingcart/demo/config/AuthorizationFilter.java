// /*
//  * To change this license header, choose License Headers in Project Properties.
//  * To change this template file, choose Tools | Templates
//  * and open the template in the editor.
//  */
// package com.shoppingcart.demo.config;

// import com.shoppingcart.demo.utils.AppConstants;
// import io.jsonwebtoken.Jwts;
// import java.io.IOException;
// import java.util.ArrayList;
// import javax.servlet.FilterChain;
// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

// /**
//  *
//  * @author nilesh
//  */
// public class AuthorizationFilter extends BasicAuthenticationFilter {

//     public AuthorizationFilter(AuthenticationManager authenticationManager) {
//         super(authenticationManager);
//     }

//     @Override
//     protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain ch) throws IOException, ServletException {
//         String header = req.getHeader(AppConstants.HEADER_STRING);
//         if (header == null || !header.startsWith(AppConstants.TOKEN_PREFIX)) {
//             ch.doFilter(req, res);
//             return;
//         }
//         System.out.println(" this is header in main :" + header);
//         UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
//         SecurityContextHolder.getContext().setAuthentication(authentication);
//         ch.doFilter(req, res);
//     }

//     private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
//         String token = req.getHeader(AppConstants.HEADER_STRING);
//         System.out.println(" this is header " + token);
//         if (token != null) {
//             String user = Jwts.parser().setSigningKey(AppConstants.SECRET)
//                     .parseClaimsJws(token.replace(AppConstants.TOKEN_PREFIX, ""))
//                     .getBody()
//                     .getSubject();

//             if (user != null) {
//                 return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
//             }
//             return null;
//         }
//         return null;
//     }

// }
