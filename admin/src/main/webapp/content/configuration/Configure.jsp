<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel=stylesheet type="text/css" href="css/osx.css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery.simplemodal.js"></script>
<script type="text/javascript" src="js/osx.js"></script>

<h3><bean:message key="configure.10" /></h3>
<html:form action="/SaveMailConfiguration">
	<table id="ConfigureMail">
		<tr>
			<td><label for="SMTPHost"> <bean:message
				key="configure.0" /> </label></td>
			<td><html:text errorStyleClass="error" styleId="SMTPHost"
				property="SMTPHost" /></td>
		</tr>
		<logic:messagesPresent property="SMTPHost">
			<tr>
				<td colspan="2"><html:errors property="SMTPHost" /></td>
			</tr>
		</logic:messagesPresent>
		<tr>
			<td><label for="SMTPPort"> <bean:message
				key="configure.1" /> </label></td>
			<td><html:text errorStyleClass="error" styleId="SMTPPort"
				property="SMTPPort" /></td>
		</tr>
		<logic:messagesPresent property="SMTPPort">
			<tr class="errorMessage">
				<td colspan="2"><html:errors property="SMTPPort" /></td>
			</tr>
		</logic:messagesPresent>
		<tr>
			<td><label for="enableAuthentication"> <bean:message
				key="configure.2" /> </label></td>
			<td><html:checkbox styleId="enableAuthentication"
				property="enableAuthentication"
				onchange="updateAuthenticationFields();" /></td>
		</tr>
		<logic:messagesPresent property="enableAuthentication">
			<tr class="errorMessage">
				<td colspan="2"><html:errors property="enableAuthentication" />
				</td>
			</tr>
		</logic:messagesPresent>
		<tr>
			<td><label for="SMTPUsername"> <bean:message
				key="configure.3" /> </label></td>
			<td><html:text errorStyleClass="error" styleId="SMTPUsername"
				property="SMTPUsername" /></td>
		</tr>
		<logic:messagesPresent property="SMTPUsername">
			<tr class="errorMessage">
				<td colspan="2"><html:errors property="SMTPUsername" /></td>
			</tr>
		</logic:messagesPresent>
		<tr>
			<td><label for="SMTPPassword"> <bean:message
				key="configure.4" /> </label></td>
			<td><html:password errorStyleClass="error"
				styleId="SMTPPassword" property="SMTPPassword" /></td>
		</tr>
		<logic:messagesPresent property="SMTPPassword">
			<tr class="errorMessage">
				<td colspan="2"><html:errors property="SMTPPassword" /></td>
			</tr>
		</logic:messagesPresent>
		<tr>
			<td><label for="MailFrom"> <bean:message
				key="configure.5" /> </label></td>
			<td><html:text errorStyleClass="error" styleId="MailFrom"
				property="MailFrom" /></td>
		</tr>
		<logic:messagesPresent property="MailFrom">
			<tr class="errorMessage">
				<td colspan="2"><html:errors property="MailFrom" /></td>
			</tr>
		</logic:messagesPresent>
		<tr>
			<td><label for="enableTLS"> <bean:message
				key="configure.6" /> </label></td>
			<td><html:checkbox styleId="enableTLS" property="enableTLS" /></td>
		</tr>
		<logic:messagesPresent property="enableTLS">
			<tr class="errorMessage">
				<td colspan="2"><html:errors property="enableTLS" /></td>
			</tr>
		</logic:messagesPresent>
		<tr>
			<td><label for="enableSSL"> <bean:message
				key="configure.7" /> </label></td>
			<td><html:checkbox styleId="enableSSL" property="enableSSL" /></td>
		</tr>
		<logic:messagesPresent property="enableSSL">
			<tr class="errorMessage">
				<td colspan="2"><html:errors property="enableSSL" /></td>
			</tr>
		</logic:messagesPresent>
		<tr>
			<td><label for="FSNetWebURL"> <bean:message
				key="configure.8" /> </label></td>
			<td><html:text errorStyleClass="error" styleId="FSNetWebURL"
				property="FSNetWebURL" /></td>
		</tr>
		<logic:messagesPresent property="FSNetWebURL">
			<tr class="errorMessage">
				<td colspan="2"><html:errors property="FSNetWebURL" /></td>
			</tr>
		</logic:messagesPresent>
		<tr>
			<td><label for="PicturesDirectory"> <bean:message
				key="configure.22" /> </label></td>
			<td><html:text errorStyleClass="error"
				styleId="PicturesDirectory" property="PicturesDirectory" /></td>
		</tr>
		<logic:messagesPresent property="PicturesDirectory">
			<tr class="errorMessage">
				<td colspan="2"><html:errors property="PicturesDirectory" /></td>
			</tr>
		</logic:messagesPresent>
		<tr class="errorMessage">
			<td colspan="2"><html:errors property="FSNetWebURL" /></td>
		</tr>
		<tr>
			<td><label for="FSNetWebURL"> <bean:message
				key="configure.8" /> </label></td>
			<td><html:text errorStyleClass="error" styleId="FSNetWebURL"
				property="FSNetWebURL" /></td>
		</tr>
		<tr>
			<td colspan="2"><html:submit styleClass="button">
				<bean:message key="configure.9" />
			</html:submit></td>
		</tr>
	</table>
