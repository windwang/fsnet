<%-- 
    Document   : CreateHub
    Author     : Audrey Ruellan et Cerelia Besnainou
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<html:form action="/SearchHub">
	<table id="SearchHub">
		<tr>
			<td>
				<label for="hubName">Hub's name :</label>
			</td>
			<td>
    			<html:text property="hubName" styleId="hubName" />
    		</td>
    		<td>
    			<html:submit styleClass="button">Search Hub</html:submit>
    		</td>
    	</tr>
    	<tr>
    		<td>
    			<html:errors/>
    		</td>
    	</tr>
    </table>
</html:form>