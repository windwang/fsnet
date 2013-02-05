<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html:form action="/CreateCv2">
	<div class="en_cv">
		<fieldset class="fieldsetCadre">
			<div class="entete">
				<legend>
					<bean:message key="cv.title.name" />
				</legend>
			</div>
			<div class="corp">
				<table class="inLineTable fieldsetTableAppli tableStyle">
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
		<fieldset class="fieldsetCadre">
			<div class="entete">
				<legend>
					<bean:message key="cv.title.info" />
				</legend>

			</div>
			<div class="corp_contact">
				<table class="inLineTable fieldsetTableAppli tableStyle">
					<tr>
						<td><label for="cvFirstname"><bean:message
									key="cv.form.profile.firstname" /></label></td>
						<td><c:choose>
								<c:when test="${sessionScope.action}">
									<html:text property="cvFirstname" styleId="cvFirstname"
										errorStyleClass="error" />
								</c:when>

								<c:otherwise>
									<html:text property="cvFirstname" styleId="cvFirstname"
										errorStyleClass="error" value="${sessionScope.user.name}" />
								</c:otherwise>
							</c:choose> <logic:messagesPresent property="cvFirstname">
								<div class="errorMessage">
									<html:errors property="cvFirstname" />
								</div>
							</logic:messagesPresent></td>
					</tr>

					<tr>
						<td><label for="cvSurname"><bean:message
									key="cv.form.profile.surname" /></label></td>
						<td><c:choose>
								<c:when test="${sessionScope.action}">
									<html:text property="cvSurname" styleId="cvFirstname"
										errorStyleClass="error" />
								</c:when>
								<c:otherwise>
									<html:text property="cvSurname" styleId="cvSurname"
										errorStyleClass="error" value="${sessionScope.user.firstName}" />
								</c:otherwise>
							</c:choose> <logic:messagesPresent property="cvSurname">
								<div class="errorMessage">
									<html:errors property="cvSurname" />
								</div>
							</logic:messagesPresent></td>
					</tr>

					<tr>
						<td><label for="cvSexe"><bean:message
									key="cv.form.profile.sex" /></label></td>
						<td><select name="cvSexe" id="cvSexe">
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
						<td><c:choose>
								<c:when test="${sessionScope.action}">
									<html:text property="cvAddress" styleId="cvFirstname"
										errorStyleClass="error" />
								</c:when>
								<c:otherwise>
									<html:text property="cvAddress" styleId="cvAddress"
										errorStyleClass="error"
										value="${sessionScope.user.address.address}" />
								</c:otherwise>
							</c:choose> <logic:messagesPresent property="cvAddress">
								<div class="errorMessage">
									<html:errors property="cvAddress" />
								</div>
							</logic:messagesPresent></td>
					</tr>

					<tr>
						<td><label for="cvCity"><bean:message
									key="cv.form.profile.city" /></label></td>
						<td><c:choose>
								<c:when test="${sessionScope.action}">
									<html:text property="cvCity" styleId="cvFirstname"
										errorStyleClass="error" />
								</c:when>
								<c:otherwise>
									<html:text property="cvCity" styleId="cvCity"
										errorStyleClass="error"
										value="${sessionScope.user.address.city}" />
								</c:otherwise>
							</c:choose> <logic:messagesPresent property="cvCity">
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
								errorStyleClass="error" /> <logic:messagesPresent
								property="cvCountry">
								<div class="errorMessage">
									<html:errors property="cvCountry" />
								</div>
							</logic:messagesPresent></td>
					</tr>

					<tr>
						<td><label for="cvPhone"> <bean:message
									key="cv.form.profile.phone" />
						</label></td>
						<td><c:choose>
								<c:when test="${sessionScope.action}">
									<html:text property="cvPhone" styleId="cvFirstname"
										errorStyleClass="error" />
								</c:when>
								<c:otherwise>
									<html:text property="cvPhone" styleId="cvPhone"
										errorStyleClass="error" value="${sessionScope.user.phone}" />
								</c:otherwise>
							</c:choose> <logic:messagesPresent property="cvPhone">
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
								property="cvBirthDay" disabled="false" /> <logic:messagesPresent
								property="cvBirthDay">
								<div class="errorMessage">
									<html:errors property="cvBirthDay" />
								</div>
							</logic:messagesPresent></td>
					</tr>

					<tr>
						<td><label for="cvSituation"> <bean:message
									key="cv.form.profile.situation" />
						</label></td>
						<td><label for="single"><bean:message
									key="cv.form.profile.sex.single" /></label><input type="radio"
							name="situation" id="single"
							value="<bean:message key="cv.form.profile.sex.single" />" /> <label
							for="married"><bean:message key="cv.form.profile.sex.married" /></label><input
							type="radio" name="situation" id="married"
							value=" <bean:message key="cv.form.profile.sex.married" />" /></td>
					</tr>

					<tr>
						<td><label for="cvMail"> <bean:message
									key="cv.form.profile.mail" />
						</label></td>
						<td><c:choose>
								<c:when test="${sessionScope.action}">
									<html:text property="cvMail" styleId="cvFirstname"
										errorStyleClass="error" />
								</c:when>
								<c:otherwise>
									<html:text property="cvMail" styleId="cvMail"
										errorStyleClass="error" value="${sessionScope.user.email}" />
								</c:otherwise>
							</c:choose> <logic:messagesPresent property="cvMail">
								<div class="errorMessage">
									<html:errors property="cvMail" />
								</div>
							</logic:messagesPresent></td>
					</tr>

					<tr>
						<td colspan="2" class="tableButton"><html:submit
								styleClass="btn btn-inverse">
								<bean:message key="cv.button.next" />
							</html:submit></td>
					</tr>
				</table>
			</div>
		</fieldset>
	</div>
</html:form>

<script type="text/javascript" src="js/cv.js"></script>
<script type="text/javascript">
	$(function() {
		$.datepicker.setDefaults($.extend({
			yearRange : '-100:+100',
			minDate : '-100y',
			changeYear : true,
			dateFormat : 'dd/mm/yy',
			maxDate : '+0',
			showOn : 'both',
			buttonImage : 'images/calendar.gif',
			buttonImageOnly : true,
			showMonthAfterYear : false
		}));
		$.datepicker.setDefaults($.datepicker.regional['fr']);

		$("#cvBirthDay").datepicker();

	});
</script>