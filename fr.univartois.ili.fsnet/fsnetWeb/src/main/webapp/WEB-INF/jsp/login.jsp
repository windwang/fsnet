<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="application/xhtml+xml; charset=UTF-8" />
	<title>FSNet  -  Login</title>
	<link rel="stylesheet" type="text/css" media="screen" href="css/fsnet-custom.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="css/style.css" />
</head>
<body>
	<div class="wrap background">		
		<ul id="menu"></ul>
		
		<div class="clear"></div>
		
		<div id="logo">
			<h1>
				<a>FSNet</a>
			</h1>
			<h2 id="slogan">Firm Social Networking</h2>
		</div>
		
		<div class="clear"></div>
		
		<h2 id="login-title">Welcome on FSNet !</h2>
		<div id="login">
			<div id="loginWrapper">
				<form action="Authenticate" method="post">
					<table>
						<tr>
							<td>
								<label for="memberMail">
									<bean:message key="login.1"/>
								</label>
							</td>
							<td>
								<input type="text" name="memberMail" id="memberMail" class="error"/>
							</td>
						</tr>
						<tr>
							<td>
								<label for="memberPass">
									<bean:message key="login.0"/>
								</label>
							</td>
							<td>
								<input type="password" name="memberPass"/>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<a onclick="showResetPassword();">
									<bean:message key="login.2"/>
								</a>
								<input type="submit"/>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div style="display:none" id="resetPasswordWrapper">
				<form action="ResetPassword" method="post">
					<div>
						<label for="memberMail">Email :</label>
						<input type="text" id="memberMail"/>
						<input type="submit"/>
					</div>
				</form>
			</div>
			<script type="text/javascript">
				function showResetPassword() {
					var resetPasswordWrapper = document.getElementById('resetPasswordWrapper');
					var loginWrapper = document.getElementById('loginWrapper');
					loginWrapper.style.display = 'none';
					resetPasswordWrapper.style.display = 'block';
				}
			</script>
			<c:if test="${! empty loginMessage}">
				<h3><bean:message key="${loginMessage}"/></h3>
			</c:if>
		</div>
		
		<div class="clear"></div>
		
	</div>

	<div id="login-footer">	
		<div class="col last">
		</div>
		
	</div>
</body>
</html>