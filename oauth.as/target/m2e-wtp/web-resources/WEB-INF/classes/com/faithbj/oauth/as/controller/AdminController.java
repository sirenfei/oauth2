package com.faithbj.oauth.as.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.faithbj.oauth.as.utils.Jackson2Mapper;

/**
 * Controller for resetting the token store for testing purposes.
 * 
 * @author Dave Syer
 */
@Controller
public class AdminController {
	
    
    @RequestMapping("user_info")
    @ResponseBody
    public String userInfo() throws Exception {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Object principal = authentication.getPrincipal();

        if (authentication instanceof OAuth2Authentication &&
                (principal instanceof String
                         || principal instanceof org.springframework.security.core.userdetails.User)) {
                return Jackson2Mapper.write(authentication);
        } 
        else if(StringUtils.equals(principal.toString(), "anonymousUser"))
        {
            return "anonymousUser";
        }
        else {
            return Jackson2Mapper.write(principal);
        }
    }


}
