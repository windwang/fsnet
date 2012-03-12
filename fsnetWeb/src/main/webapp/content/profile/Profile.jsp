<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>


<c:if test="${not empty KEY_FACEBOOK}">
	<h3>
		<bean:message key="updateProfile.importData.title" />
	</h3>
	<div id="fb-root"></div>
	<script src="http://connect.facebook.net/fr_FR/all.js"></script>
	<script src="js/facebook.js"></script>
	<script type="text/javascript">
		initFacebook(${KEY_FACEBOOK});
	</script>
	<div class="box">
		<div id="social_networks">
			<div id="facebook_button_box">
				<fb:login-button show-faces="true" width="450"
					perms="email,user_birthday,user_location"></fb:login-button>
			</div>
		</div>
		<div id="social_networks_profiles">
			<div id="facebook_profile" style="display: none;">
				<div id="facebook_profile_image"></div>
				<div id="facebook_profile_text"></div>
				<div class="clear"></div>
				<ul id="facebook_user_data">

				</ul>
			</div>
			<div class="clear"></div>
		</div>
	</div>
</c:if>


<ili:interactionFilter user="${ socialEntity }"
	right="${ rightModifyProfil }">
	<fieldset class="fieldsetAppli">
		<legend class="legendHome">
			<bean:message key="updateProfile.title" />
		</legend>
		<table id="ModifyProfile"
			class="inLineTableDashBoardFieldset fieldsetTable">
			<html:form action="/ModifyProfile">
				<tr>
					<td><label for="name"> <bean:message
								key="updateProfile.name" /> :
					</label></td>
					<td><html:text errorStyleClass="error" property="name"
							styleId="name" /></td>
				</tr>
				<logic:messagesPresent property="name">
					<tr>
						<td colspan="2"><html:errors property="name" /></td>
					</tr>
				</logic:messagesPresent>

				<tr>
					<td><label for="firstName"> <bean:message
								key="updateProfile.firstname" /> :
					</label></td>
					<td><html:text errorStyleClass="error" property="firstName"
							styleId="firstName" /></td>
				</tr>
				<logic:messagesPresent property="firstName">
					<tr>
						<td colspan="2"><html:errors property="firstName" /></td>
					</tr>
				</logic:messagesPresent>

				<tr>
					<td><label for="adress"> <bean:message
								key="updateProfile.adress" /> :
					</label></td>
					<td><html:text errorStyleClass="error" property="adress"
							styleId="adress" /></td>
				</tr>
				<logic:messagesPresent property="adress">
					<tr>
						<td colspan="2"><html:errors property="adress" /></td>
					</tr>
				</logic:messagesPresent>

				<tr>
					<td><label for="city"> <bean:message
								key="updateProfile.city" /> :
					</label></td>
					<td><html:text errorStyleClass="error" property="city"
							styleId="city" /></td>
				</tr>
				<logic:messagesPresent property="city">
					<tr>
						<td colspan="2"><html:errors property="city" /></td>
					</tr>
				</logic:messagesPresent>

				<tr>
					<td><label for="dateOfBirth"> <bean:message
								key="updateProfile.dateOfBirth" /> :
					</label></td>
					<td><html:text errorStyleClass="error" styleId="dateOfBirth"
							property="dateOfBirth" /></td>
				</tr>
				<logic:messagesPresent property="dateOfBirth">
					<tr>
						<td colspan="2"><html:errors property="dateOfBirth" /></td>
					</tr>
				</logic:messagesPresent>

				<tr>
					<td><label for="sexe"> <bean:message
								key="updateProfile.sexe" /> :
					</label></td>
					<td><html:select property="sexe" styleId="sexe">
							<html:option value="" />

							<html:option value="male">
								<bean:message key="updateProfile.sexe.male" />
							</html:option>
							<html:option value="female">
								<bean:message key="updateProfile.sexe.female" />
							</html:option>
						</html:select></td>
				</tr>
				<logic:messagesPresent property="sexe">
					<tr>
						<td colspan="2"><html:errors property="sexe" /></td>
					</tr>
				</logic:messagesPresent>

				<tr>
					<td><label for="job"> <bean:message
								key="updateProfile.job" /> :
					</label></td>
					<td><html:text errorStyleClass="error" property="job"
							styleId="job" /></td>
				</tr>
				<logic:messagesPresent property="job">
					<tr>
						<td colspan="2"><html:errors property="job" /></td>
					</tr>
				</logic:messagesPresent>

				<tr>
					<td><label for="mail"> <bean:message
								key="updateProfile.email" /> :
					</label></td>
					<td><html:text errorStyleClass="error" property="mail"
							styleId="mail" /></td>
				</tr>
				<logic:messagesPresent property="mail">
					<tr>
						<td colspan="2"><html:errors property="mail" /></td>
					</tr>
				</logic:messagesPresent>

				<tr>
					<td><label for="phone"> <bean:message
								key="updateProfile.phone" /> :
					</label></td>
					<td><html:text errorStyleClass="error" property="phone"
							styleId="phone" /></td>
				</tr>
				<logic:messagesPresent property="phone">
					<tr>
						<td colspan="2"><html:errors property="phone" /></td>
					</tr>
				</logic:messagesPresent>

				<tr>
					<td colspan="2" align="right"><html:submit styleClass="button">
							<bean:message key="updateProfile.validate" />
						</html:submit></td>
				</tr>
			</html:form>
		</table>

	</fieldset>
