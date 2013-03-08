<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>


<c:if test="${not empty KEY_FACEBOOK}">
	<h3>
		<s:text name="updateProfile.importData.title" />
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
				<fb:login-button show-faces="true" width="450" perms="email,user_birthday,user_location"></fb:login-button>
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
	<fieldset class="fieldsetCadre">
		<legend>
			<s:text name="updateProfile.title" />
		</legend>
		<table id="ModifyProfile" class="inLineTable tableStyle">
			<s:form action="/ModifyProfile">
				<tr>
					<td><label for="name"> <s:text name="updateProfile.name" />
					</label></td>
				</tr>
				

				<tr>
					<td><label for="firstName"> <s:text name="updateProfile.firstname" />
					</label></td>
				</tr>
				

				<tr>
					<td><label for="adress"> <s:text name="updateProfile.adress" />
					</label></td>
				</tr>
			

				<tr>
					<td><label for="city"> <s:text name="updateProfile.city" />
					</label></td>
				</tr>
				

				<tr>
					<td><label for="dateOfBirth"> <s:text name="updateProfile.dateOfBirth" />
					</label></td>
				</tr>
			

				<tr>
					<td><label for="sexe"> <s:text name="updateProfile.sexe" />
					</label></td>
					<td><s:select name="sexe" id="sexe" list="sexes"/></td>
				</tr>
				

				<tr>
					<td><label for="job"> <s:text name="updateProfile.job" />
					</label></td>
				</tr>
				

				<tr>
					<td><label for="mail"> <s:text name="updateProfile.email" />
					</label></td>
				</tr>
				

				<tr>
					<td><label for="phone"> <s:text name="updateProfile.phone" />
					</label></td>
				</tr>
				

				<tr>
					<td colspan="2" class="tableButton"><s:submit
							styleClass="btn btn-inverse">
							<s:text name="updateProfile.validate" />
						</s:submit></td>
				</tr>
			</s:form>
		</table>

	</fieldset>
</ili:interactionFilter>

<script type="text/javascript">
    $(function() {
		$.datepicker.setDefaults($.extend({
			yearRange : '-100:+100',
			minDate : '-100Y+1D',
			changeYear : true,
			dateFormat : 'dd/mm/yy',
			maxDate : '+0D',
			showOn : 'both',
			buttonImage : 'images/calendar.gif',
			buttonImageOnly : true,
			showMonthAfterYear : false
		}));
        $.datepicker.setDefaults($.datepicker.regional['fr']);
        
        $("#dateOfBirth").datepicker();
    });
</script>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="updateProfile.changePassword.title" />
	</legend>

	<s:form action="/ChangePassword">

		

		<table class="inLineTable tableStyle">
			<c:forTokens var="typePwd"
				items="oldPassword:newPassword:confirmNewPassword" delims=":">
				<tr>
					<td><label for="${typePwd}"> <s:text name="updateProfile.changePassword.%{typePwd}" />
					</label></td>
					<td><s:password property="${typePwd}" styleId="${typePwd}" />
					</td>
				</tr>
			
			</c:forTokens>
			<tr>
				<td colspan="2" class="tableButton"><s:submit
						styleClass="btn btn-inverse">
						<s:text name="updateProfile.validate" />
					</s:submit></td>
			</tr>
		</table>
	</s:form>
</fieldset>

<ili:interactionFilter user="${ socialEntity }"
	right="${ rightModifyPicture }">
	<fieldset class="fieldsetCadre">
		<legend>
			<s:text name="updateProfile.changePhoto.title" />
		</legend>

		<s:form action="/ChangePhoto" enctype="multipart/form-data">
			<div class="space"></div>
			<img src="avatar/${userId}.png" style="float: right;" alt="Avatar" />
			<div>
				<s:text name="updateProfile.picturesize" />
			</div>

			<div>
				<table class="inLineTable tableStyle">
					<tr>
						<td><label for="photoUrl"> <s:text name="updateProfile.photoInternet" />
						</label>
						
						<td><s:text name="photoUrl" id="photoUrl"></s:text></td>
					</tr>

					<tr>
						<td><label for="photo"> <s:text name="updateProfile.photoLocal" />
						</label></td>
						<td><s:file property="photo" styleId="photo" size="45"></s:file></td>
					</tr>

					<tr>
						<td colspan="2" class="tableButton">
							<s:a action="/DeletePhoto" styleClass="btn btn-inverse">
								<s:text name="updateProfile.deletePhoto" />
							</s:a>
							<s:submit
								styleClass="btn btn-inverse">
								<s:text name="updateProfile.validate" />
							</s:submit></td>
					</tr>
				</table>
			</div>

			
		</s:form>
	</fieldset>
</ili:interactionFilter>



<div class="clear"></div>