<%-- 
    Document   : CreateHub
    Author     : Audrey Ruellan et Cerelia Besnainou
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>

<h3><bean:message key="hubs.search"/></h3>
<html:form action="/SearchHub">
	<table id="SearchHub">
		<tr>
			<td>
				<label for="hubName"><bean:message key="hubs.name"/></label>
			</td>
			<td>
    			<html:text property="hubName" styleId="hubName" />
    		</td>
    		<td>
    			<html:submit styleClass="button"><bean:message key="hubs.search"/></html:submit>
    		</td>
    	</tr>
    	<tr>
    		<td>
    			<html:errors/>
    		</td>
    	</tr>
    </table>
</html:form>