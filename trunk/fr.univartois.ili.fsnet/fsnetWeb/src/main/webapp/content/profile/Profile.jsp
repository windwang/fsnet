<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>

<h3>Update Profile</h3>
<html:form action="/ModifyProfile">
    <table id="ModifyProfile">
        <tr>
            <td>
                <label for="name"><bean:message key="updateProfile.name"/></label>
            </td>
            <td>
                <html:text errorStyleClass="error" property="name" value="${currentUser.nom}"/>
            </td>
        </tr>
        <tr><td colspan="2"><label><html:errors property="name"/></label></td></tr>
        <tr>
            <td>
                <label for="firstName"><bean:message key="updateProfile.firstname"/></label>
            </td>
            <td>
                <html:text errorStyleClass="error" property="firstName"  value="${currentUser.prenom}"/>
            </td>
        </tr>
        <tr><td colspan="2"><label><html:errors property="firstName"/></label></td></tr>
        <tr>
            <td>
                <label for="adress"><bean:message key="updateProfile.adress"/></label>
            </td>
            <td>
                <html:textarea errorStyleClass="error" property="adress" value="${currentUser.adresse}"/>
            </td>
        </tr>
        <tr><td colspan="2"><label><html:errors property="adress"/></label></td></tr>
        <tr>
            <td>
                <label for="dateOfBirth"><bean:message key="updateProfile.dateOfBirth"/></label>
            </td>
            <td>
                <html:text errorStyleClass="error" readonly="true" styleId="dateOfBirth" property="dateOfBirth" value="${currentUser.dateNaissance}"/>
            </td>
        </tr>
        <tr><td colspan="2"><label><html:errors property="dateOfBirth"/></label></td></tr>
        <tr>
            <td>
                <label for="sexe"><bean:message key="updateProfile.sexe"/></label>
            </td>
            <td>
                <html:select property="sexe" value="${currentUser.sexe}">
                    <html:option value="Man">Male</html:option>
                    <html:option value="Woman">Female</html:option>
                </html:select>
            </td>
        </tr>
        <tr><td colspan="2"><label><html:errors property="sexe"/></label></td></tr>
        <tr>
            <td>
                <label for="pwd"><bean:message key="updateProfile.pwd"/></label>
            </td>
            <td><html:password property="pwd" value="${currentUser.mdp}"/></td>
        </tr>
        <tr><td colspan="2"><label><html:errors property="pwd"/></label></td></tr>
        <tr>
            <td>
                <label for="confirmPwd"><bean:message key="updateProfile.confirmPwd"/></label>
            </td>
            <td><html:password property="confirmPwd" value="${currentUser.mdp}"/></td>
        </tr>
        <tr><td colspan="2"><label><html:errors property="confirmPwd"/></label></td></tr>
        <tr>
            <td>
                <label for="job"><bean:message key="updateProfile.job"/></label>
            </td>
            <td>
                <html:text errorStyleClass="error" property="job" value="${currentUser.profession}"/>
            </td>
        </tr>
        <tr><td colspan="2"><label><html:errors property="job"/></label></td></tr>
        <tr>
            <td>
                <label for="mail"><bean:message key="updateProfile.email"/></label>
            </td>
            <td>
                <html:text errorStyleClass="error" property="mail" value="${currentUser.email}" readonly="true"/>
            </td>
        </tr>
        <tr><td colspan="2"><label><html:errors property="mail"/></label></td></tr>        
        <tr>
            <td>
                <label for="phone"><bean:message key="updateProfile.phone"/></label>
            </td>
            <td>
                <html:text errorStyleClass="error" property="phone" value="${currentUser.numTel}"/>
            </td>
        </tr>
        <tr><td colspan="2"><label><html:errors property="phone"/></label></td></tr> 
        <tr>        	
            <td colspan="2">
            	<html:submit>Validate</html:submit>
          	</td>
        </tr>
    </table>
</html:form>

	<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.7.2.custom.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-i18n.min.js"></script>
	<script type="text/javascript">
		$(function() {
			$.datepicker.setDefaults($.extend(
					{
						yearRange: '-100:+1',
						changeYear: true,
						maxDate: 0,
					 	dateFormat: 'dd/mm/yy',
					 	showOn: 'button', 
					 	buttonImage: 'images/calendar.gif', 
					 	buttonImageOnly: true, 
					 	showMonthAfterYear: false
					}));
			$("#dateOfBirth").datepicker($.datepicker.regional['fr']);		
		});
	</script> 
      