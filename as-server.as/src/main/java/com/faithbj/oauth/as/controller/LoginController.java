package com.faithbj.oauth.as.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for resetting the token store for testing purposes.
 * 
 * @author Dave Syer
 */
@Controller
public class LoginController {
    protected final Logger logger = LoggerFactory.getLogger(LoginController.class);


	@RequestMapping("/login")
	public String loginPage()  {
		
		logger.info("kkkkkkkkkkkkkkk");
		
		return "login";
	}
	
	@RequestMapping("/loginFail")
	public String loginFail()  {
	    logger.info("kkkkkkkkkkkkkkk");
	    return "login";
	}
	
	@RequestMapping("/loginSuccess")
	public String loginSuccessPage(ModelMap model)  {
	    logger.info("kkkkkkkkkkkkkkk");
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    model.put("username", authentication.getName());
	    return "loginSuccess";
	}
	@RequestMapping("/")
	public String welcome(ModelMap model)  {
	    logger.info("kkkkkkkkkkkkkkk");
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    model.put("username", authentication.getName());
	    return "loginSuccess";
	}
	

}
