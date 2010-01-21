<%-- 
    Document   : CreateHub
    Author     : Audrey Ruellan et Cerelia Besnainou
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<h3>Create Hub</h3>
<html:form action="/CreateHub">
	<table id="CreateHub">
		<tr>
			<td>
				<label for="hubName">Hub's name :</label>
			</td>
			<td>
    			<html:text property="hubName" styleId="hubName" />
    		</td>
    		<td>
    			<html:submit styleClass="button">Create Hub</html:submit>
    		</td>
    	</tr>
    	<tr>
    		<td>
    			<html:errors property="hubName"/>
    		</td>
    	</tr>
    </table>
</html:form>