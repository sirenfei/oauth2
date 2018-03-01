/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.dubai.oauth.client.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dubai.oauth.client.Utils.CustomHttp;

/**
 * .
 */
@Controller
public class ImplictController {
	
    protected final Logger logger = LoggerFactory.getLogger(ImplictController.class);
    
    @Autowired
    private CustomHttp customHttp;
    
    @Value("${hello2URL}")
    private String hello2URL;
    

    @RequestMapping(value = "/implicitdemo")
    @ResponseBody
    public String demo(HttpServletRequest request) {
    	String authorization = request.getHeader("Authorization");
    	String accept = request.getHeader("Accept");
    	        System.out.println(hello2URL);
    	        Header[] headers = {new BasicHeader("Authorization", authorization), new BasicHeader("Accept", accept)};
    	        String result  = customHttp.manualGet(hello2URL, headers);
	     	return result;
    }
    
    
    @RequestMapping(value = "/index")
    public String index() {
    	
    	
    	return "index";
    }


}
