<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
<title>AS Server系统</title>
        <link rel="stylesheet" type="text/css" href="css/style.css" />
        <script src="js/modernizr.custom.63321.js"></script>
        <!--[if lte IE 7]><style>.main{display:none;} .support-note .note-ie{display:block;}</style><![endif]-->
		<style>
			body {
				background: #e1c192 url(images/wood_pattern.jpg);
			}
		</style>
</head>
   <body>
        <div class="container">
		
			<header>
			
				<h1><strong>AS登录</strong></h1>
				
				<div class="support-note">
					<span class="note-ie">Sorry, only modern browsers.</span>
				</div>
				
			</header>
			
			<section class="main">
				<form class="form-2" action="j_spring_security_check" method="post">
					<h1><span class="log-in">登录</span> 或者 <span class="sign-up">注册</span></h1>
					<p class="float">
						<label for="login"><i class="icon-user"></i>用户名</label>
						<input type="text" name="j_username" placeholder="Username">
					</p>
					<p class="float">
						<label for="password"><i class="icon-lock"></i>密码</label>
						<input type="password" name="j_password" placeholder="Password" class="showpassword">
					</p>
					<p class="float">
						<label for="password"><i class="icon-lock"></i>验证码</label>
						<input type="text" name="j_captcha" class="showpassword">
					</p>
					<p class="float">
						<label for="password"></label>
						<img style="margin-left:5px;margin-top:5px;" src="<%=request.getContextPath()%>/showcaptcha" border=0>
					</p>
					
					
					<p class="clearfix"> 
						<a href="#" class="log-twitter">注册</a>    
						<input type="submit" name="submit" value="登录">
					</p>
					<div> ${SPRING_SECURITY_LAST_EXCEPTION.message}</div>
				</form>
			</section>
			
        </div>

    </body>
</html>