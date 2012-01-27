<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type"
	content="application/xhtml+xml; charset=UTF-8" />
<title>FSNet - Login</title>
<link rel="stylesheet" type="text/css" media="screen"
	href="css/fsnet-custom.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="css/style.css" />
<script type="text/javascript">
	
</script>
</head>
<body>
	<div class="wrap background">
		<ul id="menuLogin">
			<li><a href="http://code.google.com/p/fsnet/"> <img
					width="30px" height="30px" src="images/google.png"
					title="Accédez à notre forge" alt="Google Forge" />
			</a></li>
			<li><a href="http://www.univ-artois.fr/"> <img width="30px"
					height="30px" src="images/univartois.png"
					title="Site de l'université d'artois" alt="université d'artois" />
			</a></li>
			<li><a
				href="http://fsnet.googlecode.com/files/FsnetAndroid-0.7.2.3.apk">
					<img src="images/android.png" alt="application androïd"
					title="Téléchargez l'application androïd" />
			</a></li>
			<li><a
				href="http://www.facebook.com/profile.php?id=100003374258658"> <img
					width="30px" height="30px" src="images/facebook.png"
					title="Rejoignez-nous sur Facebook" alt="Facebook" />
			</a></li>
		</ul>

		<div class="clear"></div>

		<div id="logo">
			<table>
				<tr>
					<td><img src="images/FSNET.png" /></td>
					<td id="affiche"><p class="quotation">
							"
							<bean:message key="welcome.message1" />
						</p>
						<p class="quotation">
							<bean:message key="welcome.message2" />
							"
						</p></td>
				</tr>
			</table>
		</div>

		<div class="clear"></div>

		<h2 id="login-title">
			<bean:message key="welcome.message" />
		</h2>
		<div id="login">
			<div id="loginWrapper">
				<form action="Authenticate" method="post">
					<table>
						<tr>
							<td>
								<div class="label">
									<label for="memberMail"> <bean:message key="login.1" />
									</label>
								</div>
							</td>
							<td><input type="text" name="memberMail" id="memberMail"
								class="error" /></td>
						</tr>
						<tr>
							<td>
								<div class="label">
									<label for="memberPass"> <bean:message key="login.0" />
									</label>
								</div>
							</td>
							<td><input type="password" id="memberPass" name="memberPass" /></td>
						</tr>
						<tr>
							<td><label for="remember"> <bean:message
										key="login.5" />
							</label></td>
							<td><input type="checkbox" id="remember" name="remember" />
							</td>
						</tr>
						<tr>
							<td colspan="2"><a onclick="showResetPasswordForm();"> <bean:message
										key="login.2" />
							</a> <input type="submit" /></td>
						</tr>
					</table>
				</form>
			</div>
			<div style="display: none" id="resetPasswordWrapper">
				<form action="ResetPassword" method="post">
					<table>
						<tr>
							<td><label for="memberMail">Email :</label></td>
							<td><input name="memberMail" type="text" id="memberMail" />
							</td>
							<td><input type="submit" /></td>
						</tr>
						<tr>
							<td colspan="3"><a onclick="showLoginForm();"> <bean:message
										key="login.4" />
							</a></td>
						</tr>
					</table>
				</form>
			</div>
			<script type="text/javascript">
				function showResetPasswordForm() {
					var resetPasswordWrapper = document
							.getElementById('resetPasswordWrapper');
					var loginWrapper = document.getElementById('loginWrapper');
					loginWrapper.style.display = 'none';
					resetPasswordWrapper.style.display = 'block';
				}
				function showLoginForm() {
					var resetPasswordWrapper = document
							.getElementById('resetPasswordWrapper');
					var loginWrapper = document.getElementById('loginWrapper');
					loginWrapper.style.display = 'block';
					resetPasswordWrapper.style.display = 'none';
				}
			</script>
			<c:if test="${! empty loginMessage}">
				<h3>
					<bean:message key="${loginMessage}" />
				</h3>
			</c:if>
		</div>

		<div class="clear"></div>
	</div>

	<div id="login-footer">
		<div class="col last"></div>

	</div>
</body>
</html>