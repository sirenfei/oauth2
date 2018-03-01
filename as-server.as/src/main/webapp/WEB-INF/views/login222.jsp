<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
		<meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
        <title>Custom Login Form Styling</title>
        <meta name="description" content="Custom Login Form Styling with CSS3" />
        <meta name="keywords" content="css3, login, form, custom, input, submit, button, html5, placeholder" />
        <meta name="author" content="Codrops" />
        <link rel="shortcut icon" href="../favicon.ico"> 
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
custom grant

        
<form id="confirmationForm" name="confirmationForm" action="<%=request.getContextPath()%>/oauth/authorize" method="post">
			<input name="user_oauth_approval" value="true" type="hidden"><label>
			<input name="authorize" value="Authorize" type="submit"></label>
			
			<input name="scope" value="${scope}" type="hidden"/>
			<input name="client_id" value="${client_id}" type="hidden"/>
			<input name="redirect_uri" value="${redirect_uri}" type="hidden"/>
			<input name="response_type" value="${response_type}" type="hidden"/>
			<input name="state" value="${state}" type="hidden"/>

			

			
</form>
		<form id="denialForm" name="denialForm" action="authorize"
			method="post">
			<input name="user_oauth_approval" value="false" type="hidden"><label><input
				name="deny" value="Deny" type="submit"></label>
		</form>        
        
        
        
    </body>
</html>