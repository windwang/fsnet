<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<s:form action="/CreateCv2">
	<div class="en_cv">
		<fieldset class="fieldsetCadre">
			<div class="entete">
				<legend>
					<s:text name="cv.title.name" />
				</legend>
			</div>
			<div class="corp">
				<table class="inLineTable  tableStyle">
					<tr>
						<td><label for="cvTitle"><s:text name="cv.form.title" /></label></td>
						<td><s:textfield property="cvTitle" styleId="cvTitle"
								cssStyleClass="error" /></td>
					</tr>
				</table>
			</div>
		</fieldset>
	</div>

	<div class="en_cv">
		<fieldset class="fieldsetCadre">
			<div class="entete">
				<legend>
					<s:text name="cv.title.info" />
				</legend>

			</div>
			<div class="corp_contact">
				<table class="inLineTable  tableStyle">
					<tr>
						<td><label for="cvFirstname"><s:text
									name="cv.form.profile.firstname" /></label></td>
						<td><c:choose>
								<c:when test="${sessionScope.action}">
									<s:textfield property="cvFirstname" styleId="cvFirstname"
										cssStyleClass="error" />
								</c:when>

								<c:otherwise>
									<s:textfield property="cvFirstname" styleId="cvFirstname"
										cssStyleClass="error" value="%{sessionScope.user.name}" />
								</c:otherwise>
							</c:choose> </td>
					</tr>

					<tr>
						<td><label for="cvSurname"><s:text
									name="cv.form.profile.surname" /></label></td>
						<td><c:choose>
								<c:when test="${sessionScope.action}">
									<s:textfield property="cvSurname" styleId="cvFirstname"
										cssStyleClass="error" />
								</c:when>
								<c:otherwise>
									<s:textfield property="cvSurname" styleId="cvSurname"
										cssStyleClass="error" value="%{sessionScope.user.firstName}" />
								</c:otherwise>
							</c:choose> </td>
					</tr>

					<tr>
						<td><label for="cvSexe"><s:text
									name="cv.form.profile.sex" /></label></td>
						<td><select name="cvSexe" id="cvSexe">
								<c:choose>
									<c:when test="${sessionScope.user.sex == 'male'}">
										<option value=""></option>
										<option value="male" selected="selected">
											<s:text name="members.sexe.Male" />
										</option>
										<option value="female">
											<s:text name="members.sexe.Female" />
										</option>
									</c:when>
									<c:when test="${sessionScope.user.sex == 'female'}">
										<option value=""></option>
										<option value="male">
											<s:text name="members.sexe.Male" />
										</option>
										<option value="female" selected="selected">
											<s:text name="members.sexe.Female" />
										</option>
									</c:when>
									<c:otherwise>
										<option value=""></option>
										<option value="male">
											<s:text name="members.sexe.Male" />
										</option>
										<option value="female">
											<s:text name="members.sexe.Female" />
										</option>
									</c:otherwise>
								</c:choose>
						</select></td>
					</tr>

					<tr>
						<td><label for="cvAddress"><s:text
									name="cv.form.profile.address" /></label></td>
						<td><c:choose>
								<c:when test="${sessionScope.action}">
									<s:textfield property="cvAddress" styleId="cvFirstname"
										cssStyleClass="error" />
								</c:when>
								<c:otherwise>
									<s:textfield property="cvAddress" styleId="cvAddress"
										cssStyleClass="error"
										value="%{sessionScope.user.address.address}" />
								</c:otherwise>
							</c:choose> </td>
					</tr>

					<tr>
						<td><label for="cvCity"><s:text
									name="cv.form.profile.city" /></label></td>
						<td><c:choose>
								<c:when test="${sessionScope.action}">
									<s:textfield property="cvCity" styleId="cvFirstname"
										cssStyleClass="error" />
								</c:when>
								<c:otherwise>
									<s:textfield property="cvCity" styleId="cvCity"
										cssStyleClass="error"
										value="%{sessionScope.user.address.city}" />
								</c:otherwise>
							</c:choose> </td>
					</tr>

					<tr>
						<td><label for="cvCP"><s:text
									name="cv.form.profile.cp" /></label></td>
						<td><s:textfield property="cvCP" styleId="cvCP"
								cssStyleClass="error" /> </td>
					</tr>

					<tr>
						<td><label for="cvCountry"> <s:text
									name="cv.form.profile.country" /></label></td>
						<td><s:textfield property="cvCountry" styleId="cvCountry"
								cssStyleClass="error" /> </td>
					</tr>

					<tr>
						<td><label for="cvPhone"> <s:text
									name="cv.form.profile.phone" />
						</label></td>
						<td><c:choose>
								<c:when test="${sessionScope.action}">
									<s:textfield property="cvPhone" styleId="cvFirstname"
										cssStyleClass="error" />
								</c:when>
								<c:otherwise>
									<s:textfield property="cvPhone" styleId="cvPhone"
										cssStyleClass="error" value="%{sessionScope.user.phone}" />
								</c:otherwise>
							</c:choose> </td>
					</tr>

					<tr>
						<td><label for="cvBirthDay"> <s:text
									name="cv.form.profile.birthday" />
						</label></td>
						<td><s:textfield cssStyleClass="error" styleId="cvBirthDay"
								property="cvBirthDay" disabled="false" /></td>
					</tr>

					<tr>
						<td><label for="cvSituation"> <s:text
									name="cv.form.profile.situation" />
						</label></td>
						<td><label for="single"><s:text
									name="cv.form.profile.sex.single" /></label><input type="radio"
							name="situation" id="single"
							value="<s:text name="cv.form.profile.sex.single" />" /> <label
							for="married"><s:text name="cv.form.profile.sex.married" /></label><input
							type="radio" name="situation" id="married"
							value=" <s:text name="cv.form.profile.sex.married" />" /></td>
					</tr>

					<tr>
						<td><label for="cvMail"> <s:text
									name="cv.form.profile.mail" />
						</label></td>
						<td><c:choose>
								<c:when test="${sessionScope.action}">
									<s:textfield property="cvMail" styleId="cvFirstname"
										cssStyleClass="error" />
								</c:when>
								<c:otherwise>
									<s:textfield property="cvMail" styleId="cvMail"
										cssStyleClass="error" value="%{sessionScope.user.email}" />
								</c:otherwise>
							</c:choose></td>
					</tr>

					<tr>
						<td colspan="2" class="tableButton"><s:submit type="button"
								styleClass="btn btn-inverse">
								<s:text name="cv.button.next" />
							</s:submit></td>
					</tr>
				</table>
			</div>
		</fieldset>
	</div>
</s:form>

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