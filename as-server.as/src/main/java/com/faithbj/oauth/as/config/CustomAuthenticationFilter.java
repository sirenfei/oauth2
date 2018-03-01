package com.faithbj.oauth.as.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.faithbj.oauth.as.captcha.JCaptchaEngine;
import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;


public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
    protected final Logger logger = LoggerFactory.getLogger(CustomAuthenticationFilter.class);
    

	private CaptchaService captchaService;
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			String captchaID = request.getSession().getId();
			String challengeResponse = request.getParameter(JCaptchaEngine.CAPTCHA_INPUT_NAME);
			String finalResponse = StringUtils.upperCase(request.getParameter(JCaptchaEngine.CAPTCHA_INPUT_NAME));
			
			boolean result =  captchaService.validateResponseForID(captchaID,challengeResponse);
			if(!result)
			{
				logger.info("valid code error,captchaID:{},challengeResponse:{},finalResponse:{}", captchaID, challengeResponse, finalResponse);
				throw new AuthenticationServiceException("用户名或者密码或验证码错误！");  
			}
		} catch (CaptchaServiceException e) {
			logger.error("CaptchaServiceException", e);
			throw new AuthenticationServiceException("用户名或者密码或验证码错误！"); 
		}
		
		return super.attemptAuthentication(request, response);
	}

	public void setCaptchaService(CaptchaService captchaService) {
		this.captchaService = captchaService;
	}

	@Override
	public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler successHandler) {
		super.setAuthenticationSuccessHandler(successHandler);
	}

	@Override
	public void setAuthenticationFailureHandler(AuthenticationFailureHandler failureHandler) {
		// TODO Auto-generated method stub
		super.setAuthenticationFailureHandler(failureHandler);
	}


}
