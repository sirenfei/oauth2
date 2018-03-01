package com.faithbj.oauth.as.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.event.LoggerListener;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.faithbj.oauth.as.oauth.CustomerUserDetailsService;
import com.octo.captcha.service.CaptchaService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
/*    @Autowired  
    private DataSource dataSource; */
    
    @Autowired
    @Qualifier("customerUserDetailsService")
    public UserDetailsService customerUserDetailsService;
    
    @Autowired
    @Qualifier("captchaService")
	private CaptchaService captchaService;
	
    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customerUserDetailsService).passwordEncoder(new Md5PasswordEncoder());
    }
    
    @Bean(name = "authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    // 不拦截规则
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/images/**","/js/**","/css/**", "/font/**", "/oauth/uncache_approvals", "/oauth/cache_approvals","/showcaptcha");
    }

    
    @Bean
    CustomAuthenticationFilter customAuthenticationFilter() {
    		CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter();
    		try {
				customAuthenticationFilter.setAuthenticationManager(super.authenticationManagerBean());
				customAuthenticationFilter.setCaptchaService(captchaService);
				AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler("/loginFail");
//				SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler("/loginSuccess");
				customAuthenticationFilter.setAuthenticationFailureHandler(failureHandler);
//				customAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
			} catch (Exception e) {
				e.printStackTrace();
			}
    		customAuthenticationFilter.setCaptchaService(captchaService);
    	
      return customAuthenticationFilter;
    }
    
    //拦截规则
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        // 自定义accessDecisionManager访问控制器,并开启表达式语言
                 http
            .authorizeRequests()
                .antMatchers("/logout/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .anyRequest().hasRole("USER")
                .and()
            .exceptionHandling()
                .accessDeniedPage("/login.jsp?authorization_error=true")
                .and()
            // TODO: put CSRF protection back into this endpoint
            .csrf()
                .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
                .disable()
            .logout()
            	.logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .and()
            .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/j_spring_security_check")
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .permitAll()
                .defaultSuccessUrl("/loginSuccess")
                .failureUrl("/loginFail")
                .and()
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .rememberMe().key("webmvc#FD637E6D9C0F1A5A67082AF56CE32485")
                .and()
                .sessionManagement().sessionFixation().changeSessionId().maximumSessions(1).expiredUrl("/samelogin");
        // @formatter:on
    }
    
    
    
    @Bean  
    public CustomerUserDetailsService userDetailsService() {  
        CustomerUserDetailsService userDetailsService = new CustomerUserDetailsService(); 
        return userDetailsService;  
    }  
  
    @Bean  
    public LoggerListener loggerListener() {  
        logger.info("org.springframework.security.authentication.event.LoggerListener");  
        LoggerListener loggerListener = new LoggerListener();  
  
        return loggerListener;  
    }  
  
    @Bean  
    public org.springframework.security.access.event.LoggerListener eventLoggerListener() {  
        logger.info("org.springframework.security.access.event.LoggerListener");  
        org.springframework.security.access.event.LoggerListener eventLoggerListener = new org.springframework.security.access.event.LoggerListener();  
  
        return eventLoggerListener;  
    }  
  
    /* 
     *  
     * 这里可以增加自定义的投票器 
     */  
    @SuppressWarnings("rawtypes")  
    @Bean(name = "accessDecisionManager")  
    public AccessDecisionManager accessDecisionManager() {  
        logger.info("AccessDecisionManager");  
        List<AccessDecisionVoter> decisionVoters = new ArrayList<AccessDecisionVoter>();  
        decisionVoters.add(new RoleVoter());  
        decisionVoters.add(new AuthenticatedVoter());  
        decisionVoters.add(webExpressionVoter());// 启用表达式投票器  
  
        AffirmativeBased accessDecisionManager = new AffirmativeBased(  
                decisionVoters);  
  
        return accessDecisionManager;  
    }  
  
    /* 
     * 表达式控制器 
     */  
    @Bean(name = "expressionHandler")  
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {  
        logger.info("DefaultWebSecurityExpressionHandler");  
        DefaultWebSecurityExpressionHandler webSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();  
        return webSecurityExpressionHandler;  
    }  
  
    /* 
     * 表达式投票器 
     */  
    @Bean(name = "expressionVoter")  
    public WebExpressionVoter webExpressionVoter() {  
        logger.info("WebExpressionVoter");  
        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();  
        webExpressionVoter.setExpressionHandler(webSecurityExpressionHandler());  
        return webExpressionVoter;  
    }  
}
