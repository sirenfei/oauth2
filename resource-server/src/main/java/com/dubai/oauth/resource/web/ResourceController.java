/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.dubai.oauth.resource.web;

import java.security.Principal;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dubai.oauth.resource.utils.RSACoder;

/**
 * Created by qiudeqiang qiudeqiang@baidu.com on 17/5/15.
 */
@RestController
public class ResourceController {
	
	@RequestMapping("/photos")
	@ResponseBody
	public String getTrustedUserMessage(Principal principal,String username) {
		return "Hello, Trusted User" + (principal != null ? " " + principal.getName() : "");
}
	
	
    @PreAuthorize("#oauth2.hasScope('read')")
    @RequestMapping("/hello")
    public String hello(){
    	
    	
        Map<String, Object> keyMap = null;
        try {
            String result = "hello";
            keyMap = RSACoder.initKey();
            String privateKey = RSACoder.getPrivateKey(keyMap);
            String publicKey = RSACoder.getPublicKey(keyMap);
            System.out.println(publicKey);
            byte[] encodedData = RSACoder.encryptByPrivateKey(result.getBytes(), privateKey);
            // 私钥进行数据签名
            String sign = RSACoder.sign(encodedData, privateKey);
            System.out.println("sign:"+sign);
            System.out.println("data:"+ Base64.getEncoder().encodeToString(encodedData));
            return Base64.getEncoder().encodeToString(encodedData)+";"+sign;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "hello";
    }
    
    @PreAuthorize("#oauth2.hasScope('read')")
    @RequestMapping(value = "/hello2" , method = RequestMethod.GET, produces = "application/json")
    public ModelMap hello2(){
    	ModelMap map = new ModelMap();
    	map.put("hello", "hello");
    	return map;
    }
    
    
    @PreAuthorize("#oauth2.hasScope('read')")
//    @PreAuthorize("#oauth2.hasScope('read') and #oauth2.hasScope('write')")
    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public ResponseEntity authDemo(OAuth2Authentication auth) {
    	auth.eraseCredentials();
        // 获取当前用户资源
        if (auth != null) {
            Map user = (Map) auth.getPrincipal();
            return new ResponseEntity<>(new HashMap<String, Object>() {{
                put("username", user.get("user_name"));
                put("name", user.get("name"));
                put("createAt", user.get("createAt"));
                put("auth", "OK");
            }}, HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
    }
}
