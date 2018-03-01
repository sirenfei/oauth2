package com.faithbj.oauth.as.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.faithbj.oauth.as.captcha.JCaptchaEngine;
import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;

@Controller
public class CaptchaController {
	public static final String CAPTCHA_IMAGE_FORMAT = "jpeg";
	@Resource
	private CaptchaService captchaService;
	
	/**
	 * 生成验证码图片.
	 */
	@RequestMapping(value = "/showcaptcha", method = RequestMethod.GET)
	public void genernateCaptchaImage(final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {

		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		ServletOutputStream out = response.getOutputStream();
		try {
			String captchaId = request.getSession(true).getId();
			BufferedImage challenge = (BufferedImage) captchaService.getChallengeForID(captchaId, request.getLocale());
			ImageIO.write(challenge, CAPTCHA_IMAGE_FORMAT, out);
			out.flush();
		} catch (CaptchaServiceException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	
	
	/**
	 * 验证验证码.
	 */
	public boolean validateCaptchaChallenge(final HttpServletRequest request) {
		try {
			String captchaID = request.getSession().getId();
			String challengeResponse = request.getParameter(JCaptchaEngine.CAPTCHA_INPUT_NAME);
			String finalResponse = StringUtils.upperCase(request.getParameter(JCaptchaEngine.CAPTCHA_INPUT_NAME));
			
			return captchaService.validateResponseForID(captchaID,challengeResponse);
		} catch (CaptchaServiceException e) {
			// logger.error(e.getMessage(), e);
			return false;
		}
	}
}
