package com.faithbj.oauth.as.config;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import com.faithbj.oauth.as.oauth.CustomClientDetailsService;
import com.faithbj.oauth.as.oauth.CustomTokenEnhancer;
import com.faithbj.oauth.as.oauth.CustomUserApprovalHandler;
import com.faithbj.oauth.as.oauth.CustomerAccessTokenConverter;

/**
 * 
 * @author xueyongfei01
 *
 */
@Configuration
@PropertySource("classpath:config.properties")
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;
    
    @Autowired
    @Qualifier("customerUserDetailsService")
    public UserDetailsService customerUserDetailsService;;
    @Autowired
    @Qualifier("customClientDetailsService")
    private CustomClientDetailsService customClientDetailsService;
    
    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(customClientDetailsService);
    }
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
         endpoints.userDetailsService(customerUserDetailsService)
         .authorizationCodeServices(authorizationCodeServices())
         .authenticationManager(authenticationManager)
//         .userApprovalHandler(oauthUserApprovalHandler())
//         .tokenEnhancer(tokenEnhancer())
         .accessTokenConverter(accessTokenConverter());
         endpoints.tokenServices(tokenServices() );
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        // oauthServer.realm("sparklr2/client");
        oauthServer.checkTokenAccess("permitAll()");
        oauthServer.allowFormAuthenticationForClients();
    }
    
    

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyStoreKeyFactory keyStoreKeyFactory =
                new KeyStoreKeyFactory(new ClassPathResource("mytest.jks"), "mypass".toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mytest"));

        converter.setAccessTokenConverter(new CustomerAccessTokenConverter());
        return converter;
    }
    @Bean
    public JdbcTokenStore tokenStore() {
          return new JdbcTokenStore(dataSource);
//        return new JwtTokenStore(accessTokenConverter());
    }
    @Bean
    public JwtTokenStore jwtTokenStore() {
       return new JwtTokenStore(accessTokenConverter());
    }
    
    
    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setClientDetailsService(customClientDetailsService);
        defaultTokenServices.setTokenEnhancer(accessTokenConverter());
//        defaultTokenServices.setTokenEnhancer(tokenEnhancerChain());
        defaultTokenServices.setAccessTokenValiditySeconds(600);
        return defaultTokenServices;
    }

    

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }


    @Bean(name = "tokenEnhancerChain")
    public TokenEnhancerChain tokenEnhancerChain() {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));
        return tokenEnhancerChain;
    }



    @Bean
    protected AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }
    
    
    @Bean
    public ApprovalStore approvalStore() throws Exception {
        TokenApprovalStore store = new TokenApprovalStore();
        store.setTokenStore(tokenStore());
        return store;
    }
//
//    @Bean
//    @Lazy
//    @Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
//    public CustomApprovalUserApprovalHandler userApprovalHandler() throws Exception {
//        CustomApprovalUserApprovalHandler handler = new CustomApprovalUserApprovalHandler();
//        handler.setApprovalStore(approvalStore());
//        handler.setRequestFactory(new DefaultOAuth2RequestFactory(customClientDetailsService));
//        handler.setClientDetailsService(customClientDetailsService);
//        handler.setUseApprovalStore(true);
//        return handler;
//    }

    
    @Bean
    public TokenStoreUserApprovalHandler oauthUserApprovalHandler() {
        TokenStoreUserApprovalHandler oauthUserApprovalHandler = new CustomUserApprovalHandler();
        oauthUserApprovalHandler.setTokenStore(jwtTokenStore());
        oauthUserApprovalHandler.setClientDetailsService(customClientDetailsService);
        oauthUserApprovalHandler.setRequestFactory(new DefaultOAuth2RequestFactory(customClientDetailsService));
        return oauthUserApprovalHandler;
    }
    
}