</html:form>

<h3><bean:message key="configure.25" /></h3>
<html:form action="/SaveFacebookId">
	<table id="saveFacebookId">
		<logic:messagesPresent property="KeyFacebook">
			<tr class="errorMessage">
				<td colspan="2"><html:errors property="KeyFacebook" /></td>
			</tr>
		</logic:messagesPresent>
		<tr>
			<td><label for="KeyFacebook"> <bean:message
				key="configure.24" /> </label>
				</td>
			<td><html:text errorStyleClass="error" styleId="KeyFacebook"
				property="KeyFacebook" />
			</td>
		</tr>
		<tr>
			<td colspan="2"><html:submit styleClass="button">
				<bean:message key="configure.9" />
			</html:submit></td>
		</tr>
	</table>
</html:form>

<h3><bean:message key="configure.UpdateDB"/></h3>
<html:form enctype="multipart/form-data" action="/UpdateDB">
	<div>	
	<html:submit styleClass="button">
		<bean:message key="configure.9" />
	</html:submit>
	</div>
</html:form>
 
<h3><bean:message key="configure.UpdateDateType"/></h3>
<html:form enctype="multipart/form-data" action="/UpdateDateType">
	<div>	
	<html:submit styleClass="button">
		<bean:message key="configure.9" />
	</html:submit>
	</div>
</html:form>

<h3><bean:message key="configure.16" /></h3>
<html:form action="/SendTestMail">
	<table id="SendTestMail">
		<tr>
			<td><label for="Recipient"> <bean:message
				key="configure.14" /> </label> <html:text errorStyleClass="error"
				property="Recipient" styleId="Recipient"></html:text></td>
			<td><html:submit styleClass="button">
				<bean:message key="configure.11" />
			</html:submit></td>
		</tr>
		<logic:messagesPresent property="Recipient">
			<tr class="errorMessage">
				<td colspan="2"><html:errors property="Recipient" /></td>
			</tr>
		</logic:messagesPresent>
	</table>
</html:form>



<script type="text/javascript">
				function updateAuthenticationFields() {
					var usernameField = document.getElementById('SMTPUsername');
					var passwordField = document.getElementById('SMTPPassword');
					var enableAuthentication = document.getElementById('enableAuthentication');
					if (enableAuthentication.checked) {
						usernameField.disabled= false;
						passwordField.disabled= false;
					} else {
						usernameField.disabled= true;
						passwordField.disabled= true;
					}
				}
				updateAuthenticationFields();
			</script>
			
<c:if test="${!empty success}">
	<script type="text/javascript">
		jQuery(function () { popup(); });
		success = null;
	</script>
	<div id="osx-modal-content" class="simplemodal-data">
		<div id="osx-modal-title">Message</div>
		<div class="close">
			<a class="simplemodal-close" href="#">X</a>
		</div>
		<div id="osx-modal-data">
			<p><c:out value="${success}"/></p>
		</div>
	</div>
</c:if>





