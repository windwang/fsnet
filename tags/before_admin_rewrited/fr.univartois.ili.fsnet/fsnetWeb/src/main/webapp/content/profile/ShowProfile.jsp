<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>


<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>  
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>

<h3>
	<bean:message key="showProfile.title" arg0="${watchedProfile.prenom} ${watchedProfile.nom}"/>
</h3>
<table class="watchedProfile">
    <tr>
        <th><bean:message key="updateProfile.email"/></th>
        <td>
        	<html:link href="mailto:${watchedProfile.email}">
        		${watchedProfile.email}
        	</html:link>
        </td>
    </tr>
    <tr>
        <th><bean:message key="updateProfile.adress"/></th>
        <td>${watchedProfile.adresse}</td>
    </tr>
    <tr>
        <th><bean:message key="updateProfile.dateOfBirth"/></th>
        <td>${watchedProfile.dateNaissance}</td>
    </tr>

    <tr>
        <th><bean:message key="updateProfile.sexe"/></th>
        <td>${watchedProfile.sexe}</td>
    </tr>
    <tr>
        <th><bean:message key="updateProfile.phone"/></th>
        <td>${watchedProfile.numTel}</td>
    </tr>
</table>