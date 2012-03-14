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

<fieldset class="fieldsetAdmin">
	<legend class="legendAdmin">
		<bean:message key="configure.title.mailConfiguration" />
	</legend>

	<html:form action="/SaveMailConfiguration">
		<table id="ConfigureMail" class="inLineTable fieldsetTableAdmin">
			<tr>
				<td><label for="SMTPHost"> <bean:message
							key="configure.form.serverSmtp" />
				</label></td>
				<td><html:text errorStyleClass="error" styleId="SMTPHost"
						property="SMTPHost" /> <logic:messagesPresent property="SMTPHost">
						<div class="errorMessage">
							<html:errors property="SMTPHost" />
						</div>
					</logic:messagesPresent></td>
			</tr>

			<tr>
				<td><label for="SMTPPort"> <bean:message
							key="configure.form.portSmtp" />
				</label></td>
				<td><html:text errorStyleClass="error" styleId="SMTPPort"
						property="SMTPPort" /> <logic:messagesPresent property="SMTPPort">
						<div class="errorMessage">
							<html:errors property="SMTPPort" />
						</div>
					</logic:messagesPresent></td>
			</tr>

			<tr>
				<td><label for="enableAuthentication"> <bean:message
							key="configure.form.activateAuth" />
				</label></td>
				<td><html:checkbox styleId="enableAuthentication"
						property="enableAuthentication"
						onchange="updateAuthenticationFields();" /> <logic:messagesPresent
						property="enableAuthentication">
						<div class="errorMessage">
							<html:errors property="enableAuthentication" />
						</div>
					</logic:messagesPresent></td>
			</tr>

			<tr>
				<td><label for="SMTPUsername"> <bean:message
							key="configure.form.mailLogin" />
				</label></td>
				<td><html:text errorStyleClass="error" styleId="SMTPUsername"
						property="SMTPUsername" /> <logic:messagesPresent
						property="SMTPUsername">
						<div class="errorMessage">
							<html:errors property="SMTPUsername" />
						</div>
					</logic:messagesPresent></td>
			</tr>

			<tr>
				<td><label for="SMTPPassword"> <bean:message
							key="configure.form.mailPassword" />
				</label></td>
				<td><html:password errorStyleClass="error"
						styleId="SMTPPassword" property="SMTPPassword" /> <logic:messagesPresent
						property="SMTPPassword">
						<div class="errorMessage">
							<html:errors property="SMTPPassword" />
						</div>
					</logic:messagesPresent></td>
			</tr>

			<tr>
				<td><label for="MailFrom"> <bean:message
							key="configure.form.mailAdmin" />
				</label></td>
				<td><html:text errorStyleClass="error" styleId="MailFrom"
						property="MailFrom" /> <logic:messagesPresent property="MailFrom">
						<div class="errorMessage">
							<html:errors property="MailFrom" />
						</div>
					</logic:messagesPresent></td>
			</tr>

			<tr>
				<td><label for="enableTLS"> <bean:message
							key="configure.form.activateTls" />
				</label></td>
				<td><html:checkbox styleId="enableTLS" property="enableTLS" />
					<logic:messagesPresent property="enableTLS">
						<div class="errorMessage">
							<html:errors property="enableTLS" />
						</div>
					</logic:messagesPresent></td>
			</tr>

			<tr>
				<td><label for="enableSSL"> <bean:message
							key="configure.form.activateSsl" />
				</label></td>
				<td><html:checkbox styleId="enableSSL" property="enableSSL" />
					<logic:messagesPresent property="enableSSL">
						<div class="errorMessage">
							<html:errors property="enableSSL" />
						</div>
					</logic:messagesPresent></td>
			</tr>

			<tr>
				<td><label for="FSNetWebURL"> <bean:message
							key="configure.form.urlPublicSite" />
				</label></td>
				<td><html:text errorStyleClass="error" styleId="FSNetWebURL"
						property="FSNetWebURL" /> <logic:messagesPresent
						property="FSNetWebURL">
						<div class="errorMessage">
							<html:errors property="FSNetWebURL" />
						</div>
					</logic:messagesPresent></td>
			</tr>

			<tr>
				<td><label for="PicturesDirectory"> <bean:message
							key="configure.form.imgFolder" />
				</label></td>
				<td><html:text errorStyleClass="error"
						styleId="PicturesDirectory" property="PicturesDirectory" /> <logic:messagesPresent
						property="PicturesDirectory">
						<div class="errorMessage">
							<html:errors property="PicturesDirectory" />
						</div>
					</logic:messagesPresent></td>
			</tr>

			<tr>
				<td colspan="2" class="tableButton"><html:submit styleClass="button">
						<bean:message key="configure.button.submit" />
					</html:submit></td>
			</tr>
		</table>
	</html:form>
</fieldset>

