package com.shoppingcart.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

// import com.shoppingcart.demo.utils.AppConstants;
import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final Logger logger = LoggerFactory.getLogger(ResourceServerConfig.class);
    @Autowired
    private ResourceServerTokenServices tokenServices;

    @Value("${security.jwt.resource-ids}")
    private String resourceIds;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(resourceIds).tokenServices(tokenServices);
    }

    @Override
    public void configure(HttpSecurity http) {
        try {
            logger.info("################## filter called in resource server##################");
            http.requestMatchers().and().authorizeRequests()
                    .antMatchers("/products/**","/swagger-ui.html/**", "/swagger-resources/**", "/v2/api-docs/**").permitAll()
                    // .antMatchers("/product/**","cart/**").authenticated();
                    .anyRequest().authenticated();
        } catch (Exception ex) {
            System.out
                    .println("this is error handling in resource server ############################################");

        }
    }

}
