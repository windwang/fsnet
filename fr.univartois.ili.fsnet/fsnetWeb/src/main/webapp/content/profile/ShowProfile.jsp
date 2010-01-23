<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>


<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>  


<h3>${watchedProfile.prenom} ${watchedProfile.nom}'s profile</h3>
<table class="watchedProfile">

    <tr>
        <th>E-mail</th>
        <td><html:link href="mailto:${watchedProfile.email}">${watchedProfile.email}</html:link></td>
    </tr>
    <tr>
        <th>Adress</th>
        <td>${watchedProfile.adresse}</td>
    </tr>
    <tr>
        <th>Birthday</th>
        <td>${watchedProfile.dateNaissance}</td>
    </tr>

    <tr>
        <th>Sexe</th>
        <td>${watchedProfile.sexe}</td>
    </tr>
    <tr>
        <th>PhoneNumber</th>
        <td>${watchedProfile.numTel}</td>
    </tr>
</table>