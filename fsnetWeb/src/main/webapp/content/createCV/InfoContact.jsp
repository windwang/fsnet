<%-- 
    Document   : InfoContact
    Created on : 07 janv
    Author     : BENZAOUIA Anass <anassbenzaouia at gmail.com>
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet" type="text/css" media="screen" href="css/cv.css" />
<html:form action="/CreateCV">
	<div class="en_cv">
		<div class="entete">
			<h3>
				<bean:message key="cv.titre" /> 
			</h3>
		</div>
		<div class="corp">
			<table>
				<tr>
					<td><label for="CvTitle"> <bean:message key="cv.0" />
							:
					</label></td>
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
	</div>

	<div class="en_cv">
		<div class="entete">
			<h3>
				<bean:message key="cv.contact" />
			</h3>
		</div>
		<div class="corp_contact">
			<table>
				<tr>
					<td><label for="CvNom"> <bean:message key="cv.1" /> :
					</label></td>
					<td><html:text property="CvNom" styleId="CvNom"
							errorStyleClass="error" value="${sessionScope.user.name}" /> <logic:messagesPresent
							property="CvNom">
							<div class="errorMessage">
								<html:errors property="CvNom" />
							</div>
						</logic:messagesPresent></td>
				</tr>
				<tr>
					<td><label for="CvPrenom"> <bean:message key="cv.2" />
							:
					</label></td>
					<td><html:text property="CvPrenom" styleId="CvPrenom"
							errorStyleClass="error" value="${sessionScope.user.firstName}"/> <logic:messagesPresent
							property="CvPrenom">
							<div class="errorMessage">
								<html:errors property="CvPrenom" />
							</div>
						</logic:messagesPresent></td>
				</tr>
				<tr>
			<td><label for="sexe"> <bean:message key="members.sexe" />
			: </label></td>
			<td><select name="sexe">
		
			<c:choose>
				<c:when test="${sessionScope.user.sex == 'male'}">
					<option value=""></option>
					<option value="male" selected="selected"><bean:message key="members.sexe.Male" /></option>
					<option value="female"><bean:message key="members.sexe.Female" /></option>	
				</c:when>
				<c:when test="${sessionScope.user.sex == 'female'}">
					<option value=""></option>
					<option value="male" ><bean:message key="members.sexe.Male" /></option>
					<option value="female" selected="selected"><bean:message key="members.sexe.Female" /></option>	
				</c:when>
				<c:otherwise>
					<option value=""></option>
					<option value="male"><bean:message key="members.sexe.Male" /></option>
					<option value="female"><bean:message key="members.sexe.Female" /></option>	
				</c:otherwise>
			</c:choose>			
			
			</select></td>
		</tr>
				<tr>
					<td><label for="CvAdresse"> <bean:message key="cv.3" />
							:
					</label></td>
					<td><html:text property="CvAdresse" styleId="CvAdresse"
							errorStyleClass="error" value="${sessionScope.user.address.address}" /> <logic:messagesPresent
							property="CvAdresse">
							<div class="errorMessage">
								<html:errors property="CvAdresse" />
							</div>
						</logic:messagesPresent></td>
				</tr>
				<tr>
					<td><label for="CvVille"> <bean:message key="cv.4" />
							:
					</label></td>
					<td><html:text property="CvVille" styleId="CvVille"
							errorStyleClass="error" value="${sessionScope.user.address.city}"/> <logic:messagesPresent
							property="CvVille">
							<div class="errorMessage">
								<html:errors property="CvVille" />
							</div>
						</logic:messagesPresent></td>
				</tr>
				<tr>
					<td><label for="CvCp"> <bean:message key="cv.5" /> :
					</label></td>
					<td><html:text property="CvCp" styleId="CvCp"
							errorStyleClass="error" /> <logic:messagesPresent
							property="CvCp">
							<div class="errorMessage">
								<html:errors property="CvCp" />
							</div>
						</logic:messagesPresent></td>
				</tr>
				<tr>
					<td><label for="CvPays"> <bean:message key="cv.6" /> :
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
					<td><label for="CvPortable"> <bean:message key="cv.7" />
							:
					</label></td>
					<td><html:text property="CvPortable" styleId="CvPortable"
							errorStyleClass="error" value="${sessionScope.user.phone}"  /> <logic:messagesPresent
							property="CvPortable">
							<div class="errorMessage">
								<html:errors property="CvPortable" />

							</div>
						</logic:messagesPresent></td>
				</tr>
				
				<tr>
			<td><label for="birthDay"> <bean:message
						key="members.birthDay" /> :</label></td>
			<td><html:text errorStyleClass="error" styleId="birthDay"
					property="formatBirthDay" >
				</html:text></td>
		</tr>
				<tr>
					<td><label for="CvSituation"> <bean:message key="cv.8" />
							:
					</label></td>
					<td><bean:message key="cv.celeb" /><input type="radio"
						name="situation" value="<bean:message key="cv.celeb" />" /> <bean:message key="cv.mar" /><input
						type="radio" name="situation" value=" <bean:message key="cv.mar" />" /></td>
				</tr>
				<tr>
					<td><label for="CvContact"> <bean:message key="cv.9" />
							:
					</label></td>
					<td><html:text property="CvContact" styleId="CvContact"
							errorStyleClass="error" value="${sessionScope.user.email}"/> <logic:messagesPresent
							property="CvContact">
							<div class="errorMessage">
								<html:errors property="CvContact" />
							</div>
						</logic:messagesPresent></td>
				</tr>
			</table>
		</div>
	</div>
	<html:submit styleClass="button"><bean:message key="cv.suivant" /></html:submit>
</html:form>

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
		$("#birthDay").datepicker($.datepicker.regional['fr']);
	});
</script>
