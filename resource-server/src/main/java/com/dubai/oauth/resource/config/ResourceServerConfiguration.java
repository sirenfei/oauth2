/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.dubai.oauth.resource.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


/**
 * 自定义资源服务器配置
 * qdq
 *
 */
@Configuration
@EnableResourceServer
@ComponentScan(value = {"com.dubai.oauth.resource"})
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
	private final static String resourceId = "unity-client";

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and().authorizeRequests()
				.anyRequest().authenticated();
	}
	@Override
	public void configure(ResourceServerSecurityConfigurer config) {
		config.tokenServices(tokenServices());
		config.resourceId(resourceId);
//		config.tokenStore(tokenStore);
	}


	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		Resource resource = new ClassPathResource("public.txt");
		String publicKey = null;
		try {
			publicKey = inputStream2String(resource.getInputStream());
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		converter.setVerifierKey(publicKey);
		converter.setAccessTokenConverter(new CustomerAccessTokenConverter());
		return converter;
	}

	@Bean
	@Primary
	public RemoteTokenServices tokenServices() {
		final RemoteTokenServices defaultTokenServices = new RemoteTokenServices();
		defaultTokenServices.setCheckTokenEndpointUrl("http://localhost:8888/oauth/check_token");
		defaultTokenServices.setClientId("123456");
		defaultTokenServices.setClientSecret("111111111");
		defaultTokenServices.setAccessTokenConverter(accessTokenConverter());
		return defaultTokenServices;
	}

	String inputStream2String(InputStream is) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
		}
		return buffer.toString();
	}
}
