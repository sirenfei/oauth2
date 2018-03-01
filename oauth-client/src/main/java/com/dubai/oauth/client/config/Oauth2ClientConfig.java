/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.dubai.oauth.client.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

/**
 * .
 */
@Configuration
@EnableOAuth2Client
@ComponentScan(value = {"com.dubai.oauth.client"})
@PropertySource("classpath:sparklr.properties")
public class Oauth2ClientConfig {
    @Autowired
    OAuth2ClientContext oAuth2ClientContext;

    @Value("${accessTokenUri}")
    private String accessTokenUri;

    @Value("${userAuthorizationUri}")
    private String userAuthorizationUri;
    @Value("${demo.client.id}")
    private String demoId;
    @Value("${demo.client.ClientId}")
    private String demoClientId;
    @Value("${demo.client.ClientSecret}")
    private String demoClientSercet;
    @Bean
    public OAuth2ProtectedResourceDetails demo(){
        AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
        details.setId(demoId);
        details.setClientId(demoClientId);
        details.setClientSecret(demoClientSercet);
        details.setAccessTokenUri(accessTokenUri);
        details.setUserAuthorizationUri(userAuthorizationUri);
        details.setScope(Arrays.asList("read", "write"));
        return details;
    }
    @Bean
    public OAuth2ProtectedResourceDetails sparklr() {
        ImplicitResourceDetails details = new ImplicitResourceDetails();//implicit类型
//        details.setId("sparklr/tonr");
//        details.setClientId("tonr");
//        details.setClientSecret("secret");
//        details.setAccessTokenUri(accessTokenUri);
//        details.setUserAuthorizationUri(userAuthorizationUri);
//        details.setScope(Arrays.asList("read", "write"));
        details.setId(demoId);
        details.setClientId(demoClientId);
        details.setClientSecret(demoClientSercet);
        details.setAccessTokenUri(accessTokenUri);
        details.setUserAuthorizationUri(userAuthorizationUri);
        details.setScope(Arrays.asList("read", "write"));
        return details;
    }

//    @Bean
//    public OAuth2ProtectedResourceDetails sparklrRedirect() {
//        AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();//authorizationCode类型
//        details.setId("sparklr/tonr-redirect");
//        details.setClientId("tonr-with-redirect");
//        details.setClientSecret("secret");
//        details.setAccessTokenUri(accessTokenUri);
//        details.setUserAuthorizationUri(userAuthorizationUri);
//        details.setScope(Arrays.asList("read", "write"));
//        details.setUseCurrentUri(false);
//        return details;
//    }
//
//    @Bean
//    public OAuth2ProtectedResourceDetails facebook() {
//        AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
//        details.setId("facebook");
//        details.setClientId("233668646673605");
//        details.setClientSecret("33b17e044ee6a4fa383f46ec6e28ea1d");
//        details.setAccessTokenUri("https://graph.facebook.com/oauth/access_token");
//        details.setUserAuthorizationUri("https://www.facebook.com/dialog/oauth");
//        details.setTokenName("oauth_token");
//        details.setAuthenticationScheme(AuthenticationScheme.query);
//        details.setClientAuthenticationScheme(AuthenticationScheme.form);
//        return details;
//    }
//
//    @Bean
//    public OAuth2ProtectedResourceDetails trusted() {
//        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
//        details.setId("sparklr/trusted");
//        details.setClientId("my-client-with-registered-redirect");
//        details.setAccessTokenUri(accessTokenUri);
//        details.setScope(Arrays.asList("trust"));
//        return details;
//    }

    @Bean(name = "demoRestTemplate")
    public OAuth2RestTemplate demoRestTemplate(){
        return new OAuth2RestTemplate(demo(), oAuth2ClientContext);
    }
    @Bean(name = "implictRestTemplate")
    public OAuth2RestTemplate implictRestTemplate(){
        return new OAuth2RestTemplate(sparklr(), oAuth2ClientContext);
    }
//    @Bean
//    public OAuth2RestTemplate sparklrRestTemplate() {
//        return new OAuth2RestTemplate(sparklr(), oAuth2ClientContext);
//    }
//
//    @Bean
//    public OAuth2RestTemplate sparklrRedirectRestTemplate() {
//        return new OAuth2RestTemplate(sparklrRedirect(), oAuth2ClientContext);
//    }
//
//    @Bean
//    public OAuth2RestTemplate trustedClientRestTemplate() {
//        return new OAuth2RestTemplate(trusted(), new DefaultOAuth2ClientContext());
//    }

//    @Bean
//    public OAuth2ClientAuthenticationProcessingFilter oAuth2ClientAuthenticationProcessingFilter(){
//        OAuth2ClientAuthenticationProcessingFilter githubFilter = new OAuth2ClientAuthenticationProcessingFilter("/login");
//        OAuth2RestTemplate githubTemplate = new OAuth2RestTemplate(github(), oauth2ClientContext);
//        githubFilter.setRestTemplate(githubTemplate);
//        UserInfoTokenServices tokenServices = new UserInfoTokenServices(githubResource().getUserInfoUri(), github().getClientId());
//        tokenServices.setRestTemplate(githubTemplate);
//        githubFilter.setTokenServices(tokenServices);
//    }
}
