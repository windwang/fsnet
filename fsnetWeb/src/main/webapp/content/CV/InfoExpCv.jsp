<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html:form action="/CreateCv3">
	<div class="en_cv">
		<fieldset class="fieldsetAppli">
			<legend class="legendHome">
				<bean:message key="cv.title.experiences" />
			</legend>

			<table id="experiences" class="listeExperience inLineTable fieldsetTableAppli"></table>

			<div class="addExpTable">
				<table class="inLineTable fieldsetTableAppli">
					<tr>
						<td colspan="2"><a><span class="addExp"><bean:message
										key="cv.button.addExperience" /></span></a></td>

					</tr>
				</table>
			</div>

			<div class="corp_experience">
				<table class="inLineTable fieldsetTableAppli">
					<tr>
						<td><label for="cvExpJob">*<bean:message
									key="cv.form.exp.job" /></label></td>
						<td><html:text property="cvExpJob" styleId="cvExpJob"
								errorStyleClass="error" /> <span class="CvExpJobError errorCV"><bean:message
									key="error.CvPoste" /></span></td>
					</tr>

					<tr>
						<td><label for="cvExpFirmName">*<bean:message
									key="cv.form.exp.firm" /></label></td>
						<td><html:text property="cvExpFirmName"
								styleId="cvExpFirmName" errorStyleClass="error" /> <span
							class="CvExpFirmNameError errorCV"><bean:message
									key="error.NomEntreprise" /></span></td>
					</tr>

					<tr>
						<td><label for="cvExpDomain">*<bean:message
									key="cv.form.exp.domain" /></label></td>
						<td><html:text property="cvExpDomain" styleId="cvExpDomain"
								errorStyleClass="error" /> <span
							class="CvExpDomainError errorCV"><bean:message
									key="error.CvSecteur" /></span></td>
					</tr>

					<tr>
						<td><label for="cvExpCountry"><bean:message
									key="cv.form.country" /></label></td>
						<td><html:text property="cvExpCountry" styleId="cvExpCountry"
								errorStyleClass="error" /></td>
					</tr>

					<tr>
						<td><label for="cvExpCity"><bean:message
									key="cv.form.city" /></label></td>
						<td><html:text property="cvExpCity" styleId="cvExpCity"
								errorStyleClass="error" /></td>
					</tr>

					<tr>
						<td><label for=cvExpBeginDate><bean:message
									key="cv.form.dateBegin" />*</label></td>
						<td><html:text property="cvExpBeginDate"
								styleId="cvExpBeginDate" errorStyleClass="error" /><span
							class="CvExpBeginDateError errorCV"><bean:message
									key="error.expBeginDate" /></span></td>
					</tr>

					<tr>
						<td><label for="cvExpEndDate">*<bean:message
									key="cv.form.dateEnd" /></label></td>
						<td><html:text property="cvExpEndDate" styleId="cvExpEndDate"
								errorStyleClass="error" /> <span
							class="CvExpEndDateError errorCV"><bean:message
									key="error.expEndDate" /></span></td>
					</tr>

					<tr>
						<td colspan="2" class="tableButton"><a><span
								class="annuleExp"><bean:message key="cv.button.cancel" /></span></a>
							| <a><span class="SaveExp"><bean:message
										key="cv.button.save" /></span></a></td>
					</tr>
				</table>
			</div>
		</fieldset>
	</div>

	<div class="en_cv">
		<fieldset class="fieldsetAppli">
			<div class="entete">
				<legend class="legendHome">
					<bean:message key="cv.title.degree" />
				</legend>
			</div>

			<table id="diplome" class="listeDiplome inLineTable fieldsetTableAppli"></table>
			
			<div class="addDiplTable">
				<table class="inLineTable fieldsetTableAppli">
					<tr>
						<td colspan="2"><a><span class="addDip"><bean:message
										key="cv.button.addDegree" /></span></a></td>

					</tr>
				</table>
			</div>

			<div class="corp_diplome">
				<table class="inLineTable fieldsetTableAppli">
					<tr>
						<td><label for="cvDegreeName">*<bean:message
									key="cv.form.degree.level" /></label></td>
						<!--<td><html:text property="cvDegreeName" styleId="cvDegreeName"
								errorStyleClass="error" /><span class="CvDegreeNameError errorCV"><bean:message
									key="error.CvEtude" /></span></td>-->
						<td><select name="cvDegreeName" id="cvDegreeName">
								<option value="" />
								<option value="+0">+0</option>
								<option value="+1">+1</option>
								<option value="+2">+2</option>
								<option value="+3">+3</option>
								<option value="+4">+4</option>
								<option value="+5">+5</option>
								<option value="+6">+6</option>
								<option value="+7">+7</option>
								<option value="+8">+8</option>
								<option value="+9">+9</option>
								<option value="+10">+10</option>
								<option value="+11">+11</option>
								<option value="+12">+12</option>
						</select><span class="CvDegreeNameError errorCV"> <bean:message
									key="error.CvEtude" /></span></td>
					</tr>

					<tr>
						<td><label for="cvDegreeDomain">*<bean:message
									key="cv.form.degree.domain" /></label></td>
						<td><html:text property="cvDegreeDomain"
								styleId="cvDegreeDomain" errorStyleClass="error" /> <span
							class="CvDegreeDomainError errorCV"><bean:message
									key="error.CvEtudeDom" /></span></td>
					</tr>

					<tr>
						<td><label for="cvDegreeSchool">*<bean:message
									key="cv.form.school" /></label></td>
						<td><html:text property="cvDegreeSchool"
								styleId="cvDegreeSchool" errorStyleClass="error" /> <span
							class="CvDegreeSchoolError errorCV"><bean:message
									key="error.CvEtablissment" /></span></td>
					</tr>
					<tr>
						<td><label for="cvDegreeCountry"><bean:message
									key="cv.form.country" /></label></td>
						<td><html:text property="cvDegreeCountry"
								styleId="cvDegreeCountry" errorStyleClass="error" /></td>
					</tr>

					<tr>
						<td><label for="cvDegreeCity"><bean:message
									key="cv.form.city" /></label></td>
						<td><html:text property="cvDegreeCity" styleId="cvDegreeCity"
								errorStyleClass="error" /></td>
					</tr>

					<tr>
						<td><label for="cvDegreeBeginDate">*<bean:message
									key="cv.form.dateBegin" />
						</label></td>
						<td><html:text property="cvDegreeBeginDate"
								styleId="cvDegreeBeginDate" errorStyleClass="error" /> <span
							class="CvDegreeBeginDateError errorCV"><bean:message
									key="error.etudBeginDate" /></span></td>
					</tr>

					<tr>
						<td><label for="cvDegreeEndDate">*<bean:message
									key="cv.form.dateEnd" /></label></td>
						<td><html:text property="cvDegreeEndDate"
								styleId="cvDegreeEndDate" errorStyleClass="error" /> <span
							class="CvDegreeEndDateError errorCV"><bean:message
									key="error.etudEndDate" /></span></td>
					</tr>

					<tr>
						<td colspan="2" class="tableButton"><a><span
								class="annuleDip"><bean:message key="cv.button.cancel" /></span></a>
							| <a><span class="SaveDip"><bean:message
										key="cv.button.save" /> </span></a></td>
					</tr>
				</table>
			</div>
		</fieldset>
	</div>

	<div class="en_cv">
		<fieldset class="fieldsetAppli">
			<div class="entete">
				<legend class="legendHome">
					<bean:message key="cv.title.formation" />
				</legend>
			</div>

			<table id="formation" class="listeFormation inLineTable fieldsetTableAppli"></table>
			
			<div class="addFormTable">
				<table class="inLineTable fieldsetTableAppli">
					<tr>
						<td colspan="2"><a><span class="addForm"><bean:message
										key="cv.button.addFormation" /></span></a></td>

					</tr>
				</table>
			</div>

			<div class="corp_formation">
				<table class="inLineTable fieldsetTableAppli">
					<tr>
						<td><label for="cvTrainingName">*<bean:message
									key="cv.form.training.name" /></label></td>
						<td><html:text property="cvTrainingName"
								styleId="cvTrainingName" errorStyleClass="error" /> <span
							class="CvTrainingNameError errorCV"><bean:message
									key="error.CvFormation" /></span></td>
					</tr>

					<tr>
						<td><label for="cvTrainingInstitution">*<bean:message
									key="cv.form.school" /></label></td>
						<td><html:text property="cvTrainingInstitution"
								styleId="cvTrainingInstitution" errorStyleClass="error" /> <span
							class="CvTrainingInstitutionError errorCV"><bean:message
									key="error.CvEtablissmentform" /></span></td>
					</tr>

					<tr>
						<td><label for="cvTrainingCountry"><bean:message
									key="cv.form.country" /></label></td>
						<td><html:text property="cvTrainingCountry"
								styleId="cvTrainingCountry" errorStyleClass="error" /></td>
					</tr>

					<tr>
						<td><label for="cvTrainingCity"><bean:message
									key="cv.form.city" /></label></td>
						<td><html:text property="cvTrainingCity"
								styleId="cvTrainingCity" errorStyleClass="error" /></td>
					</tr>

					<tr>
						<td><label for="cvTrainingObtainingDate">*<bean:message
									key="cv.form.training.dateObtaining" />
						</label></td>
						<td><html:text property="cvTrainingObtainingDate"
								styleId="cvTrainingObtainingDate" errorStyleClass="error" /> <span
							class="CvTrainingObtainingDateError errorCV"><bean:message
									key="error.DateObtention" /></span></td>
					</tr>

					<tr>
						<td colspan="2" class="tableButton"><a><span
								class="annuleForm"><bean:message key="cv.button.cancel" /></span></a>
							| <a><span class="SaveForm"><bean:message
										key="cv.button.save" /> </span></a></td>
					</tr>
				</table>
			</div>
		</fieldset>
	</div>

	<div class="en_cv">
		<fieldset class="fieldsetAppli">
			<div class="entete">
				<legend class="legendHome">
					<bean:message key="cv.title.hobbies" />
				</legend>
			</div>

			<table id="loisir" class="listeLoisir inLineTable fieldsetTableAppli"></table>

			<div class="addLoisirTable">
				<table class="inLineTable fieldsetTableAppli">
					<tr>
						<td colspan="2"><a><span class="addLoisir"><bean:message
										key="cv.button.addHobby" /> </span> </a></td>

					</tr>
				</table>
			</div>

			<div class="corp_loisir">
				<table class="inLineTable fieldsetTableAppli">
					<tr>
						<td><label for="cvHobbyName">*<bean:message
									key="cv.form.hobby.name" /></label></td>
						<td><html:text property="cvHobbyName" styleId="cvHobbyName"
								errorStyleClass="error" /> <span
							class="CvHobbyNameError errorCV"><bean:message
									key="error.CvNomLoisir" /></span></td>
					</tr>

					<tr>
						<td colspan="2" class="tableButton"><a><span
								class="annuleLoisir"><bean:message key="cv.button.cancel" /></span></a>
							| <a><span class="SaveLoisir"><bean:message
										key="cv.button.save" /> </span></a></td>
					</tr>
				</table>
			</div>
		</fieldset>
	</div>

	<div class="en_cv">
		<fieldset class="fieldsetAppli">
			<div class="entete">
				<legend class="legendHome">
					<bean:message key="cv.title.languages" />
				</legend>
			</div>

			<table id="langue" class="listeLangues inLineTable fieldsetTableAppli"></table>

			<div class="addLangueTable">
				<table class="inLineTable fieldsetTableAppli">
					<tr>
						<td colspan="2"><a><span class="addLangue"><bean:message
										key="cv.button.addLanguage" /></span></a></td>

					</tr>
				</table>
			</div>

			<div class="corp_langue">
				<table class="inLineTable fieldsetTableAppli">
					<tr>
						<td><label for="cvLangName">*<bean:message
									key="cv.form.lang.name" /></label></td>
						<td><input type="text" name="cvLangName" id="cvLangName" />
							<span class="CvLangNameError errorCV"><bean:message
									key="error.CVLangue" /></span></td>
					</tr>

					<tr>
						<td><label for="cvLangLevel">*<bean:message
									key="cv.form.lang.level" /></label></td>
						<td><select name="cvLangLevel" id="cvLangLevel">
								<option value="<bean:message key="cv.form.lang.level0" />"
									selected="selected">
									<bean:message key="cv.form.lang.level0" />
								</option>
								<option value="<bean:message key="cv.form.lang.level1"/>">
									<bean:message key="cv.form.lang.level1" />
								</option>
								<option value="<bean:message key="cv.form.lang.level2"/>">
									<bean:message key="cv.form.lang.level2" />
								</option>
								<option value="<bean:message key="cv.form.lang.level3"/>">
									<bean:message key="cv.form.lang.level3" />
								</option>
						</select></td>
					</tr>
					<tr>
						<td colspan="2" class="tableButton"><a><span
								class="annuleLangue"><bean:message key="cv.button.cancel" /></span></a>
							| <a><span class="SaveLangue"><bean:message
										key="cv.button.save" /></span></a></td>
					</tr>
				</table>
			</div>
		</fieldset>
	</div>

	<html:submit styleId="envoi" styleClass="button">
		<bean:message key="cv.button.create" />
	</html:submit>
</html:form>

<script type="text/javascript" src="js/cv.js"></script>
<script type="text/javascript">
	$(function() {
		$.datepicker.setDefaults($.extend({
			dateFormat : 'dd/mm/yy',
			showOn : 'both',
			buttonImage : 'images/calendar.gif',
			buttonImageOnly : true,
			showMonthAfterYear : false
		}));
		$.datepicker.setDefaults($.datepicker.regional['fr']);

		$("#cvExpBeginDate").datepicker();
		$("#cvExpEndDate").datepicker();
		$("#cvDegreeBeginDate").datepicker();
		$("#cvDegreeEndDate").datepicker();
		$("#cvTrainingObtainingDate").datepicker();
	});
</script>