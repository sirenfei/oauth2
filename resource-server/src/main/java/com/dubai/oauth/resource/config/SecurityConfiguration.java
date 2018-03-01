/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.dubai.oauth.resource.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("customerUserDetailsService")
    public UserDetailsService customerUserDetailsService;
    
    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customerUserDetailsService).passwordEncoder(new Md5PasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**", "/images/**", "/oauth/uncache_approvals", "/oauth/cache_approvals");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	http
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
	.and()
		.authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers("/logout").permitAll()
        .anyRequest().hasRole("USER")
//        .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and().csrf().disable()
//        .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
        .logout().logoutUrl("/logout").logoutSuccessUrl("/");
    }
}
