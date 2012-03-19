<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html:form action="/CreateCv2">
	<div class="en_cv">
		<fieldset class="fieldsetAppli">
			<div class="entete">
				<legend class="legendHome">
					<bean:message key="cv.title.name" />
				</legend>
			</div>
			<div class="corp">
				<table class="inLineTable fieldsetTableAppli">
					<tr>
						<td><label for="cvTitle"><bean:message
									key="cv.form.title" /></label></td>
						<td><html:text property="cvTitle" styleId="cvTitle"
								errorStyleClass="error" /> <logic:messagesPresent
								property="cvTitle">
								<div class="errorMessage">
									<html:errors property="cvTitle" />
								</div>
							</logic:messagesPresent></td>
					</tr>
				</table>
			</div>
		</fieldset>
	</div>

	<div class="en_cv">
		<fieldset class="fieldsetAppli">
			<div class="entete">
				<legend class="legendHome">
					<bean:message key="cv.title.info" />
				</legend>
			</div>
			<div class="corp_contact">
				<table class="inLineTable fieldsetTableAppli">
					<tr>
						<td><label for="cvFirstname"><bean:message
									key="cv.form.profile.firstname" /></label></td>
						<td><html:text property="cvFirstname" styleId="cvFirstname"
								errorStyleClass="error" value="${sessionScope.user.name}" /> <logic:messagesPresent
								property="cvFirstname">
								<div class="errorMessage">
									<html:errors property="cvFirstname" />
								</div>
							</logic:messagesPresent></td>
					</tr>

					<tr>
						<td><label for="cvSurname"><bean:message
									key="cv.form.profile.surname" /></label></td>
						<td><html:text property="cvSurname" styleId="cvSurname"
								errorStyleClass="error" value="${sessionScope.user.firstName}" />
							<logic:messagesPresent property="cvSurname">
								<div class="errorMessage">
									<html:errors property="cvSurname" />
								</div>
							</logic:messagesPresent></td>
					</tr>

					<tr>
						<td><label for="cvSexe"><bean:message
									key="cv.form.profile.sex" /></label></td>
						<td><select name="cvSexe">
								<c:choose>
									<c:when test="${sessionScope.user.sex == 'male'}">
										<option value=""></option>
										<option value="male" selected="selected">
											<bean:message key="members.sexe.Male" />
										</option>
										<option value="female">
											<bean:message key="members.sexe.Female" />
										</option>
									</c:when>
									<c:when test="${sessionScope.user.sex == 'female'}">
										<option value=""></option>
										<option value="male">
											<bean:message key="members.sexe.Male" />
										</option>
										<option value="female" selected="selected">
											<bean:message key="members.sexe.Female" />
										</option>
									</c:when>
									<c:otherwise>
										<option value=""></option>
										<option value="male">
											<bean:message key="members.sexe.Male" />
										</option>
										<option value="female">
											<bean:message key="members.sexe.Female" />
										</option>
									</c:otherwise>
								</c:choose>
						</select></td>
					</tr>

					<tr>
						<td><label for="cvAddress"><bean:message
									key="cv.form.profile.address" /></label></td>
						<td><html:text property="cvAddress" styleId="cvAddress"
								errorStyleClass="error"
								value="${sessionScope.user.address.address}" /> <logic:messagesPresent
								property="cvAddress">
								<div class="errorMessage">
									<html:errors property="cvAddress" />
								</div>
							</logic:messagesPresent></td>
					</tr>

					<tr>
						<td><label for="cvCity"><bean:message
									key="cv.form.profile.city" /></label></td>
						<td><html:text property="cvCity" styleId="cvCity"
								errorStyleClass="error"
								value="${sessionScope.user.address.city}" /> <logic:messagesPresent
								property="cvCity">
								<div class="errorMessage">
									<html:errors property="cvCity" />
								</div>
							</logic:messagesPresent></td>
					</tr>

					<tr>
						<td><label for="cvCP"><bean:message
									key="cv.form.profile.cp" /></label></td>
						<td><html:text property="cvCP" styleId="cvCP"
								errorStyleClass="error" /> <logic:messagesPresent
								property="cvCP">
								<div class="errorMessage">
									<html:errors property="cvCP" />
								</div>
							</logic:messagesPresent></td>
					</tr>

					<tr>
						<td><label for="cvCountry"> <bean:message
									key="cv.form.profile.country" /></label></td>
						<td><html:text property="cvCountry" styleId="cvCountry"
								errorStyleClass="error" />
							<logic:messagesPresent property="cvCountry">
								<div class="errorMessage">
									<html:errors property="cvCountry" />
								</div>
							</logic:messagesPresent></td>
					</tr>

					<tr>
						<td><label for="cvPhone"> <bean:message
									key="cv.form.profile.phone" />
						</label></td>
						<td><html:text property="cvPhone" styleId="cvPhone"
								errorStyleClass="error" value="${sessionScope.user.phone}" /> <logic:messagesPresent
								property="cvPhone">
								<div class="errorMessage">
									<html:errors property="cvPhone" />
								</div>
							</logic:messagesPresent></td>
					</tr>

					<tr>
						<td><label for="cvBirthDay"> <bean:message
									key="cv.form.profile.birthday" />
						</label></td>
						<td><html:text errorStyleClass="error" styleId="cvBirthDay"
								property="cvBirthDay">
							</html:text> <logic:messagesPresent property="cvBirthDay">
								<div class="errorMessage">
									<html:errors property="cvBirthDay" />
								</div>
							</logic:messagesPresent></td>
					</tr>

					<tr>
						<td><label for="cvSituation"> <bean:message
									key="cv.form.profile.situation" />
						</label></td>
						<td><bean:message key="cv.form.profile.sex.single" /><input type="radio"
							name="situation" value="<bean:message key="cv.form.profile.sex.single" />" />
							<bean:message key="cv.form.profile.sex.married" /><input type="radio"
							name="situation" value=" <bean:message key="cv.form.profile.sex.married" />" /></td>
					</tr>

					<tr>
						<td><label for="cvMail"> <bean:message
									key="cv.form.profile.mail" />
						</label></td>
						<td><html:text property="cvMail" styleId="cvMail"
								errorStyleClass="error" value="${sessionScope.user.email}" /> <logic:messagesPresent
								property="cvMail">
								<div class="errorMessage">
									<html:errors property="cvMail" />
								</div>
							</logic:messagesPresent></td>
					</tr>

					<tr>
						<td colspan="2" class="tableButton"><html:submit
								styleClass="button">
								<bean:message key="cv.button.next" />
							</html:submit></td>
					</tr>
				</table>
			</div>
		</fieldset>
	</div>
</html:form>

<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="js/cv.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.7.2.custom.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-i18n.min.js"></script>
<script type="text/javascript">
	$(function() {
		$.datepicker.setDefaults($.extend({

			dateFormat : 'dd/mm/yy',
			showOn : 'button',
			buttonImage : 'images/calendar.gif',
			buttonImageOnly : true,
			showMonthAfterYear : false
		}));
		$("#cvBirthDay").datepicker($.datepicker.regional['fr']);

	});
</script>