<fieldset class="fieldsetAdmin">
	<legend class="legendAdmin">
		<bean:message key="configure.title.testMail" />
	</legend>

	<html:form action="/SendTestMail">
		<table id="SendTestMail" class="inLineTable fieldsetTableAdmin">
			<tr>
				<td><label for="Recipient"> <bean:message
							key="configure.form.testMail" />
				</label></td>
				<td><html:text errorStyleClass="error" property="Recipient"
						styleId="Recipient"></html:text> <logic:messagesPresent
						property="Recipient">
						<div class="errorMessage">
							<html:errors property="Recipient" />
						</div>
					</logic:messagesPresent></td>
			</tr>

			<tr>
				<td colspan="2" class="tableButton"><html:submit styleClass="button">
						<bean:message key="configure.button.submit" />
					</html:submit></td>
			</tr>
		</table>
	</html:form>
</fieldset>

<fieldset class="fieldsetAdmin">
	<legend class="legendAdmin">
		<bean:message key="configure.title.facebookKey" />
	</legend>

	<html:form action="/SaveFacebookId">
		<table id="saveFacebookId" class="inLineTable fieldsetTableAdmin">
			<tr>
				<td><label for="KeyFacebook"> <bean:message
							key="configure.form.facebookKey" />
				</label></td>
				<td><html:text errorStyleClass="error" styleId="KeyFacebook"
						property="KeyFacebook" /> <logic:messagesPresent
						property="KeyFacebook">
						<div class="errorMessage">
							<html:errors property="KeyFacebook" />
						</div>
					</logic:messagesPresent></td>
			</tr>

			<tr>
				<td colspan="2" class="tableButton"><html:submit styleClass="button">
						<bean:message key="configure.button.submit" />
					</html:submit></td>
			</tr>
		</table>
	</html:form>
</fieldset>

<fieldset class="fieldsetAdmin">
	<legend class="legendAdmin">
		<bean:message key="configure.title.updateDBMail" />
	</legend>

	<table class="inLineTable fieldsetTableAdmin">
		<tr>
			<td><html:form enctype="multipart/form-data" action="/UpdateDB">
					<div>
						<html:submit styleClass="button">
							<bean:message key="configure.button.submit" />
						</html:submit>
					</div>
				</html:form></td>
		</tr>
	</table>
</fieldset>

<fieldset class="fieldsetAdmin">
	<legend class="legendAdmin">
		<bean:message key="configure.title.updateDBDateType" />
	</legend>

	<table class="inLineTable fieldsetTableAdmin">
		<tr>
			<td><html:form enctype="multipart/form-data"
					action="/UpdateDateType">
					<div>
						<html:submit styleClass="button">
							<bean:message key="configure.button.submit" />
						</html:submit>
					</div>
				</html:form></td>
		</tr>
	</table>
</fieldset>

<fieldset class="fieldsetAdmin">
	<legend class="legendAdmin">
		<bean:message key="configure.title.addDBRecallDateColumn" />
	</legend>

	<table class="inLineTable fieldsetTableAdmin">
		<tr>
			<td><html:form enctype="multipart/form-data"
					action="/AddRecalTimeColumnInMeeting">
					<div>
						<html:submit styleClass="button">
							<bean:message key="configure.button.submit" />
						</html:submit>
					</div>
				</html:form></td>
		</tr>
	</table>
</fieldset>

<fieldset class="fieldsetAdmin">
	<legend class="legendAdmin">
		<bean:message key="configure.title.dropDBCVTables" />
	</legend>

	<table class="inLineTable fieldsetTableAdmin">
		<tr>
			<td><html:form enctype="multipart/form-data"
					action="/DropCVTables">
					<div>
						<html:submit styleClass="button">
							<bean:message key="configure.button.submit" />
						</html:submit>
					</div>
				</html:form></td>
		</tr>
	</table>
</fieldset>

<script type="text/javascript">
	function updateAuthenticationFields() {
		var usernameField = document.getElementById('SMTPUsername');
		var passwordField = document.getElementById('SMTPPassword');
		var enableAuthentication = document
				.getElementById('enableAuthentication');
		if (enableAuthentication.checked) {
			usernameField.disabled = false;
			passwordField.disabled = false;
		} else {
			usernameField.disabled = true;
			passwordField.disabled = true;
		}
	}
	updateAuthenticationFields();
</script>

<c:if test="${!empty success}">
	<script type="text/javascript">
		jQuery(function() {
			popup();
		});
		success = null;
	</script>
	<div id="osx-modal-content" class="simplemodal-data">
		<div id="osx-modal-title">Message</div>
		<div class="close">
			<a class="simplemodal-close" href="#">X</a>
		</div>
		<div id="osx-modal-data">
			<p>
				<c:out value="${success}" />
			</p>
		</div>
	</div>
</c:if>





