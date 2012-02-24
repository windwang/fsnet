<%-- 
    Document   : Information
    Created on : 07 janv
    Author     : BENZAOUIA Anass <anassbenzaouia at gmail.com>
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html:form action="/GenerateCV">



	<div class="en_cv">
	<fieldset class="fieldsetAppli">
		<div class="entete">
			 <legend class="legendHome">
				<bean:message key="cv.experiencePro" />
			</legend>
		</div>
		<div id="experiences">
			<ul class="listeExperience fieldsetTableAdmin">


			</ul>
		</div>
		<div class="corp_experience">
			<table class="fieldsetTableAdmin">

				<tr>
					<td>*<bean:message key="cv.10" /></td>
				</tr>
				<tr>
					<td><html:text property="CvPoste" styleId="CvPoste"
							errorStyleClass="error" /></td>
					<td><span class="CvPosteError errorCV"><bean:message
								key="error.CvPoste" /></span></td>
				</tr>
				<tr>
					<td>*<bean:message key="cv.11" /></td>
				</tr>
				<tr>
					<td><html:text property="NomEntreprise"
							styleId="NomEntreprise" errorStyleClass="error" /></td>
					<td><span class="NomEntrepriseError errorCV"><bean:message
								key="error.NomEntreprise" /></span></td>
				</tr>
				<tr>
					<td>*<bean:message key="cv.secteur" /></td>
				</tr>
				<tr>
					<td><html:text property="CvSecteur" styleId="CvSecteur"
							errorStyleClass="error" /></td>
					<td><span class="CvSecteurError errorCV"><bean:message
								key="error.CvSecteur" /></span></td>
				</tr>
				
				<tr>
					<td><bean:message key="cv.18" /></td>
					<td><bean:message key="cv.19" /></td>
				</tr>
				<tr>
					<td><html:text property="CvPaysExp" styleId="CvPaysExp"
							errorStyleClass="error" /></td>
					<td>
						<html:text property="CvVilleExp" styleId="CvVilleExp"
							errorStyleClass="error" /></td>
				</tr>

				<tr>
					<td>*<label for="expDate"> <bean:message key="cv.12" />
							:
					</label>
					</td>
				</tr>
				<tr>
					<td><html:text property="expBeginDate" styleId="expBeginDate"
							errorStyleClass="error" /> <logic:messagesPresent
							property="expBeginDate">
							<div class="errorMessage">
								<html:errors property="expBeginDate" />
							</div>
						</logic:messagesPresent></td>
					<td><span class="expBeginDateError errorCV"><bean:message
								key="error.expBeginDate" /></span></td>
				</tr>
				<tr>
					<td>*<label for="expDate"> <bean:message key="cv.13" />
							:
					</label>
					</td>
				</tr>
				<tr>
					<td><html:text property="expEndDate" styleId="expEndDate"
							errorStyleClass="error" /></td>
					<td><span class="expEndDateError errorCV"><bean:message
								key="error.expEndDate" /></span></td>
				<tr>
					<td><a><span class="annuleExp"> <bean:message
									key="cv.annuler" />
						</span></a> <a><span class="SaveExp"> <bean:message
									key="cv.enregister" />
						</span></a></td>
				</tr>
			</table>

		</div>

		<a> <span class="addExp"> <bean:message key="cv.addExp" />
		</span>
		</a>
		</fieldset>
	</div>
	<div class="en_cv">
	<fieldset class="fieldsetAppli">
		<div class="entete">
			 <legend class="legendHome">
				<bean:message key="cv.Diplome" />
			</legend> 
		</div>
		<div id="diplome">
			<ul class="listeDiplome fieldsetTableAdmin">


			</ul>
		</div>
		<div class="corp_diplome">
			<table class="fieldsetTableAdmin">
				<tr>
					<td>*<bean:message key="cv.15" /></td>
				</tr>
				<tr>
					<td><html:text property="CvEtude" styleId="CvEtude"
							errorStyleClass="error" /></td>
					<td><span class="CvEtudeError errorCV"><bean:message
								key="error.CvEtude" /></span></td>
				</tr>
				<tr>
					<td>*<bean:message key="cv.16" /></td>
				</tr>

				<tr>
					<td><html:text property="CvEtudeDom" styleId="CvEtudeDom"
							errorStyleClass="error" /></td>
					<td><span class="CvEtudeDomError errorCV"><bean:message
								key="error.CvEtudeDom" /></span></td>
				</tr>

				<tr>
					<td>*<bean:message key="cv.17" /></td>
				</tr>
				<tr>
					<td><html:text property="CvEtablissment"
							styleId="CvEtablissment" errorStyleClass="error" /></td>
					<td><span class="CvEtablissmentError errorCV"><bean:message
								key="error.CvEtablissment" /></span></td>
				</tr>
				<tr>
					<td><bean:message key="cv.18" /></td>
					<td><bean:message key="cv.19" /></td>
				</tr>
				<tr>
					<td><html:text property="CvEtudePays" styleId="CvEtudePays"
							errorStyleClass="error" /></td>
					<td>
						<html:text property="CvEtudeVille" styleId="CvEtudeVille"
							errorStyleClass="error" /></td>
				</tr>
				<tr>
					<td>*<label for="expDate"> <bean:message key="cv.12" />
							:
					</label>
					</td>
				</tr>
				<tr>
					<td><html:text property="etudBeginDate"
							styleId="etudBeginDate" errorStyleClass="error" /> <logic:messagesPresent
							property="etudBeginDate">
							<div class="errorMessage">
								<html:errors property="etudBeginDate" />
							</div>
						</logic:messagesPresent></td>
					<td><span class="etudBeginDateError errorCV"><bean:message
								key="error.etudBeginDate" /></span></td>
				</tr>
				<tr>
					<td>*<label for="etudDate"> <bean:message key="cv.13" />
							:
					</label>
					</td>
				</tr>
				<tr>
					<td><html:text property="etudEndDate" styleId="etudEndDate"
							errorStyleClass="error" /></td>
					<td><span class="etudEndDateError errorCV"><bean:message
								key="error.etudEndDate" /></span></td>
				<tr>
					<td><a><span class="annuleDip"> <bean:message
									key="cv.annuler" /></span></a> <a><span class="SaveDip"> <bean:message
									key="cv.enregister" />
						</span></a></td>
				</tr>
			</table>
		</div>
		<a> <span class="addDip"><bean:message key="cv.addDip" /> </span>
		</a>
		</fieldset>
	</div>

	<div class="en_cv">
	<fieldset class="fieldsetAppli">
		<div class="entete">
			<legend class="legendHome">
				<bean:message key="cv.Formation" />
			</legend>
		</div>
		<div id="formation">
			<ul class="listeFormation fieldsetTableAdmin">


			</ul>
		</div>
		<div class="corp_formation">
			<table class="fieldsetTableAdmin">
				<tr>
					<td>*<bean:message key="cv.29" /></td>
				</tr>
				<tr>
					<td><html:text property="CvFormation" styleId="CvFormation"
							errorStyleClass="error" /></td>
					<td><span class="CvFormationError errorCV"><bean:message
								key="error.CvFormation" /></span></td>
				</tr>
				<tr>
					<td>*<bean:message key="cv.17" /></td>
				</tr>
				<tr>
					<td><html:text property="CvEtablissmentform"
							styleId="CvEtablissmentform" errorStyleClass="error" /></td>
					<td><span class="CvEtablissmentformError errorCV"><bean:message
								key="error.CvEtablissmentform" /></span></td>
				</tr>
				<tr>
					<td><bean:message key="cv.18" /></td>
					<td><bean:message key="cv.19" /></td>
				</tr>
				<tr>
					<td><html:text property="CvFormPays" styleId="CvFormPays"
							errorStyleClass="error" /></td>
					<td>
						<html:text property="CvFormVille" styleId="CvFormVille"
							errorStyleClass="error" /></td>
				</tr>
				<tr>
					<td>*<label for="expDate"> <bean:message key="cv.21" />
							:
					</label>
					</td>
				</tr>
				<tr>
					<td><html:text property="DateObtention"
							styleId="DateObtention" errorStyleClass="error" /> <logic:messagesPresent
							property="DateObtention">
							<div class="errorMessage errorCV">
								<html:errors property="DateObtention" />
							</div>
						</logic:messagesPresent></td>
					<td><span class="DateObtentionError errorCV"><bean:message
								key="error.DateObtention" /></span></td>
				</tr>


				<tr>
					<td><a><span class="annuleForm"> <bean:message
									key="cv.annuler" /></span></a> <a><span class="SaveForm"> <bean:message
									key="cv.enregister" />
						</span></a></td>
				</tr>
			</table>
		</div>
		<a> <span class="addForm"><bean:message key="cv.addForm" />
		</span>
		</a>
		</fieldset>
	</div>

	<div class="en_cv">
	<fieldset class="fieldsetAppli">
		<div class="entete">
			<legend class="legendHome">
				<bean:message key="cv.25" />
			</legend> 
		</div>

		<div id="loisir">
			<ul class="listeLoisir fieldsetTableAdmin">


			</ul>
		</div>
		<div class="corp_loisir">
			<table class="fieldsetTableAdmin">
				<tr>
					<td>*<bean:message key="cv.20" /></td>
				</tr>
				<tr>
					<td><html:text property="CvNomLoisir" styleId="CvNomLoisir"
							errorStyleClass="error" /></td>
					<td><span class="CvNomLoisirError errorCV"><bean:message
								key="error.CvNomLoisir" /></span></td>
				</tr>

				<tr>
					<td><a><span class="annuleLoisir"> <bean:message
									key="cv.annuler" /></span></a> <a><span class="SaveLoisir"> <bean:message
									key="cv.enregister" /></span></a></td>
				</tr>
			</table>
		</div>
		<a> <span class="addLoisir"> <bean:message
					key="cv.addLoisir" />
		</span>
		</a>
		</fieldset>
	</div>
	<div class="en_cv">
	  <fieldset class="fieldsetAppli">
		<div class="entete">
			<legend class="legendHome">
				<bean:message key="cv.26" />
			</legend>
		</div>

		<div id="Langue">
			<ul class="listeLangues fieldsetTableAdmin">


			</ul>
		</div>
		<div class="corp_langue">
			<table class="fieldsetTableAdmin">
				<tr>
					<td>*<bean:message key="cv.27" /></td>
				</tr>
				<tr>
					<td><input type="text" name="CVLangue" id="CVLangue" />
					<td>
					<td><span class="CVLangueError errorCV"><bean:message
								key="error.CVLangue" /></span></td>
				</tr>
				<tr>
					<td>*<bean:message key="cv.28" /></td>
				</tr>
				<tr>
					<td><select name="niveaux" id="niveaux">
							<option selected="selected">
								<bean:message key="niveux.0" />
							</option>
							<option value="<bean:message key="niveux.1"/>">
								<bean:message key="niveux.1" />
							</option>
							<option value="<bean:message key="niveux.2"/>">
								<bean:message key="niveux.2" />
							</option>
							<option value="<bean:message key="niveux.3"/>">
								<bean:message key="niveux.3" />
							</option>
					</select></td>
				</tr>
				<tr>
					<td><a><span class="annuleLangue"> <bean:message
									key="cv.annuler" /></span></a> <a><span class="SaveLangue"><bean:message
									key="cv.enregister" /> </span></a></td>
				</tr>
			</table>
		</div>
		<a> <span class="addLangue"> <bean:message
					key="cv.addLangue" />
		</span>
		</a>
		</fieldset>
	</div>


	<html:submit styleId="envoi" styleClass="button">
		<bean:message key="cv.23" />
	</html:submit>
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
		$("#expBeginDate").datepicker($.datepicker.regional['fr']);
		$("#expEndDate").datepicker($.datepicker.regional['fr']);
		$("#etudBeginDate").datepicker($.datepicker.regional['fr']);
		$("#etudEndDate").datepicker($.datepicker.regional['fr']);
		$("#DateObtention").datepicker($.datepicker.regional['fr']);
	});
</script>