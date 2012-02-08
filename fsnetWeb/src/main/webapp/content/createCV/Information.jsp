<%-- 
    Document   : Information
    Created on : 07 janv
    Author     : BENZAOUIA Anass <anassbenzaouia at gmail.com>
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

 <link rel="stylesheet" type="text/css" media="screen"	href="css/cv.css" />
<html:form action="/GenerateCV">


<div class="en_cv">
<div class="entete">
<h3><bean:message key="cv.objectifs"/></h3> 
</div>
<div class="corp_objectif">
<table>
<tr><td><bean:message key="cv.objectifs2"/></td></tr>
<tr><td><textarea style="border: 1px solid rgb(204, 204, 204);"></textarea></td></tr>
</table></div>
 
</div>

<div class="en_cv">
<div class="entete">
<h3><bean:message key="cv.experiencePro"/></h3> 
</div>
<div id="experiences">
<ul class="listeExperience">
 
 
</ul>
</div>
<div class="corp_experience">
<table>

<tr><td>*<bean:message key="cv.10"/></td></tr>
<tr><td><html:text  property="CvPoste"
                            styleId="CvPoste"
                            errorStyleClass="error" /></td>
                            <td><span class="CvPosteError"><bean:message key="error.CvPoste"/></span></td></tr>
<tr><td>*<bean:message key="cv.11"/></td></tr>
<tr><td><html:text  property="NomEntreprise"
                            styleId="NomEntreprise"
                            errorStyleClass="error" /></td>
                            <td><span class="NomEntrepriseError"><bean:message key="error.NomEntreprise"/></span></td></tr>
<tr><td>*<bean:message key="cv.secteur"/></td></tr>
<tr><td><html:text  property="CvSecteur"
                            styleId="CvSecteur"
                            errorStyleClass="error" /></td>
                             <td><span class="CvSecteurError"><bean:message key="error.CvSecteur"/></span></td></tr>
<tr><td>*<bean:message key="cv.lieu"/></td></tr>
<tr><td><html:text  property="CvLieu"
                            styleId="CvLieu"
                            errorStyleClass="error" /></td>
                            <td><span  errorStyleClass="error" class="CvLieuError"><bean:message key="error.CvLieu"/></span></td></tr>
      <tr><td>*<label for="expDate">
                    <bean:message key="cv.12"/> :
                </label>
            </td>
            </tr>
            <tr>
            <td>
                <html:text  property="expBeginDate" 
                            styleId="expBeginDate"
                            errorStyleClass="error"/>
                            
                <logic:messagesPresent property="expBeginDate">
                    <div class="errorMessage">
                        <html:errors property="expBeginDate"/>
                    </div>
                </logic:messagesPresent>
            </td>  <td><span class="expBeginDateError"><bean:message key="error.expBeginDate"/></span></td>
        </tr>
        <tr>
            <td>
                *<label for="expDate">
                    <bean:message key="cv.13"/> :
                </label>
            </td>
            </tr>
            <tr>
            <td>
                <html:text  property="expEndDate" 
                            styleId="expEndDate"
                            errorStyleClass="error"/>
                            </td><td><span class="expEndDateError"><bean:message key="error.expEndDate"/></span></td>
        
         <tr><td><a><span class="annuleExp"> <bean:message key="cv.annuler"/> </span></a>
        <a><span class="SaveExp"> <bean:message key="cv.enregister"/> </span></a></td></tr>                   
</table>

</div>

<a>
<span class="addExp"> <bean:message key="cv.addExp"/> </span>
</a>
</div>
<div class="en_cv">
<div class="entete">
<h3><bean:message key="cv.Diplome"/></h3> 
</div>
<div id="formation">
<ul class="listeFormation">
 
 
</ul>
</div>
<div class="corp_diplome">
<table>
<tr><td>*<bean:message key="cv.15"/></td></tr>
<tr><td><html:text  property="CvEtude"
                            styleId="CvEtude"
                            errorStyleClass="error" /></td>
                            <td><span class="CvEtudeError"><bean:message key="error.CvEtude"/></span></td></tr>
<tr><td>*<bean:message key="cv.16"/></td></tr>
                            
<tr><td><html:text  property="CvEtudeDom"
                            styleId="CvEtudeDom"
                            errorStyleClass="error" /></td>
                             <td><span class="CvEtudeDomError"><bean:message key="error.CvEtudeDom"/></span></td></tr>
                            
<tr><td>*<bean:message key="cv.17"/></td></tr>                            
<tr><td><html:text  property="CvEtablissment"
                            styleId="CvEtablissment"
                            errorStyleClass="error" /></td>
                            <td><span class="CvEtablissmentError"><bean:message key="error.CvEtablissment"/></span></td></tr>
