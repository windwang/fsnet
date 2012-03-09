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
					<bean:message key="cv.titre" />
				</legend>
			</div>
			<div class="corp">
				<table class="inLineTableDashBoardFieldset fieldsetTable">
					<tr>
						<td><label for="CvTitle"><bean:message
									key="cv.form.title" /> : </label></td>
						<td><html:text property="CvTitle" styleId="CvTitle"
								errorStyleClass="error" /> <logic:messagesPresent
								property="CvTitle">
								<div class="errorMessage">
									<html:errors property="CvTitle" />
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
					<bean:message key="cv.contact" />
				</legend>
			</div>
			<div class="corp_contact">
				<table class="inLineTableDashBoardFieldset fieldsetTable">
					<tr>
						<td><label for="CvNom"><bean:message
									key="cv.form.name" /> : </label></td>
						<td><html:text property="CvNom" styleId="CvNom"
								errorStyleClass="error" value="${sessionScope.user.name}" /> <logic:messagesPresent
								property="CvNom">
								<div class="errorMessage">
									<html:errors property="CvNom" />
								</div>
							</logic:messagesPresent></td>
					</tr>

					<tr>
						<td><label for="CvPrenom"><bean:message
									key="cv.form.surname" /> : </label></td>
						<td><html:text property="CvPrenom" styleId="CvPrenom"
								errorStyleClass="error" value="${sessionScope.user.firstName}" />
							<logic:messagesPresent property="CvPrenom">
								<div class="errorMessage">
									<html:errors property="CvPrenom" />
								</div>
							</logic:messagesPresent></td>
					</tr>

					<tr>
						<td><label for="CvSexe"><bean:message
									key="members.sexe" /> : </label></td>
						<td><select name="CvSexe">

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
						<td><label for="CvAdresse"><bean:message
									key="cv.form.address" /> : </label></td>
						<td><html:text property="CvAdresse" styleId="CvAdresse"
								errorStyleClass="error"
								value="${sessionScope.user.address.address}" /> <logic:messagesPresent
								property="CvAdresse">
								<div class="errorMessage">
									<html:errors property="CvAdresse" />
								</div>
							</logic:messagesPresent></td>
					</tr>

					<tr>
						<td><label for="CvVille"><bean:message
									key="cv.form.city" /> : </label></td>
						<td><html:text property="CvVille" styleId="CvVille"
								errorStyleClass="error"
								value="${sessionScope.user.address.city}" /> <logic:messagesPresent
								property="CvVille">
								<div class="errorMessage">
									<html:errors property="CvVille" />
								</div>
							</logic:messagesPresent></td>
					</tr>

					<tr>
						<td><label for="CvCp"><bean:message key="cv.form.cp" />
								: </label></td>
						<td><html:text property="CvCp" styleId="CvCp"
								errorStyleClass="error" /> <logic:messagesPresent
								property="CvCp">
								<div class="errorMessage">
									<html:errors property="CvCp" />
								</div>
							</logic:messagesPresent></td>
					</tr>

					<tr>
						<td><label for="CvPays"> <bean:message
									key="cv.form.country" /> :
						</label></td>
						<td><html:text property="CvPays" styleId="CvPays"
								errorStyleClass="error" /> <logic:messagesPresent
								property="CvPays">
								<div class="errorMessage">
									<html:errors property="CvPays" />
								</div>
							</logic:messagesPresent></td>
					</tr>

					<tr>
						<td><label for="CvTel"> <bean:message
									key="cv.form.tel" /> :
						</label></td>
						<td><html:text property="CvTel" styleId="CvTel"
								errorStyleClass="error" value="${sessionScope.user.phone}" /> <logic:messagesPresent
								property="CvTel">
								<div class="errorMessage">
									<html:errors property="CvTel" />
								</div>
							</logic:messagesPresent></td>
					</tr>

					<tr>
						<td><label for="CvBirthDay"> <bean:message
									key="members.birthDay" /> :
						</label></td>
						<td><html:text errorStyleClass="error" styleId="CvBirthDay"
								property="CvBirthDay">
							</html:text> <logic:messagesPresent property="CvBirthDay">
								<div class="errorMessage">
									<html:errors property="CvBirthDay" />
								</div>
							</logic:messagesPresent></td>
					</tr>

					<tr>
						<td><label for="CvSituation"> <bean:message
									key="cv.form.situation" /> :
						</label></td>
						<td><bean:message key="cv.form.single" /><input type="radio"
							name="situation" value="<bean:message key="cv.form.single" />" />
							<bean:message key="cv.form.married" /><input type="radio"
							name="situation" value=" <bean:message key="cv.form.married" />" /></td>
					</tr>
					
					<tr>
						<td><label for="CvContact"> <bean:message
									key="cv.form.mail" /> :
						</label></td>
						<td><html:text property="CvContact" styleId="CvContact"
								errorStyleClass="error" value="${sessionScope.user.email}" /> <logic:messagesPresent
								property="CvContact">
								<div class="errorMessage">
									<html:errors property="CvContact" />
								</div>
							</logic:messagesPresent></td>
					</tr>

					<tr>
						<td colspan="2" align="right"><html:submit styleClass="button">
								<bean:message key="cv.suivant" />
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
		$("#CvBirthDay").datepicker($.datepicker.regional['fr']);

	});
</script>
