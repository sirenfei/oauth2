/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.dubai.oauth.client.web;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dubai.oauth.client.Utils.RSACoder;
import com.dubai.oauth.client.service.DemoService;

/**
 * .
 */
@RestController
public class DemoController {
	
    protected final Logger logger = LoggerFactory.getLogger(DemoController.class);
    
    @Autowired
    private DemoService demoService;
//    @Value("${rsa.publicKey}")
    private String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCNsV702M61DL/iKtiGkJ/Aus+l2QlS/JBQqRR/qqLWcLx9XjHx0/Ov1AEA0dYIUPuICLjzKdfmXU2n6bcLpi0R0m3aRMeLuNrc1ANYqEIELwxD0NrreiRVtTqu34nYd9N9VSqBtGPAE/By1JjkDgZVapbZjl+4WVmC2sKKl7MOkwIDAQAB";
    
    
    @RequestMapping(value = "/demo")
    public String demo() {
        String result = demoService.getHelloMessage();
        if(StringUtils.endsWithIgnoreCase("error", result))
        {
        	    return "error,no login";
        }
        String decodeData = result.split(";")[0];
        String sign = result.split(";")[1];
        logger.debug("_______________decodeData:{},sign:{}",decodeData ,sign);
        
        try {
            boolean flag = RSACoder.verify(Base64.getDecoder().decode(decodeData), publicKey, sign);
            if (flag) {
                return new String(RSACoder.decryptByPublicKey(Base64.getDecoder().decode(decodeData),
                        publicKey));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }
    @RequestMapping(value = "/me")
    public String user(OAuth2Authentication auth) {
//        Map<String, Object> map = new LinkedHashMap<>();
//        map.put("name", auth.getName());
//        map.put("id", ((Map) auth.getUserAuthentication().getDetails()).get("id"));
        return demoService.getUserInfo();
    }
}