</ili:interactionFilter>

<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.7.2.custom.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-i18n.min.js"></script>
<script type="text/javascript">
    $(function() {
        $.datepicker.setDefaults($.extend(
        {
        	yearRange : '-100:+100',
        	minDate:"-100Y+1D",
        	maxDate:0,
            changeYear: true,
            dateFormat: 'dd/mm/yy',
            showOn: 'button',
            buttonImage: 'images/calendar.gif',
            buttonImageOnly: true,
            showMonthAfterYear: false
        }));
        $("#dateOfBirth").datepicker($.datepicker.regional['fr']);
    });
</script>

<fieldset class="fieldsetAppli">
	<legend class="legendHome">
		<bean:message key="updateProfile.changePassword.title" />
	</legend>

	<html:form action="/ChangePassword">

		<logic:messagesPresent property="passwordChange">
			<html:errors property="passwordChange" />
		</logic:messagesPresent>

		<table class="inLineTableDashBoardFieldset fieldsetTable">
			<c:forTokens var="typePwd"
				items="oldPassword:newPassword:confirmNewPassword" delims=":">
				<tr>
					<td><label for="${typePwd}"> <bean:message
								key="updateProfile.changePassword.${typePwd}" />
					</label></td>
					<td><html:password property="${typePwd}" styleId="${typePwd}" />
					</td>
				</tr>
				<logic:messagesPresent property="${typePwd}">
					<tr class="errorMessage">
						<td colspan="2"><html:errors property="${typePwd}" /></td>
					</tr>
				</logic:messagesPresent>
			</c:forTokens>
			<tr>
				<td colspan="2" align="right"><html:submit styleClass="button">
						<bean:message key="updateProfile.validate" />
					</html:submit></td>
			</tr>
		</table>
	</html:form>
</fieldset>

<ili:interactionFilter user="${ socialEntity }"
	right="${ rightModifyPicture }">
	<fieldset class="fieldsetProfil">
		<legend class="legendHome">
			<bean:message key="updateProfile.changePhoto.title" />
		</legend>

		<html:form action="/ChangePhoto" enctype="multipart/form-data">
			<div class="space"></div>

			<div>
				<bean:message key="updateProfile.picturesize" />
			</div>

			<div>
				<table class="inLineTableDashBoardFieldset">
					<tr>
						<td><label> <bean:message
									key="updateProfile.photoInternet" /> :
						</label></td>
						<td><html:text property="photoUrl"></html:text></td>
					</tr>
					
					<tr>
						<td><label> <bean:message
									key="updateProfile.photoLocal" /> :
						</label></td>
						<td><html:file property="photo" size="45"></html:file></td>
					</tr>
					
					<tr>
						<td colspan="2" align="right"><html:submit
								styleClass="button">
								<bean:message key="updateProfile.validate" />
							</html:submit></td>
					</tr>
				</table>
			</div>
			
			<logic:messagesPresent property="photo">
				<div class="errorMessage">
					<html:errors property="photo" />
				</div>
			</logic:messagesPresent>
		</html:form>
	</fieldset>
</ili:interactionFilter>

<img src="avatar/${userId}.png" style="float: right;" alt="Avatar" />
<html:link action="/DeletePhoto" style="float:right;">
	<bean:message key="updateProfile.deletePhoto" />
</html:link>
<div class="clear"></div>