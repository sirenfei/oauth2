package com.dubai.oauth.resource.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

public class CustomerAccessTokenConverter extends DefaultAccessTokenConverter {
    protected final Logger logger = LoggerFactory.getLogger(CustomerAccessTokenConverter.class);
    
    
    public CustomerAccessTokenConverter() {
        super.setUserTokenConverter(new CustomerUserAuthenticationConverter());
    }

    private class CustomerUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

        @Override
        public Map<String, Object> convertUserAuthentication(Authentication authentication) {
            ConcurrentHashMap<String, Object> response = new ConcurrentHashMap<>();
            response.put("username", authentication.getName());
//            TbUsers user = (TbUsers) authentication.getPrincipal();
//            logger.info("----------------------------------DefaultUserAuthenticationConverter:{}",response);
//            response.put("email", user.getEmail());
//            response.put("mobilePhone", user.getMobilePhone());
            if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
                response.put("authorities", AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
            }
            return response;
        }
    }

}
