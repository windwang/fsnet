<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="ili" uri="../ili.tld"%>

<bean:define id="logMessage">
	<bean:message key="login.placeholder.mail" />
</bean:define>
<bean:define id="passwordMessage">
	<bean:message key="login.placeholder.password" />
</bean:define>
<bean:define id="loginSubmit">
	<bean:message key="login.submit" />
</bean:define>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type"
	content="application/xhtml+xml; charset=UTF-8" />
<title>FSNet - Login</title>
<link rel="shortcut icon" href="images/Favicon.ico"
	type="image/vnd.microsoft.icon" />
<link rel="stylesheet" type="text/css" media="screen"
	href="css/fsnet-custom.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="css/style.css" />
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<script type="text/javascript">
	
</script>
</head>
<body>
	<script src="js/jquery-1.9.0.min.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>

	<div class="navbar navbar-inverse">
		<div class="navbar-inner">
			<div class="container">

				<ul class="nav">
					<li><a href="http://code.google.com/p/fsnet/"> <img
							src="images/google.png"
							title="Accéder à Google Forge" alt="Accéder à Google Forge" height="32px"
							width="32px">
					</a></li>
					<li><a href="http://www.univ-artois.fr/"> <img
							src="images/univartois.png"
							title="Site de l'Université d'Artois" alt="Site de l'Université d'Artois"
							height="32px" width="32px">
					</a></li>
					<li><a
						href="http://fsnet.googlecode.com/files/FsnetAndroid-0.7.2.3.apk">
							<img src="images/android.png"
							alt="Télécharger l'application Android"
							title="Télécharger l'application Android">
					</a></li>
					<li><a
						href="http://www.facebook.com/profile.php?id=100003374258658">
							<img src="images/facebook.png"
							title="Rejoignez-nous sur Facebook" alt="Rejoignez-nous sur Facebook" height="32px"
							width="32px">
					</a></li>
				</ul>


			</div>
		</div>
	</div>
	
	<!-- DEBUT CONTAINER BOOTSTRAP -->
	<div class="container-fluid">

		<div class="row" >			
			
				<h2 id="login-title">
					<bean:message key="welcome.message" />
				</h2>
			
				<div class="span8 offset1">
					<div id="img-logo">
						<img src="images/FSNET.png" alt="Logo FSNET">	
					</div>
				
					<table>
						<tbody><tr>
							<td id="affiche"><p class="quotation">
									"
									<bean:message key="welcome.message1" />
								</p>
								<p class="quotation">
									<bean:message key="welcome.message2" />
									"
								</p></td>
						</tr>
					</tbody></table>
				</div>

				<div class="span4 offset1" id="loginWrapper">
						
						
						<form class="form-signin" action="Authenticate" method="post">
							<h2 class="form-signin-heading"> <bean:message key="login.connection" /> </h2>
							
							<input type="email" class="input-block-level"
								name="memberMail" id="memberMail" value="${param['memberMail'] }" placeholder="${logMessage}">
								
							<input type="password" class="input-block-level" 
								id="memberPass" name="memberPass" placeholder="${passwordMessage}">
							
							<label for="remember" class="checkbox">
							  <input type="checkbox" id="remember" name="remember"> 
							  	<bean:message key="login.checkbox.connectAuto" />
							</label>
							<input class="btn btn-large" type="submit" value="${loginSubmit}">
							
							<a onclick="showResetPasswordForm();"> 
								<bean:message key="login.password.forget" />
							</a>
					  </form>				
					  
					  <c:if test="${! empty loginMessage && loginMessage eq 'login.error'}">
						<h5 id="incorrectIdPwd">
							<bean:message key="${loginMessage}" />
						</h5>
					</c:if>		
					
				</div>	
				
				<div style="display: none" class="span4 offset1" id="resetPasswordWrapper">
					<form action="ResetPassword" method="post" class="form-signin">
					
						<h3 class="form-signin-heading"> <bean:message key="login.password.forget" /> </h3>

						<input name="memberMail2" type="email" id="memberMail2" placeholder="${logMessage}" />
						
						<input class="btn" type="submit" value="${loginSubmit}">

						<a onclick="showLoginForm();"> 
							<bean:message key="login.authentification" />
						</a>
					</form>
					
					<c:if test="${! empty loginMessage && loginMessage eq 'login.password.reset.error'}">
						<h5 id="incorrectIdPwd">
							<bean:message key="${loginMessage}" />
						</h5>
					</c:if>	
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
				
				
			
		</div> <!-- ROW-FLUID -->

	</div> <!-- CONTAINER -->
	
	<!-- FIN NEW BOOTSTRAP -->
	
	<c:if test="${loginMessage eq 'login.password.reset.error' || loginMessage eq 'login.password.reset' }">
		<script>showResetPasswordForm();</script>				
	</c:if>

</body>
</html>