<%-- 
    Document   : DisplayAnnounce
    Created on : 18 janv. 2010, 18:06:12
    Author     : Mehdi Benzaghar <mehdi.benzaghar at gmail.com>
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<html:form >
	<table id="CreateAnnounce">
	<logic:present name="owner">
	<tr>
	<bean:define id="idAnnounce" name="announce" property="id" />
	<td><html:link  action="/DisplayForModifyAnnounce"  paramId="idAnnounce" paramName="idAnnounce">update</html:link>	</td>
	<td><html:link  action="/DeleteAnnounce"  paramId="idAnnounce" paramName="idAnnounce">delete</html:link></td>
	</tr>
	</logic:present>
	<tr>
	</tr>
	<tr>
			<td>
				<label for="announceCreator">Créateur :</label>
			</td>
			<td>
			<bean:write name="entiteSociale" property="nom" />
    			<bean:write name="entiteSociale" property="prenom" />
    		</td>
    	</tr>
		<tr>
			<td>
				<label for="announceTitle">Titre :</label>
			</td>
			<td>
    			<bean:write name="announce" property="nom" />
    		</td>
    	</tr>
    	<tr>
    		<td>
    			<label for="announceContent">Contenu: </label>
    		</td>
    		<td>
    			<bean:write name="announce" property="contenu" />
    		</td>
    	</tr>
    	<tr>
    		<td>
    			<label for="announceExpiryDate">Date :</label>
    		</td>
    		<td>
    			<bean:write name="announce" property="dateFinAnnonce" />
    		</td>
    	</tr>
    	<tr>
    		<td colspan="2">
    			<html:messages id="message" />
    			<html:errors/>
    		</td>
    	</tr>
    </table>
</html:form>