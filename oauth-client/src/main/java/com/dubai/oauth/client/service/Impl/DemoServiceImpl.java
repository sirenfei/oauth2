/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.dubai.oauth.client.service.Impl;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import com.dubai.oauth.client.service.DemoService;

/**
 * .
 */
@Service("demoService")
public class DemoServiceImpl implements DemoService {
	
    protected final Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);
    
    @Autowired
    private RestOperations demoRestTemplate;
    @Autowired
    private RestOperations implictRestTemplate;

    @Value("${demo.HelloMessageURL}")
    private String demoHelloMessageURL;

    @Value("${demo.UserInfoURL}")
    private String demoUserInfoURL;

    @Override
    public String getHelloMessage() {
    	logger.debug("getHelloMessage begin,demoHelloMessageURL:{}",demoHelloMessageURL );
    	    try {
    	     	String result  = demoRestTemplate.getForObject(URI.create(demoHelloMessageURL),String.class);
    	     	return result;
    	    }
    	    catch(Exception e)
    	    {
    	    	logger.error(e.getMessage());
    	    	throw e;
    	    }
        
    }

    @Override
    public String getUserInfo() {
    	Map<String, String > urlVariables = new HashMap<String, String>();
    	urlVariables.put("username", "admin");
		demoRestTemplate.postForObject(URI.create(demoUserInfoURL), urlVariables, String.class);
        return demoRestTemplate.getForObject(URI.create(demoUserInfoURL),String.class);
    }
}