<tr><td>*<bean:message key="cv.18"/></td></tr>                            
<tr><td><html:text  property="CvEtudePays"
                            styleId="CvEtudePays"
                            errorStyleClass="error" /></td><td><bean:message key="cv.19"/><html:text  property="CvEtudeVille"
                            styleId="CvEtudeVille"
                            errorStyleClass="error" /></td>
                            </tr>
                               <tr><td>*<label for="expDate">
                    <bean:message key="cv.12"/> :
                </label>
            </td>
            </tr>
            <tr>
            <td>
                <html:text  property="etudBeginDate" 
                            styleId="etudBeginDate"
                            errorStyleClass="error"/>
                            
                <logic:messagesPresent property="etudBeginDate">
                    <div class="errorMessage">
                        <html:errors property="etudBeginDate"/>
                    </div>
                </logic:messagesPresent>
            </td><td><span class="etudBeginDateError"><bean:message key="error.etudBeginDate"/></span></td>
        </tr>
        <tr>
            <td>
                *<label for="etudDate">
                    <bean:message key="cv.13"/> :
                </label>
            </td>
            </tr>
            <tr>
            <td>
                <html:text  property="etudEndDate" 
                            styleId="etudEndDate"
                            errorStyleClass="error"/>
                            </td><td><span class="etudEndDateError"><bean:message key="error.etudEndDate"/></span></td>
               <tr><td><a><span class="annuleForm"> <bean:message key="cv.annuler"/></span></a>
        <a><span class="SaveForm"> <bean:message key="cv.enregister"/> </span></a></td></tr>                       
</table>
</div>
<a>
<span class="addForm"><bean:message key="cv.addForm"/>  </span>
</a>
</div>
<div class="en_cv">
<div class="entete">
<h3><bean:message key="cv.25"/></h3> 
</div>

<div id="Certificaion">
<ul class="listeCertification">
 
 
</ul>
</div>
<div class="corp_certificat">
<table>
<tr><td>*<bean:message key="cv.20"/></td></tr>
<tr><td><html:text  property="CvNomCertif"
                            styleId="CvNomCertif"
                            errorStyleClass="error" /></td>
                            <td><span class="CvNomCertifError"><bean:message key="error.CvNomCertif"/></span></td></tr>
<tr><td>*<bean:message key="cv.21"/></td></tr>
<tr><td><html:text  property="CvDateCertif"
                            styleId="CvDateCertif"
                            errorStyleClass="error" /></td><td><span class="CvDateCertifError"><bean:message key="error.CvDateCertif"/></span></td></tr>
 <tr><td><bean:message key="cv.22"/></td></tr>
<tr><td><html:text  property="CvEcolCertif"
                            styleId="CvEcolCertif"
                            errorStyleClass="error" /></td>
                           <td><span class="CvEcolCertifError"><bean:message key="error.CvEcolCertif"/></span></td> </tr>
                             <tr><td><a><span class="annuleCert"> <bean:message key="cv.annuler"/></span></a>
        <a><span class="SaveCert"> <bean:message key="cv.enregister"/></span></a></td></tr>         
</table>
</div>
<a>
<span class="addCert"> <bean:message key="cv.addCert"/>  </span>
</a>
</div>
<div class="en_cv">
<div class="entete">
<h3><bean:message key="cv.26"/></h3> 
</div>

<div id="Langue">
<ul class="listeLangues">
 
 
</ul>
</div>
<div class="corp_langue">
<table>
<tr><td>*<bean:message key="cv.27"/></td></tr>
<tr><td><input type="text" name="CVLangue" id="CVLangue"/><td>
                            <td><span class="CVLangueError"><bean:message key="error.CVLangue"/></span></td></tr>
 <tr><td>*<bean:message key="cv.28"/></td></tr>
 <tr><td><select name="niveaux" id="niveaux">
 		<option selected="selected"><bean:message key="niveux.0"/></option>
 		<option ><bean:message key="niveux.1"/></option>
 		<option ><bean:message key="niveux.2"/></option>
 		<option ><bean:message key="niveux.3"/></option></select>
 		</td> </tr>
 <tr><td><a><span class="annuleLangue"> <bean:message key="cv.annuler"/></span></a>
        <a><span class="SaveLangue"><bean:message key="cv.enregister"/> </span></a></td></tr>        
</table>
</div>
<a>
<span class="addLangue"> <bean:message key="cv.addLangue"/> </span>
</a>
</div>


<html:submit styleClass="button"><bean:message key="cv.23"/></html:submit>
</html:form>
<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="js/cv.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.7.2.custom.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-i18n.min.js"></script>
<script type="text/javascript">
$(function() {
	$.datepicker.setDefaults($.extend( {
		
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
	$("#CvDateCertif").datepicker($.datepicker.regional['fr']);
});
</script> 