package com.faithbj.oauth.as.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

//@Controller  
//@SessionAttributes("authorizationRequest")  
public class OAuth2ApprovalController {  
  
	 @RequestMapping("/oauth/confirm_access")  
	  public String getAccessConfirmation(Map<String, Object> model, HttpServletRequest request)  
	      throws Exception {  
	  
	    return "login222";  
	  }  
  
}  