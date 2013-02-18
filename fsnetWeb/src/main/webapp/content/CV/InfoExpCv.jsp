<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<s:form action="/CreateCv3">
	<div class="en_cv">
		<fieldset class="fieldsetCadre">
			<legend>
				<s:text name="cv.title.experiences" />
			</legend>

			<table id="experiences"
				class="listeExperience inLineTable   tableStyle"></table>

			<div class="addExpTable">
				<table class="inLineTable   tableStyle">
					<tr>
						<td colspan="2"><a><span class="addExp"><s:text name="cv.button.addExperience" /></span></a></td>

					</tr>
				</table>
			</div>

			<div class="corp_experience">
				<table class="inLineTable   tableStyle">
					<tr>
						<td><label for="cvExpJob">*<s:text name="cv.form.exp.job" /></label></td>
						<td><s:textfield property="cvExpJob" styleId="cvExpJob"
								errorStyleClass="error" /> <span class="CvExpJobError errorCV"><s:text name="error.CvPoste" /></span></td>
					</tr>

					<tr>
						<td><label for="cvExpFirmName">*<s:text name="cv.form.exp.firm" /></label></td>
						<td><s:textfield property="cvExpFirmName"
								styleId="cvExpFirmName" errorStyleClass="error" /> <span
							class="CvExpFirmNameError errorCV"><s:text name="error.NomEntreprise" /></span></td>
					</tr>

					<tr>
						<td><label for="cvExpDomain">*<s:text name="cv.form.exp.domain" /></label></td>
						<td><s:textfield property="cvExpDomain" styleId="cvExpDomain"
								errorStyleClass="error" /> <span
							class="CvExpDomainError errorCV"><s:text name="error.CvSecteur" /></span></td>
					</tr>

					<tr>
						<td><label for="cvExpCountry"><s:text name="cv.form.country" /></label></td>
						<td><s:textfield property="cvExpCountry" styleId="cvExpCountry"
								errorStyleClass="error" /></td>
					</tr>

					<tr>
						<td><label for="cvExpCity"><s:text name="cv.form.city" /></label></td>
						<td><s:textfield property="cvExpCity" styleId="cvExpCity"
								errorStyleClass="error" /></td>
					</tr>

					<tr>
						<td><label for=cvExpBeginDate><s:text name="cv.form.dateBegin" />*</label></td>
						<td><s:textfield property="cvExpBeginDate"
								styleId="cvExpBeginDate" errorStyleClass="error" /><span
							class="CvExpBeginDateError errorCV"><s:text name="error.expBeginDate" /></span></td>
					</tr>

					<tr>
						<td><label for="cvExpEndDate">*<s:text name="cv.form.dateEnd" /></label></td>
						<td><s:textfield property="cvExpEndDate" styleId="cvExpEndDate"
								errorStyleClass="error" /> <span
							class="CvExpEndDateError errorCV"><s:text name="error.expEndDate" /></span></td>
					</tr>

					<tr>
						<td colspan="2" class="tableButton"><a><span
								class="annuleExp"><s:text name="cv.button.cancel" /></span></a> | <a><span
								class="SaveExp"><s:text name="cv.button.save" /></span></a></td>
					</tr>
				</table>
			</div>
		</fieldset>
	</div>

	<div class="en_cv">
		<fieldset class="fieldsetCadre">
			<div class="entete">
				<legend>
					<s:text name="cv.title.degree" />
				</legend>
			</div>

			<table id="diplome" class="listeDiplome inLineTable   tableStyle"></table>

			<div class="addDiplTable">
				<table class="inLineTable   tableStyle">
					<tr>
						<td colspan="2"><a><span class="addDip"><s:text name="cv.button.addDegree" /></span></a></td>

					</tr>
				</table>
			</div>

			<div class="corp_diplome">
				<table class="inLineTable   tableStyle">
					<tr>
						<td><label for="cvDegreeName">*<s:text name="cv.form.degree.level" /></label></td>
						<!--<td><s:textfield property="cvDegreeName" styleId="cvDegreeName"
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
						</select><span class="CvDegreeNameError errorCV"> <s:text name="error.CvEtude" /></span></td>
					</tr>

					<tr>
						<td><label for="cvDegreeDomain">*<s:text name="cv.form.degree.domain" /></label></td>
						<td><s:textfield property="cvDegreeDomain"
								styleId="cvDegreeDomain" errorStyleClass="error" /> <span
							class="CvDegreeDomainError errorCV"><s:text name="error.CvEtudeDom" /></span></td>
					</tr>

					<tr>
						<td><label for="cvDegreeSchool">*<s:text name="cv.form.school" /></label></td>
						<td><s:textfield property="cvDegreeSchool"
								styleId="cvDegreeSchool" errorStyleClass="error" /> <span
							class="CvDegreeSchoolError errorCV"><s:text name="error.CvEtablissment" /></span></td>
					</tr>
					<tr>
						<td><label for="cvDegreeCountry"><s:text name="cv.form.country" /></label></td>
						<td><s:textfield property="cvDegreeCountry"
								styleId="cvDegreeCountry" errorStyleClass="error" /></td>
					</tr>

					<tr>
						<td><label for="cvDegreeCity"><s:text name="cv.form.city" /></label></td>
						<td><s:textfield property="cvDegreeCity" styleId="cvDegreeCity"
								errorStyleClass="error" /></td>
					</tr>

					<tr>
						<td><label for="cvDegreeBeginDate">*<s:text name="cv.form.dateBegin" />
						</label></td>
						<td><s:textfield property="cvDegreeBeginDate"
								styleId="cvDegreeBeginDate" errorStyleClass="error" /> <span
							class="CvDegreeBeginDateError errorCV"><s:text name="error.etudBeginDate" /></span></td>
					</tr>

					<tr>
						<td><label for="cvDegreeEndDate">*<s:text name="cv.form.dateEnd" /></label></td>
						<td><s:textfield property="cvDegreeEndDate"
								styleId="cvDegreeEndDate" errorStyleClass="error" /> <span
							class="CvDegreeEndDateError errorCV"><s:text name="error.etudEndDate" /></span></td>
					</tr>

					<tr>
						<td colspan="2" class="tableButton"><a><span
								class="annuleDip"><s:text name="cv.button.cancel" /></span></a> | <a><span
								class="SaveDip"><s:text name="cv.button.save" /> </span></a></td>
					</tr>
				</table>
			</div>
		</fieldset>
	</div>

	<div class="en_cv">
		<fieldset class="fieldsetCadre">
			<div class="entete">
				<legend>
					<s:text name="cv.title.formation" />
				</legend>
			</div>

			<table id="formation" class="listeFormation inLineTable   tableStyle"></table>

			<div class="addFormTable">
				<table class="inLineTable   tableStyle">
					<tr>
						<td colspan="2"><a><span class="addForm"><s:text name="cv.button.addFormation" /></span></a></td>

					</tr>
				</table>
			</div>

			<div class="corp_formation">
				<table class="inLineTable   tableStyle">
					<tr>
						<td><label for="cvTrainingName">*<s:text name="cv.form.training.name" /></label></td>
						<td><s:textfield property="cvTrainingName"
								styleId="cvTrainingName" errorStyleClass="error" /> <span
							class="CvTrainingNameError errorCV"><s:text name="error.CvFormation" /></span></td>
					</tr>

					<tr>
						<td><label for="cvTrainingInstitution">*<s:text name="cv.form.school" /></label></td>
						<td><s:textfield property="cvTrainingInstitution"
								styleId="cvTrainingInstitution" errorStyleClass="error" /> <span
							class="CvTrainingInstitutionError errorCV"><s:text
									name="error.CvEtablissmentform" /></span></td>
					</tr>

					<tr>
						<td><label for="cvTrainingCountry"><s:text
									name="cv.form.country" /></label></td>
						<td><s:textfield property="cvTrainingCountry"
								styleId="cvTrainingCountry" errorStyleClass="error" /></td>
					</tr>

					<tr>
						<td><label for="cvTrainingCity"><s:text
									name="cv.form.city" /></label></td>
						<td><s:textfield property="cvTrainingCity"
								styleId="cvTrainingCity" errorStyleClass="error" /></td>
					</tr>

					<tr>
						<td><label for="cvTrainingObtainingDate">*<s:text
									name="cv.form.training.dateObtaining" />
						</label></td>
						<td><s:textfield property="cvTrainingObtainingDate"
								styleId="cvTrainingObtainingDate" errorStyleClass="error" /> <span
							class="CvTrainingObtainingDateError errorCV"><s:text
									name="error.DateObtention" /></span></td>
					</tr>

					<tr>
						<td colspan="2" class="tableButton"><a><span
								class="annuleForm"><s:text name="cv.button.cancel" /></span></a> |
							<a><span class="SaveForm"><s:text
										name="cv.button.save" /> </span></a></td>
					</tr>
				</table>
			</div>
		</fieldset>
	</div>

	<div class="en_cv">
		<fieldset class="fieldsetCadre">
			<div class="entete">
				<legend>
					<s:text name="cv.title.hobbies" />
				</legend>
			</div>

			<table id="loisir" class="listeLoisir inLineTable   tableStyle"></table>

			<div class="addLoisirTable">
				<table class="inLineTable   tableStyle">
					<tr>
						<td colspan="2"><a><span class="addLoisir"><s:text
										name="cv.button.addHobby" /> </span> </a></td>

					</tr>
				</table>
			</div>

			<div class="corp_loisir">
				<table class="inLineTable   tableStyle">
					<tr>
						<td><label for="cvHobbyName">*<s:text
									name="cv.form.hobby.name" /></label></td>
						<td><s:textfield property="cvHobbyName" styleId="cvHobbyName"
								errorStyleClass="error" /> <span
							class="CvHobbyNameError errorCV"><s:text
									name="error.CvNomLoisir" /></span></td>
					</tr>

					<tr>
						<td colspan="2" class="tableButton"><a><span
								class="annuleLoisir"><s:text name="cv.button.cancel" /></span></a>
							| <a><span class="SaveLoisir"><s:text
										name="cv.button.save" /> </span></a></td>
					</tr>
				</table>
			</div>
		</fieldset>
	</div>

	<div class="en_cv">
		<fieldset class="fieldsetCadre">
			<div class="entete">
				<legend>
					<s:text name="cv.title.languages" />
				</legend>
			</div>

			<table id="langue" class="listeLangues inLineTable   tableStyle"></table>

			<div class="addLangueTable">
				<table class="inLineTable   tableStyle">
					<tr>
						<td colspan="2"><a><span class="addLangue"><s:text
										name="cv.button.addLanguage" /></span></a></td>

					</tr>
				</table>
			</div>

			<div class="corp_langue">
				<table class="inLineTable   tableStyle">
					<tr>
						<td><label for="cvLangName">*<s:text
									name="cv.form.lang.name" /></label></td>
						<td><input type="text" name="cvLangName" id="cvLangName" />
							<span class="CvLangNameError errorCV"><s:text
									name="error.CVLangue" /></span></td>
					</tr>

					<tr>
						<td><label for="cvLangLevel">*<s:text
									name="cv.form.lang.level" /></label></td>
						<td><select name="cvLangLevel" id="cvLangLevel">
								<option value="<s:text name="cv.form.lang.level0" />"
									selected="selected">
									<s:text name="cv.form.lang.level0" />
								</option>
								<option value="<s:text name="cv.form.lang.level1"/>">
									<s:text name="cv.form.lang.level1" />
								</option>
								<option value="<s:text name="cv.form.lang.level2"/>">
									<s:text name="cv.form.lang.level2" />
								</option>
								<option value="<s:text name="cv.form.lang.level3"/>">
									<s:text name="cv.form.lang.level3" />
								</option>
						</select></td>
					</tr>
					<tr>
						<td colspan="2" class="tableButton"><a><span
								class="annuleLangue"><s:text name="cv.button.cancel" /></span></a>
							| <a><span class="SaveLangue"><s:text
										name="cv.button.save" /></span></a></td>
					</tr>
				</table>
			</div>
		</fieldset>
	</div>

	<s:submit styleId="envoi" styleClass="btn btn-inverse">
		<s:text name="cv.button.create" />
	</s:submit>
</s:form>

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